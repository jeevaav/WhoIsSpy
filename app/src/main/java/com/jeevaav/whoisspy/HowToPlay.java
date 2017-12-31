package com.jeevaav.whoisspy;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        getSupportActionBar().hide();
        onClickHomeButtonListener();
        onClickPlayButtonListener();
        Display display = getWindowManager().getDefaultDisplay();

        ImageButton home = (ImageButton) findViewById(R.id.homeButtonInstruction);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) home.getLayoutParams();
        int width = (display.getWidth() * 12) / 100;
        lp.width = width;
        home.setLayoutParams(lp);
    }

    private void onClickHomeButtonListener() {
        ImageButton goButton = findViewById(R.id.homeButtonInstruction);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HowToPlay.this,
                                MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.translatein, R.anim.translateout);
                    }
                }
        );
    }

    private void onClickPlayButtonListener() {
        Button goButton = findViewById(R.id.playButtonInstruction);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HowToPlay.this,
                                GameSettings.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.translatein, R.anim.translateout);
                    }
                }
        );
    }
}
