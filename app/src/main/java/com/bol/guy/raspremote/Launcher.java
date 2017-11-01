package com.bol.guy.raspremote;


import android.util.Log;

import org.json.JSONObject;

public class Launcher extends Executor {

    public Launcher(String host) {
        super(host, "launch");
    }

    @Override
    public String generateJson(String param) {
        try {
            JSONObject cred = new JSONObject();
            cred.put("program", param);
            return cred.toString();
        }
        catch (Exception e) {
            Log.v("ErrorAPP", e.toString());
            return new String();
        }
    }
}
