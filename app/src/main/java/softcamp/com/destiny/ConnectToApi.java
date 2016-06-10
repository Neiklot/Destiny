package softcamp.com.destiny;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by laptop on 07/06/2016.
 */
public class ConnectToApi extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String[] params) {
            String urlToRead = "http://www.bungie.net/Platform/User/GetBungieNetUserById/13300045/";
            urlToRead = "http://www.bungie.net/Platform/User/GetBungieAccount/13300045/2/";
            // urlToRead = "http://www.bungie.net/Platform/Destiny/Stats/ActivityHistory/1/13300045/4611686018459599029/";
            BufferedReader rd = null;
            HttpURLConnection conn = null;
            String datos="";
            try {
                URL url = new URL(urlToRead);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("x-api-key", "68c569382716442ba9ee0ffcbb6bd12c");
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String dataLine;

                while ((dataLine = rd.readLine()) != null) {
                    if (dataLine.startsWith("ERROR"))
                        throw new RuntimeException();
                    datos+= dataLine.toString();
                    System.out.println(datos);
                }
                rd.close();
                conn.disconnect();
            } catch (Exception e) {
                System.out.println("Url no válida: {" + urlToRead + "} por excepción: " + e);
            }
            return datos;
        }

        public String logIn(String user,String pass) {

            return this.execute(pass, pass).toString();
        }


}
