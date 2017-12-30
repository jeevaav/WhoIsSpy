package com.jeevaav.whoisspy;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onClickHomeButtonListener();
        Display display = getWindowManager().getDefaultDisplay();
        ImageButton home = (ImageButton) findViewById(R.id.homeButtonInstructions);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) home.getLayoutParams();
        int width = (display.getWidth() * 12) / 100;
        lp.width = width;
        home.setLayoutParams(lp);
    }

    public void onClickHomeButtonListener() {
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButtonInstructions);
        homeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HowToPlay.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
