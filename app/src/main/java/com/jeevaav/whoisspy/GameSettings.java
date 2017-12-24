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
    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        addPlayersOnClick();
    }

    public void addPlayersOnClick() {
        playersList = (LinearLayout) findViewById(R.id.playersInput);
        Button addButton = (Button) findViewById(R.id.addPlayerButton);
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
                            name.setHint("Name");
                            name.setLayoutParams(new LinearLayout.LayoutParams(0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f));
                            ll.addView(name);

                            // add button
                            Button add = new Button(getApplicationContext());
                            add.setText("Remove");
                            add.setId(10 + id);
                            add.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Button removeButton = (Button) findViewById(v.getId());
                                    LinearLayout parent = (LinearLayout) removeButton.getParent();
                                    LinearLayout superParent = (LinearLayout) parent.getParent();
                                    superParent.removeView(parent);
                                    id--;
                                }
                            });
                            add.setLayoutParams(new LinearLayout.LayoutParams(0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f));
                            ll.addView(add);
                            playersList.addView((View) ll);
                        }
                    }
                }
        );
    }

    public void removePlayersOnClick(View v) {
        Button removeButton = (Button) findViewById(v.getId());
        LinearLayout parent = (LinearLayout) removeButton.getParent();
        LinearLayout superParent = (LinearLayout) parent.getParent();
        superParent.removeView(parent);
        id--;
    }
}
