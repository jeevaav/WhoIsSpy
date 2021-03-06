package com.jeevaav.whoisspy;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        onClickPlayButtonListener();
        onClickInstructionsButtonListener();
    }

    public void onClickPlayButtonListener() {
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.jeevaav.whoisspy.GameSettings");
                        startActivity(intent);
                        overridePendingTransition(R.anim.translatein, R.anim.translateout);
                    }
                }
        );
    }

    public void onClickInstructionsButtonListener() {
        playButton = (Button) findViewById(R.id.instructionButton);
        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.jeevaav.whoisspy.HowToPlay");
                        startActivity(intent);
                        overridePendingTransition(R.anim.translatein, R.anim.translateout);
                    }
                }
        );
    }





}
