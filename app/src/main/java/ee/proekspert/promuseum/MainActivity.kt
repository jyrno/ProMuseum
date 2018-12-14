package ee.proekspert.promuseum

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import ee.proekspert.promuseum.barcode.BarcodeCaptureActivity
import ee.proekspert.promuseum.itemlist.ItemListActivity
import ee.proekspert.promuseum.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mResultTextView = findViewById(R.id.result_textview)

        Log.i("OpenCameraSource", "qQQ")
//        findViewById<Button>(R.id.show_item_list).setOnClickListener {
//            val intent = Intent(applicationContext, ItemListActivity::class.java)
//            startActivity(intent)
//        }
//        findViewById<Button>(R.id.scan_barcode_button).setOnClickListener {
//            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
//            Log.i("OpenCameraSource", "qQQ222")
//            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
//        }
        findViewById<Button>(R.id.connect_device).setOnClickListener {
            val intent = Intent(applicationContext, ConnectToScannerActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.search_page_button).setOnClickListener {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPostResume() {
        super.onPostResume()

        var message = intent.getStringExtra("CONNECTION_MESSAGE")
        if (message != null) {
            var toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
            toast.show()
        }

    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return false
    }
}
