package ee.proekspert.promuseum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
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
        TextView code = findViewById(R.id.item_code);
        code.setText("TLM 2344 L 23:543");
        //code.setText(code.getText() + "\n" + item_code);
        ((TextView) findViewById(R.id.item_name)).setText("Lembitu raudrüü");
        ((TextView) findViewById(R.id.item_condition)).setText("Rahuldav");
        ((TextView) findViewById(R.id.item_condition_comment)).setText("Veidi mõranenud külgede pealt");
        ((TextView) findViewById(R.id.item_location)).setText("Kelder / Riiul 28");
        ((TextView) findViewById(R.id.item_last_checked)).setText("21.12.2018 11:33:52");
        new DownloadImageTask((ImageView) findViewById(R.id.item_image))
                .execute("https://www.muis.ee/digitaalhoidla/api/meedia/pisipilt?id=94a16596-e649-4109-a2ce-7fb10249d210");

        findViewById(R.id.item_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
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
