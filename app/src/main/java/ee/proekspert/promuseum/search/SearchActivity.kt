package ee.proekspert.promuseum.search

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import ee.proekspert.promuseum.ItemActivity
import ee.proekspert.promuseum.R
import ee.proekspert.promuseum.barcode.BarcodeTracker
import ee.proekspert.promuseum.barcode.BarcodeTrackerFactory
import ee.proekspert.promuseum.camera.CameraSource
import ee.proekspert.promuseum.camera.CameraSourcePreview
import ee.proekspert.promuseum.itemlist.TabbedItemListActivityBackup
import java.io.IOException


class SearchActivity : AppCompatActivity(), BarcodeTracker.BarcodeGraphicTrackerCallback {

    private var items = hashSetOf("0100000001", "0201198362", "0202444499", "0201699391", "0201472125")

    private val TAG = "Barcode-reader"

    // Intent request code to handle updating play services if needed.
    private val RC_HANDLE_GMS = 9001

    // Permission request codes need to be < 256
    private val RC_HANDLE_CAMERA_PERM = 2

    private lateinit var inputField: EditText

    private var mCameraSource: CameraSource? = null
    private var mPreview: CameraSourcePreview? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)


        supportActionBar!!.title = "Otsing"

        inputField = findViewById(R.id.insert_code_field)

        inputField.setOnEditorActionListener { view, i, keyEvent ->
            codeLookup(inputField.text.toString())
            true
        }

        mPreview = findViewById<View>(R.id.preview) as CameraSourcePreview

        val autoFocus = true
        val useFlash = false

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        var museumCode = intent.getStringExtra("LAST_CHECKED_ITEM")

        if (museumCode != null) {
            var toast = Toast.makeText(applicationContext, museumCode + " checked", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    private fun codeLookup(barcode: String) {
        if (!items.contains(barcode)) notFound()

        if (barcode.startsWith("02")) {
            goToItem(barcode)
        } else if (barcode.startsWith("01")) {
            goToLocation(barcode)
        } else {
            notFound()
        }
    }

    private fun notFound() {
        findViewById<TextView>(R.id.result_textview).text = "404 Not Found"
        inputField.selectAll()
    }

    private fun goToItem(text: String) {
        inputField.text.clear()
        val intent = Intent(applicationContext, ItemActivity::class.java)
        ItemActivity.item_code = text
        startActivity(intent)
    }

    private fun goToLocation(text: String) {
        inputField.text.clear()
        val intent = Intent(applicationContext, TabbedItemListActivityBackup::class.java)
//        ItemListActivity.location_code = text
        startActivity(intent)
    }

    // Handles the requesting of the camera permission.
    private fun requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission")

        val permissions = arrayOf(Manifest.permission.CAMERA)

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
        }
    }

    /**
     * Creates and starts the camera.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val context = applicationContext

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        val barcodeDetector = BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()
        val barcodeFactory = BarcodeTrackerFactory(this)
        barcodeDetector.setProcessor(MultiProcessor.Builder(barcodeFactory).build())

        if (!barcodeDetector.isOperational) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.")

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error,
                        Toast.LENGTH_LONG).show()
                Log.w(TAG, getString(R.string.low_storage_error))
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        var builder: CameraSource.Builder = CameraSource.Builder(applicationContext, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f)

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
        }

        mCameraSource = builder
                .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
                .build()
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *
     *
     * @param requestCode  The request code passed in [.requestPermissions].
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either [PackageManager.PERMISSION_GRANTED]
     * or [PackageManager.PERMISSION_DENIED]. Never null.
     * @see .requestPermissions
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {


        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source")
            // we have permission, so create the camerasource
            val autoFocus = true
            val useFlash = false
            createCameraSource(autoFocus, useFlash)
            return
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.size +
                " Result code = " + if (grantResults.size > 0) grantResults[0] else "(empty)")

        val listener = DialogInterface.OnClickListener { dialog, id -> finish() }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show()
    }


    // Restarts the camera
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    // Stops the camera
    override fun onPause() {
        super.onPause()
        if (mPreview != null) {
            mPreview!!.stop()
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mPreview != null) {
            mPreview!!.release()
        }
    }


    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    @Throws(SecurityException::class)
    private fun startCameraSource() {
        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (mCameraSource != null) {
            try {
                mPreview!!.start(mCameraSource)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }

        }
    }

    override fun onDetectedQrCode(barcode: Barcode?) {
        if (barcode != null) {
            codeLookup(barcode.displayValue)
        }
    }

}
