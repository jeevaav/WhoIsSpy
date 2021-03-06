package com.jeevaav.whoisspy;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;


public class GameSettings extends AppCompatActivity {
    private LinearLayout playersList;
    private int[] ids = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int maxPlayers = 15;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        getSupportActionBar().hide();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int i = 5;
            @Override
            public void run() {
                createPlayer(i);
                i--;
                if (i == 0) {
                    return;
                }
                handler.postDelayed(this, 30);
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) home.getLayoutParams();
        int width = (display.getWidth() * 12) / 100;
        lp.width = width;
        home.setLayoutParams(lp);

        onClickNextButtonListener();
        addPlayersOnClick();
        onClickHomeButtonListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void addPlayersOnClick() {
        playersList = findViewById(R.id.playersInput);
        final ImageButton addButton = findViewById(R.id.addPlayerButton);
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        int id = roomForPlayer();
                        if (id > -1) {
                            createPlayer(id);
                        } else {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(GameSettings.this);
                            a_builder.setMessage("Maximum number of players added!").setCancelable(true).setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.show();
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
        name.setTextAppearance(R.style.TextSize);
        name.setHint("Name");
        name.setTypeface(face);
        name.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.9f));
        ll.addView(name);

        // remove button
        ImageButton remove = new ImageButton(GameSettings.this);
        remove.setImageResource(R.drawable.remove);
        remove.setScaleType(ImageView.ScaleType.FIT_CENTER);
        remove.setBackgroundResource(R.drawable.custom_red_ripple);
        remove.setId(10 + id);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (numOfPlayers() > 3) {
                    ImageButton removeButton = (ImageButton) findViewById(v.getId());
                    final LinearLayout parent = (LinearLayout) removeButton.getParent();
                    final LinearLayout superParent = (LinearLayout) parent.getParent();
                    parent.animate().translationX(-1000).
                            alpha(0).setDuration(300).
                            withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            superParent.removeView(parent);
                        }
                    });

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
                ViewGroup.LayoutParams.MATCH_PARENT, 0.1f));
        ll.addView(remove);

        // add animation
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(400);
        ll.startAnimation(alphaAnimation);

        // add the view to the main view
        playersList.addView((View) ll);
        ids[id] = 1;
    }

    public int roomForPlayer() {
        for (int i = 0; i < maxPlayers; i++) {
            if (ids[i] == 0) {
                return i;
            }
        }
        // no room for more players
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
        ImageButton goButton = (ImageButton) findViewById(R.id.homeButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GameSettings.this,
                                MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.translatein, R.anim.translateout);
                    }
                }
        );
    }

    public void onClickNextButtonListener() {
        ImageButton goButton = (ImageButton) findViewById(R.id.nextButton);
        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag = false;
                        boolean samePlayerFlag = false;
                        final HashMap<String, Integer> players = new HashMap<String, Integer>();
                        for (int i = 0; i < maxPlayers; i++) {
                            if (ids[i] == 1) {
                                EditText player1   = (EditText) findViewById(100 + i);
                                String player = player1.getText().toString();
                                if (player.isEmpty() || player == null) {
                                    flag = true;
                                    break;
                                } else if (players.containsKey(player)) {
                                    samePlayerFlag = true;
                                }
                                players.put(player, 0);

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

                        } else if (samePlayerFlag) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(GameSettings.this);
                            a_builder.setMessage("Two or more players can't have the same name.")
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
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                    }
                }
        );
    }
}
