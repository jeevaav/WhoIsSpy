package com.jeevaav.whoisspy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class GameSettings extends AppCompatActivity {
    private static LinearLayout playersList;
    private  Button addButton;
    private static int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        addPlayersOnClick();
    }

    public void addPlayersOnClick() {
        playersList = (LinearLayout) findViewById(R.id.playersInput);
        addButton = (Button) findViewById(R.id.addPlayerButton);
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id < 5) {
                            id++;
                            LinearLayout ll = new LinearLayout(getApplicationContext());
                            ll.setOrientation(LinearLayout.HORIZONTAL);
                            ll.setId(id);
                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, 10, 0, 0);

                            // add text area
                            EditText name = new EditText(getApplicationContext());
                            name.setText("Name");
                            name.setLayoutParams(new LinearLayout.LayoutParams(730,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            ll.addView(name);

                            // add button
                            Button add = new Button(getApplicationContext());
                            add.setText("Remove");
                            ll.addView(add);
                            playersList.addView((View) ll);
                        }
                    }
                }
        );
    }
}
