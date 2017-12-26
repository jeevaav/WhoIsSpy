package com.jeevaav.whoisspy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePreferences extends AppCompatActivity {

    private ArrayList<String> players;
    private String numOfSpies;
    private String includeBlanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_preferences);
        backButtonListener();
        nextButtonListener();

        Bundle bundle =  getIntent().getExtras();

        players = bundle.getStringArrayList("activity_game_settings");
        TextView allPlayers = findViewById(R.id.allPlayers);
        String playersList = "";
        for (int i = 0; i < players.size(); i++) {
            playersList += players.get(i) + "\n";
        }
        playersList = playersList.trim();
        allPlayers.setText(playersList);
    }


    public void backButtonListener() {
        Button goButton = (Button) findViewById(R.id.backButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openMainActivity= new Intent(GamePreferences.this,
                                                                        GameSettings.class);
                        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(openMainActivity, 0);
                    }
                }
        );
    }

    public void nextButtonListener() {
        Button goButton = (Button) findViewById(R.id.nextButtonPreferences);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spies = (Spinner) findViewById(R.id.numOfSpies);
                        numOfSpies = spies.getSelectedItem().toString();

                        Spinner blank = (Spinner) findViewById(R.id.includeBlanks);
                        includeBlanks = blank.getSelectedItem().toString();

                        Intent intent = new Intent(GamePreferences.this,
                                MainGame.class);

                        intent.putExtra("numOfSpies", Integer.parseInt(numOfSpies));
                        intent.putExtra("includeBlanks", includeBlanks);
                        intent.putExtra("activity_game_settings", players);

                        startActivity(intent);
                    }
                }
        );
    }
}
