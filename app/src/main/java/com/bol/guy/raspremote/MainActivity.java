package com.bol.guy.raspremote;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button mLaunchKodiButton;
    private Button mRebootButton;
    private Button mShutdownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLaunchKodiButton = (Button) findViewById(R.id.launch_kodi_button);
        mRebootButton = (Button) findViewById(R.id.reboot_button);
        mShutdownButton = (Button) findViewById(R.id.shutdown_button);

        mLaunchKodiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner postReq = new AsyncTaskRunner();
                postReq.execute("kodi &");
            }
        });

        mRebootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner postReq = new AsyncTaskRunner();
                postReq.execute("sudo reboot");
            }
        });

        mShutdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner postReq = new AsyncTaskRunner();
                postReq.execute("sudo shutdown -h now");
            }
        });
    }
}

class AsyncTaskRunner extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            String url="http://192.168.1.16:5000/raspremote/api/v1.0/cli";
            URL object=new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            JSONObject cred = new JSONObject();
            for (String command : params) {
                cred.put("command", command);
                break;
            }

            DataOutputStream localDataOutputStream = new DataOutputStream(con.getOutputStream());
            localDataOutputStream.writeBytes(cred.toString());
            localDataOutputStream.flush();
            localDataOutputStream.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String ligne;
            // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
            }
        }
        catch (Exception e){
            Log.v("ErrorAPP",e.toString());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}