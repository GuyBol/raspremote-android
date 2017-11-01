package com.bol.guy.raspremote;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Executor {

    private String mHost;
    private String mCommand;

    public Executor(String host, String command) {
        mHost = host;
        mCommand = command;
    }

    public void execute(String param) {
        AsyncTaskRunner postReq = new AsyncTaskRunner();
        postReq.execute(mHost, mCommand, generateJson(param));
    }

    public String generateJson(String param) {
        return new String();
    }

    // Inner class
    class AsyncTaskRunner extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                // Extract parameters
                String host = new String("raspberrypi");
                String command = new String();
                String parameter = new String();
                int index = 0;
                for (String param : params) {
                    switch (index) {
                        case 0:
                            host = param;
                            break;
                        case 1:
                            command = param;
                            break;
                        case 2:
                            parameter = param;
                            break;
                    }
                    index++;
                }

                String url = "http://" + host + ":5000/raspremote/api/v1.0/" + command;
                URL object = new URL(url);

                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestMethod("POST");

                DataOutputStream localDataOutputStream = new DataOutputStream(con.getOutputStream());
                localDataOutputStream.writeBytes(parameter);
                localDataOutputStream.flush();
                localDataOutputStream.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String ligne;
                // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
                while ((ligne = reader.readLine()) != null) {
                    System.out.println(ligne);
                }
            } catch (Exception e) {
                Log.v("ErrorAPP", e.toString());
            }
            return "";
        }
    }
}
