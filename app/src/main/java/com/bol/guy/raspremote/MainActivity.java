package com.bol.guy.raspremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private String mHost = new String("192.168.1.13");

    private Button mLaunchKodiButton;
    private Button mRebootButton;
    private Button mShutdownButton;
    private TextView mHostText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLaunchKodiButton = (Button) findViewById(R.id.launch_kodi_button);
        mRebootButton = (Button) findViewById(R.id.reboot_button);
        mShutdownButton = (Button) findViewById(R.id.shutdown_button);
        mHostText = (TextView) findViewById(R.id.host_text);

        mLaunchKodiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor launcher = new Launcher(mHostText.getText().toString());
                launcher.execute("kodi");
            }
        });

        mRebootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor commandLine = new CommandLine(mHost);
                commandLine.execute("sudo reboot");
            }
        });

        mShutdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor commandLine = new CommandLine(mHost);
                commandLine.execute("sudo shutdown -h now");
            }
        });
    }
}
