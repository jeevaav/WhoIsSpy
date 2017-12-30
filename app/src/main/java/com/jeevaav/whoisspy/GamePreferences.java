package com.jeevaav.whoisspy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePreferences extends AppCompatActivity {

    private HashMap<String, Integer> players;
    private String numOfSpies;
    private String includeBlanks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_preferences);
        getSupportActionBar().hide();
        entranceAnimations();
        backButtonListener();
        nextButtonListener();

        Bundle bundle =  getIntent().getExtras();
        players = (HashMap<String, Integer>) bundle.getSerializable("players");
        TextView allPlayers = findViewById(R.id.allPlayers);
        String playersList = "";
        for (String key: players.keySet()) {
            playersList += key + "\n";
        }
        playersList = playersList.trim();
        allPlayers.setText(playersList);
        Typeface face = ResourcesCompat.getFont(getApplicationContext(),
                R.font.annie_use_your_telescope);
        allPlayers.setTypeface(face);
    }


    public void backButtonListener() {
        ImageButton goButton = (ImageButton) findViewById(R.id.backButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openMainActivity= new Intent(GamePreferences.this,
                                                                                GameSettings.class);
                        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(openMainActivity);
                    }
                }
        );
    }

    private void nextButtonListener() {
        ImageButton goButton = (ImageButton) findViewById(R.id.nextButtonPreferences);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spies = (Spinner) findViewById(R.id.numOfSpies);
                        numOfSpies = spies.getSelectedItem().toString();

                        CheckBox blank = (CheckBox) findViewById(R.id.includeBlanks);
                        if (blank.isChecked()) {
                            includeBlanks = "Yes";
                        } else {
                            includeBlanks = "No";
                        }

                        Intent intent = new Intent(GamePreferences.this,
                                MainGame.class);

                        intent.putExtra("numOfSpies", Integer.parseInt(numOfSpies));
                        intent.putExtra("includeBlanks", includeBlanks);
                        intent.putExtra("players", players);

                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                }
        );
    }

    private void entranceAnimations() {

        // players
        LinearLayout players = findViewById(R.id.playersLayout);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0,1);
        alphaAnimation1.setDuration(500);
        players.setAnimation(alphaAnimation1);
        alphaAnimation1.start();

        // spies
        LinearLayout spies = findViewById(R.id.spiesLayout);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0,1);
        alphaAnimation2.setDuration(500);
        alphaAnimation2.setStartOffset(300);
        spies.setAnimation(alphaAnimation2);
        alphaAnimation2.start();

        // blank
        LinearLayout blanks = findViewById(R.id.blanksLayout);
        AlphaAnimation alphaAnimation3 = new AlphaAnimation(0,1);
        alphaAnimation3.setDuration(500);
        alphaAnimation3.setStartOffset(600);
        blanks.setAnimation(alphaAnimation3);
        alphaAnimation3.start();
    }
}
