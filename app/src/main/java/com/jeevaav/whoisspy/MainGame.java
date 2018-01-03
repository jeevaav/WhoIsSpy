package com.jeevaav.whoisspy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.min;


public class MainGame extends AppCompatActivity {

    private HashMap<String, Integer> players;
    private HashMap<String, Boolean> seen = new HashMap<>();
    private String normalWord;
    private String spyWord;
    private int numOfSpies;
    private int numOfBlanks = 0;
    private String includeBlanks;
    private ArrayList<Integer> spies;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();
        Bundle bundle =  getIntent().getExtras();

        // get data from previous game preferences
        players = (HashMap<String, Integer>) bundle.getSerializable("players");
        numOfSpies = bundle.getInt("numOfSpies");
        includeBlanks = bundle.getString("includeBlanks");

        // create players, spies and blanks
        createPlayersSpiesBlanks();

        // button listeners
        playButtonListener();
        backButtonListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPlayer(final String playerName, final Boolean spy, final Boolean blank) {
        LinearLayout playersList = findViewById(R.id.playersList);
        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 20, 0, 20);
        ll.setLayoutParams(layoutParams);

        Typeface face = ResourcesCompat.getFont(getApplicationContext(),
                R.font.annie_use_your_telescope);

        // add button
        final Button checkWord = new Button(getApplicationContext());
        checkWord.setText(playerName);
        checkWord.setTextAppearance(R.style.TextSize);
        checkWord.setTypeface(face);
        checkWord.setTextColor(Color.WHITE);
        checkWord.setBackgroundResource(R.drawable.button_bg);
        checkWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!(seen.get(playerName))) {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
                    if (spy) {
                        a_builder.setMessage(spyWord).setCancelable(true).setNegativeButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                    } else if (blank) {
                        a_builder.setMessage("Oohh.. You've got a blank!").setCancelable(true).setNegativeButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                    } else {
                         a_builder.setMessage(normalWord).setCancelable(true).setNegativeButton("ok",
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

        // add animation
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(400);
        ll.startAnimation(alphaAnimation);

        playersList.addView(ll);
    }

    public void playButtonListener() {
        Button goButton = findViewById(R.id.playButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] playerNames = players.keySet().toArray(new String[players.size()]);
                        Random rand = new Random();
                        int n = rand.nextInt(playerNames.length) + 0;
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
                        a_builder.setMessage(playerNames[n].toUpperCase() +
                                " will start first ....\n").setCancelable(false)
                                .setNegativeButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                                Intent intent = new Intent(MainGame.this,
                                                        Result.class);
                                                intent.putExtra("players", players);
                                                intent.putExtra("spies", spies);
                                                intent.putExtra("includeBlanks", includeBlanks);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                            }
                                        });

                        AlertDialog whoStarts = a_builder.create();
                        whoStarts.show();

                    }
                }
        );
    }

    public void backButtonListener() {
        ImageButton goButton = findViewById(R.id.backButtonMainGame);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainGame.this,
                                GamePreferences.class);
                        intent.putExtra("players", players);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPlayersSpiesBlanks() {

        if (includeBlanks.equals("Yes")) {
            if (players.size() <= 10) {
                if (players.size() - numOfSpies == 2) {
                    numOfBlanks = 0;
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
                    a_builder.setMessage("No blanks will be included")
                            .setCancelable(true).setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Less players / More spies");
                    alert.show();
                } else {
                    numOfBlanks = min((players.size() - numOfSpies) / 3, 2);
                }
            } else {
                numOfBlanks = min((players.size() - numOfSpies) / 3, 3);
            }
        }

        if (numOfSpies > (players.size() - 2)) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainGame.this);
            a_builder.setMessage("Too many spies!")
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

        DatabaseHelper mydb = new DatabaseHelper(MainGame.this);
        Random rand = new Random();
        int n = rand.nextInt(7) + 6;
        Cursor result = mydb.getDatabaseData(n);

        while (result.moveToNext()) {
            normalWord = result.getString(1);
            spyWord = result.getString(2);
        }

        int[] spiesAndBlanks = rand.ints(0, players.size())
                .distinct().limit(numOfSpies + numOfBlanks).toArray();

        spies = new ArrayList<>();
        final ArrayList<Integer> blanks = new ArrayList<>();
        int i = 0;
        while (i < numOfSpies) {
            spies.add(spiesAndBlanks[i]);
            i++;
        }

        while (i < spiesAndBlanks.length) {
            blanks.add(spiesAndBlanks[i]);
            i++;
        }

        final String[] playerNames = players.keySet().toArray(new String[players.size()]);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int counter = 0;
            @Override
            public void run() {
                if (spies.contains(counter)) {
                    createPlayer(playerNames[counter], true, false);
                } else if (blanks.contains(counter)) {
                    createPlayer(playerNames[counter], false, true);
                } else {
                    createPlayer(playerNames[counter], false, false);
                }
                seen.put(playerNames[counter], false);
                counter++;
                if (counter == playerNames.length) {
                    return;
                }
                handler.postDelayed(this, 50);
            }
        });
    }

}
