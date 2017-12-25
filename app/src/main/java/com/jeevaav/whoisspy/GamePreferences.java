package com.jeevaav.whoisspy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_preferences);
        backButtonListener();

        Bundle bundle =  getIntent().getExtras();

        ArrayList<String> players = bundle.getStringArrayList("players");
        TextView allPlayers = findViewById(R.id.allPlayers);
        String playersList = "";
        for (int i = 0; i < players.size(); i++) {
            playersList += players.get(i) + "\n";
        }
        playersList = playersList.trim();
        allPlayers.setText(playersList);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Bundle bundle =  getIntent().getExtras();
        ArrayList<String> players = bundle.getStringArrayList("players");
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
}
