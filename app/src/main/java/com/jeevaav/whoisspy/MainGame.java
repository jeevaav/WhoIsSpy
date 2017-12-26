package com.jeevaav.whoisspy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class MainGame extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<String, String>();
    private ArrayList<String> normalWords = new ArrayList<String>();
    private ArrayList<String> players;
    private HashMap<String, Boolean> seen = new HashMap<String, Boolean>();
    private String normalWord;
    private String spyWord;
    private int numOfSpies;
    private String includeBlanks;
    private int[] spies;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        populateWords();

        Bundle bundle =  getIntent().getExtras();
        players = bundle.getStringArrayList("activity_game_settings");
        numOfSpies = bundle.getInt("numOfSpies");
        includeBlanks = bundle.getString("includeBlanks");

        if (numOfSpies > (players.size() - 3)) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
            a_builder.setMessage("Number of activity_game_settings must be at least 2 more than number of spies")
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

        Random rand = new Random();
        int n = rand.nextInt(words.size() - 1);
        normalWord = normalWords.get(n);
        spyWord = words.get(normalWord);

        spies = rand.ints(0, players.size())
                                                .distinct().limit(numOfSpies).toArray();

        for (int i = 0; i < players.size(); i++) {
            if (containsInt(spies, i)) {
                createPlayer(players.get(i), true);
            } else {
                createPlayer(players.get(i), false);
            }
            seen.put(players.get(i), false);
        }

        playButtonListener();
    }

    private void populateWords() {
        words.put("Hospital", "Clinic");
        normalWords.add("Hospital");
        words.put("Hulk", "Spiderman");
        normalWords.add("Hulk");
        words.put("Teacher", "Headmaster");
        normalWords.add("Teacher");
        words.put("Computer", "Handphone");
        normalWords.add("Computer");
        words.put("Mathematics", "Science");
        normalWords.add("Mathematics");
    }

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
        checkWord.setTypeface(face);
        checkWord.setTextColor(Color.WHITE);
        checkWord.setBackgroundColor(Color.BLACK);
        checkWord.setTextSize(20);
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
                        intent.putExtra("activity_game_settings", players);
                        intent.putExtra("spies", spies);
                        intent.putExtra("includeBlanks", includeBlanks);
                        startActivity(intent);
                    }
                }
        );
    }

}
