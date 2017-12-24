package com.jeevaav.whoisspy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickPlayButtonListener();
    }

    public void onClickPlayButtonListener() {
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.jeevaav.whoisspy.GameSettings");
                        startActivity(intent);
                    }
                }
        );
    }

}
