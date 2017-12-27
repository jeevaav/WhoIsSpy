package com.jeevaav.whoisspy;


import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;


public class GameSettings extends AppCompatActivity {
    private LinearLayout playersList;
    private int[] ids = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int maxPlayers = 10;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        for (int i = 0; i < 3; i++) {
            createPlayer(i);
        }

        onClickNextButtonListener();
        addPlayersOnClick();
        onClickHomeButtonListener();
    }

    public void addPlayersOnClick() {
        playersList = findViewById(R.id.playersInput);
        Button addButton = findViewById(R.id.addPlayerButton);
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        int id = roomForPlayer();
                        if (id > -1) {
                            createPlayer(id);
                        }
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPlayer(int id) {
        playersList = findViewById(R.id.playersInput);
        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setId(id);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        ll.setLayoutParams(layoutParams);

        Typeface face = ResourcesCompat.getFont(getApplicationContext(),
                                                R.font.annie_use_your_telescope);

        // add text area
        EditText name = new EditText(getApplicationContext());
        name.setId(100 + id);
        name.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM );
        name.setHint("Name");
        name.setTypeface(face);
        name.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f));
        ll.addView(name);

        // add button
        Button remove = new Button(getApplicationContext());
        remove.setText("Remove");
        remove.setTextColor(Color.WHITE);
        remove.setBackgroundColor(Color.BLACK);
        remove.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM);
        remove.setTypeface(face);
        remove.setId(10 + id);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (numOfPlayers() > 3) {
                    Button removeButton = (Button) findViewById(v.getId());
                    LinearLayout parent = (LinearLayout) removeButton.getParent();
                    LinearLayout superParent = (LinearLayout) parent.getParent();
                    superParent.removeView(parent);
                    ids[(int) v.getId() - 10] = 0;
                } else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(GameSettings.this);
                    a_builder.setMessage("Minimum 3 players required!").setCancelable(true).setNegativeButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Error");
                    alert.show();
                }
            }
        });
        remove.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f));
        ll.addView(remove);

        playersList.addView((View) ll);
        ids[id] = 1;
    }

    public int roomForPlayer() {
        for (int i = 0; i < maxPlayers; i++) {
            if (ids[i] == 0) {
                return i;
            }
        }
        // no room for more activity_game_settings
        return -1;
    }

    public int numOfPlayers() {
        int counter = 0;
        for (int i = 0; i < maxPlayers; i++) {
            if (ids[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    public void onClickHomeButtonListener() {
        Button goButton = (Button) findViewById(R.id.homeButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GameSettings.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickNextButtonListener() {
        Button goButton = (Button) findViewById(R.id.nextButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag = false;
                        ArrayList<String> players = new ArrayList<String>();
                        for (int i = 0; i < maxPlayers; i++) {
                            if (ids[i] == 1) {
                                EditText player1   = (EditText) findViewById(100 + i);
                                String player = player1.getText().toString();
                                if (player.isEmpty() || player == null) {
                                    flag = true;
                                    break;
                                }
                                players.add(player);

                            }
                        }
                        if (flag) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(GameSettings.this);
                            a_builder.setMessage("Please enter valid player name")
                                    .setCancelable(true)
                                    .setNegativeButton("ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            });
                            AlertDialog gameOver = a_builder.create();
                            gameOver.setTitle("Error");
                            gameOver.show();

                        } else {

                            Intent intent = new Intent(GameSettings.this,
                                    GamePreferences.class);

                            intent.putExtra("players", players);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
