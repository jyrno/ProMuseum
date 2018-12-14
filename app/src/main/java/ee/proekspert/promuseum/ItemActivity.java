package ee.proekspert.promuseum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ee.proekspert.promuseum.R;
import ee.proekspert.promuseum.data.Item;
import ee.proekspert.promuseum.datasource.ItemProvider;
import ee.proekspert.promuseum.search.SearchActivity;

public final class ItemActivity extends AppCompatActivity {

    private static final String TAG = "item";

    private static int i = 1;
    @Nullable
    public static String item_code;

    public ItemActivity() {
        disableSSLCertificateChecking();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.item);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView code = findViewById(R.id.item_code);

        Item item = ItemProvider.getItemProvider().findById(item_code);

        final String museumCode = item.getMuseumId();
        code.setText(museumCode);
        //code.setText(code.getText() + "\n" + item_code);
        ((TextView) findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) findViewById(R.id.item_condition)).setText(item.getState().toString());
        ((TextView) findViewById(R.id.item_condition_comment)).setText(item.getDamage());
        ((TextView) findViewById(R.id.item_location)).setText(item.getLocation().toString());
        ((TextView) findViewById(R.id.item_last_checked)).setText("11.12.2018 11:23:51");
        new DownloadImageTask((ImageView) findViewById(R.id.item_image))
                .execute("https://www.muis.ee/digitaalhoidla/api/meedia/pisipilt?id=" + item.getImageId());

        //https://www.muis.ee/digitaalhoidla/api/meedia/pisipilt?id=3ccfa93f-322d-4fc6-b40b-22cc6d9a490f

        findViewById(R.id.item_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                startActivityAfterCleanup(SearchActivity.class);
                return true;
            case R.id.action_scan:
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void startActivityAfterCleanup(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                //InputStream in = new java.net.URL(urldisplay).openStream();
                URLConnection conn = new URL(urldisplay).openConnection();
                //trustAllCertificates(conn);
                InputStream in = conn.getInputStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

        private void trustAllCertificates(URLConnection conn) {
            if (conn instanceof HttpsURLConnection) {
                Log.i("aa", "AAAAAAAAA");
                ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession sslSession) {
                        return true;
                    }
                });
            }
        }
    }
    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            //e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        }
    }

}
