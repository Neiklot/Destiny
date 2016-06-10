package softcamp.com.destiny;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=(TextView)findViewById(R.id.text1);
        new ConnectToApi().execute();
    }


    private class ConnectToApi extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                String contentAsString= connectToApi();
                System.out.println("connected:" + contentAsString);

//                Log.v("blah", "blah blah");
                return contentAsString;
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            t.setText("result: "+result);
        }

        private String connectToApi() throws IOException {
            InputStream is = null;
            String contentAsString="";
            String buscado="";
            try {
                URL url = new URL("http://www.bungie.net/Platform/Destiny/1/Account/4611686018459599029/Summary/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestProperty("x-api-key", "68c569382716442ba9ee0ffcbb6bd12c");
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DEBUG", "The response is: " + response);
                is = conn.getInputStream();
                contentAsString = readIt(is, 500);
            } finally {
                if (is != null) {
                    is.close();
                }
            }

            try {
                URL url = new URL("http://www.bungie.net/Platform/Destiny/SearchDestinyPlayer/1/MasterRhombus76/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestProperty("x-api-key", "68c569382716442ba9ee0ffcbb6bd12c");
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DEBUG", "The response is: " + response);
                is = conn.getInputStream();
                buscado = readIt(is, 500);
            } finally {
                if (is != null) {
                    is.close();
                }
            }


            return contentAsString+" // "+buscado;
        }
    }


    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
