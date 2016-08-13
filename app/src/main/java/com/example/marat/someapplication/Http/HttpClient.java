package com.example.marat.someapplication.Http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Marat on 23.07.2016.
 */
public class HttpClient{
    Context context;
    ProgressDialog progDialog;
    StringBuffer response;
    BufferedReader in;

    public HttpClient(Context context,ProgressDialog progDialog){
        this.context = context;

        this.progDialog = progDialog;
    }
    //This  method create http request
    public boolean getHttp(String cityName, String status, AppCompatActivity activity){
        boolean isConnected = isOnline(activity);
        if(isConnected){
            HttpAsync http = new HttpAsync();
            http.setStatus(status);
            http.execute(cityName);
        }else{
            Toast.makeText(activity, "Network conection filed", Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }
    public void getData(String data, String status) {
    }
    //This method checks internet connection
    public static boolean isOnline(Activity activity){
        ConnectivityManager cm =(ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    class HttpAsync extends AsyncTask<String, Void, Void> {

        String status;

        public void setStatus(String status){
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog.show();
        }
        @Override
        protected Void doInBackground(String... str) {
            String url = UrlMaker.getUrlByCityName(str[0]);

            URL obj = null;
            URLConnection con = null;
            try {
                obj = new URL(url);
                con = (URLConnection) obj.openConnection();

                con.setDoOutput(true);
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = new StringBuffer();

            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            super.onPostExecute(res);
            try {
                response.append(in.readLine());
                //in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String str = response.toString();
            progDialog.dismiss();

            getData(str,status);
        }
    }
}
