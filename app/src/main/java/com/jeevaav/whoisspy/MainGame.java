package com.jeevaav.whoisspy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class MainGame extends AppCompatActivity {

    private HashMap<String, Integer> players;
    private HashMap<String, Boolean> seen = new HashMap<String, Boolean>();
    private String normalWord;
    private String spyWord;
    private int numOfSpies;
    private String includeBlanks;
    private int[] spies;
    private DatabaseHelper mydb;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Bundle bundle =  getIntent().getExtras();
        players = (HashMap<String, Integer>) bundle.getSerializable("players");
        numOfSpies = bundle.getInt("numOfSpies");
        includeBlanks = bundle.getString("includeBlanks");

        if (numOfSpies > (players.size() - 3)) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
            a_builder.setMessage("Number of players must be at least 2 more than number of spies")
                    .setCancelable(true).setNegativeButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            finish();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("Error");
            alert.show();
        }

        mydb = new DatabaseHelper(MainGame.this);
        Random rand = new Random();
        int n = rand.nextInt(7) + 6;
        Cursor result = mydb.getDatabaseData(n);

        while (result.moveToNext()) {
            normalWord = result.getString(1).toString();
            spyWord = result.getString(2).toString();
        }

        spies = rand.ints(0, players.size())
                                                .distinct().limit(numOfSpies).toArray();


        int counter = 0;
        for (String key : players.keySet()) {
            if (containsInt(spies, counter)) {
                createPlayer(key, true);
            } else {
                createPlayer(key, false);
            }
            seen.put(key, false);
            counter++;
        }

        playButtonListener();
        backButtonListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPlayer(final String playerName, final Boolean spy) {
        LinearLayout playersList = findViewById(R.id.playersList);
        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        ll.setLayoutParams(layoutParams);

        Typeface face = ResourcesCompat.getFont(getApplicationContext(),
                R.font.annie_use_your_telescope);

        // add button
        final Button checkWord = new Button(getApplicationContext());
        checkWord.setText(playerName);
        checkWord.setTextAppearance(R.style.TextSize);
        checkWord.setTypeface(face);
        checkWord.setTextColor(Color.WHITE);
        checkWord.setBackgroundColor(Color.BLACK);
        checkWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!(seen.get(playerName))) {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
                    if (!(spy)) {
                        a_builder.setMessage(normalWord).setCancelable(true).setNegativeButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                    } else {
                        a_builder.setMessage(spyWord).setCancelable(true).setNegativeButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                    }
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Your word is ...");
                    alert.show();
                    seen.put(playerName, true);
                    checkWord.setTextColor(Color.RED);
                }
            }
        });

        checkWord.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        ll.addView(checkWord);
        playersList.addView((View) ll);
    }

    private boolean containsInt(final int[] array, int val) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == val) {
                return true;
            }
        }
        return false;
    }

    public void playButtonListener() {
        Button goButton = (Button) findViewById(R.id.playButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainGame.this,
                                Result.class);
                        intent.putExtra("players", players);
                        intent.putExtra("spies", spies);
                        intent.putExtra("includeBlanks", includeBlanks);
                        startActivity(intent);
                    }
                }
        );
    }

    public void backButtonListener() {
        ImageButton goButton = (ImageButton) findViewById(R.id.backButtonMainGame);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainGame.this,
                                GamePreferences.class);
                        intent.putExtra("players", players);
                        startActivity(intent);
                    }
                }
        );
    }

}
