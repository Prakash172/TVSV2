package com.tvs.splashactivity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostAsyncTask extends AsyncTask<String,Void,String> {
    String data = "";

    private static final String TAG = "PostAsyncTask";
    @Override
    protected String doInBackground(String... params) {

            HttpURLConnection conn = null;
            try {

                conn = (HttpURLConnection) new URL(params[0]).openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "text/html");
                conn.setRequestProperty( "charset", "utf-8");

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(params[1].getBytes( StandardCharsets.UTF_8 ));
                wr.flush();
                wr.close();

                InputStream in = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                Log.i(TAG, "doInBackground: inputStreamData"+inputStreamData);
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data = data.concat(String.valueOf(current));
                    Log.i(TAG, "doInBackground: status"+conn.getResponseMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        Log.i(TAG, "doInBackground: data"+data);
            return data;
        }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(data);
        Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
    }

}
