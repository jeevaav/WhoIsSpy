package com.jeevaav.whoisspy;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class GameSettings extends AppCompatActivity {
    private LinearLayout playersList;
    private int[] ids = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        for (int i = 0; i < 3; i++) {
            createPlayer(i);
        }

        onClickGoButtonListener();
        addPlayersOnClick();

    }

    public void addPlayersOnClick() {
        playersList = findViewById(R.id.playersInput);
        Button addButton = findViewById(R.id.addPlayerButton);
        addButton.setOnClickListener(
                new View.OnClickListener() {
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

    public void createPlayer(int id) {
        playersList = findViewById(R.id.playersInput);
        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setId(id);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);

        // add text area
        EditText name = new EditText(getApplicationContext());
        name.setId(100 + id);
        name.setHint("Name");
        name.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f));
        ll.addView(name);

        // add button
        Button remove = new Button(getApplicationContext());
        remove.setText("Remove");
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
        for (int i = 0; i < 7; i++) {
            if (ids[i] == 0) {
                return i;
            }
        }
        // no room for more players
        return -1;
    }

    public int numOfPlayers() {
        int counter = 0;
        for (int i = 0; i < 7; i++) {
            if (ids[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    public void onClickGoButtonListener() {
        Button goButton = (Button) findViewById(R.id.nextButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openMainActivity = new Intent(GameSettings.this,
                                                                    GamePreferences.class);
                        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(openMainActivity, 0);
                    }
                }
        );
    }
}
