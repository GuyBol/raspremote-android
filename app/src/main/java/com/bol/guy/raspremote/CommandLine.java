package com.bol.guy.raspremote;


import android.util.Log;

import org.json.JSONObject;

public class CommandLine extends Executor {

    public CommandLine(String host) {
        super(host, "cli");
    }

    @Override
    public String generateJson(String param) {
        try {
            JSONObject cred = new JSONObject();
            cred.put("command", param);
            return cred.toString();
        }
        catch (Exception e) {
            Log.v("ErrorAPP", e.toString());
            return new String();
        }
    }
}
