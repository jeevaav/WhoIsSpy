package com.jeevaav.whoisspy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Result extends AppCompatActivity {
    private HashMap<String, Integer> players;
    private int numOfSpies;
    private String includeBlanks;
    private int[] spies;
    private int spiesAlive;
    private int playersAlive;
    private ArrayList<String> playersAliveNames;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Display display = getWindowManager().getDefaultDisplay();
        ImageButton home = (ImageButton) findViewById(R.id.homeButtonResult);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) home.getLayoutParams();
        int width = (display.getWidth() * 12) / 100;
        lp.width = width;

        Bundle bundle =  getIntent().getExtras();
        players = (HashMap<String, Integer>) bundle.getSerializable("players");
        spies = bundle.getIntArray("spies");
        numOfSpies = spies.length;
        playersAlive = players.size() - numOfSpies;
        spiesAlive = spies.length;
        includeBlanks = bundle.getString("includeBlanks");

        playersAliveNames = new ArrayList<String>();


        int counter = 0;
        for (String key: players.keySet()) {
            if (containsInt(spies, counter)) {
                createPlayer(key, true);
            } else {
                createPlayer(key, false);
            }
            playersAliveNames.add(key);
            counter++;
        }

        onClickHomeButtonListener();
        onClickRestartButtonListener();
        onClickRankButtonListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPlayer(final String playerName, final Boolean spy) {
        LinearLayout playersList = findViewById(R.id.playersResultList);
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
        checkWord.setId(playerName.hashCode());
        checkWord.setText(playerName);
        checkWord.setTextAppearance(R.style.TextSize);
        checkWord.setTypeface(face);
        checkWord.setTextColor(Color.WHITE);
        checkWord.setBackgroundColor(Color.BLACK);

        checkWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Result.this);
                if (!(spy)) {
                    a_builder.setMessage("You got the wrong person!").setCancelable(true)
                            .setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    checkWord.setTextColor(Color.RED);
                    playersAlive--;

                } else {
                    a_builder.setMessage("Spy busted!").setCancelable(true)
                            .setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    checkWord.setTextColor(Color.BLUE);
                    spiesAlive--;

                }
                playersAliveNames.remove(playerName);


                AlertDialog.Builder gameOverAlert = new AlertDialog.Builder(Result.this);
                if ((playersAlive < spiesAlive && numOfSpies > 1) ||
                        (playersAlive == spiesAlive && numOfSpies == 1)) {
                    updatePoints();
                    cancelAllButtons();
                    gameOverAlert.setMessage("The spy team has won!")
                            .setCancelable(true)
                            .setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog gameOver = gameOverAlert.create();
                    gameOver.show();
                    TextView view = findViewById(R.id.playSmartSub);
                    view.setText("Game Over");
                }

                if (spiesAlive == 0) {
                    updatePoints();
                    cancelAllButtons();
                    gameOverAlert.setMessage("The normal team has won!")
                            .setCancelable(true)
                            .setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog gameOver = gameOverAlert.create();
                    gameOver.show();
                    TextView view = findViewById(R.id.playSmartSub);
                    view.setText("Game Over");
                }
                AlertDialog alert = a_builder.create();
                alert.show();

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

    private void restartGame() {
        Intent openMainActivity= new Intent(Result.this,
                MainGame.class);
        openMainActivity.putExtra("players", players);
        openMainActivity.putExtra("numOfSpies", numOfSpies);
        openMainActivity.putExtra("includeBlanks", includeBlanks);
        startActivity(openMainActivity);
    }

    private void onClickHomeButtonListener() {
        ImageButton goButton = (ImageButton) findViewById(R.id.homeButtonResult);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Result.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void onClickRestartButtonListener() {
        Button goButton = (Button) findViewById(R.id.restartButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restartGame();
                    }
                }
        );
    }

    private void updatePoints() {
        for (String player: playersAliveNames) {
            int currPoint = players.get(player);
            players.put(player, currPoint + 10);
        }
    }

    private void onClickRankButtonListener() {
        Button goButton = (Button) findViewById(R.id.rankButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Result.this,
                                Standings.class);
                        intent.putExtra("players", players);
                        startActivity(intent);
                    }
                }
        );
    }

    private void cancelAllButtons() {
        for (String player : players.keySet()) {
            Button button = findViewById(player.hashCode());
            button.setEnabled(false);
        }
    }
}
