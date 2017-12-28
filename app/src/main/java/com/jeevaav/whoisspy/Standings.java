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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;

public class Standings extends AppCompatActivity {

    private HashMap<String, Integer> players;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        getSupportActionBar().hide();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.7));

        Bundle bundle =  getIntent().getExtras();
        players = (HashMap<String, Integer>) bundle.getSerializable("players");
        Object[] sortedValues = players.entrySet().toArray();
        Arrays.sort(sortedValues, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((HashMap.Entry<String, Integer>) o2).getValue()
                        .compareTo(((HashMap.Entry<String, Integer>) o1).getValue());
            }
        });
        int counter = 1;
        for (Object val : sortedValues) {
            createPlayer(((HashMap.Entry<String, Integer>) val).getKey(),
                    ((HashMap.Entry<String, Integer>) val).getValue(), counter);
            counter++;
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPlayer(String playerName, Integer score, Integer rank) {
        LinearLayout playersList = findViewById(R.id.playersListStandings);

        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        ll.setLayoutParams(layoutParams);

        Typeface face = ResourcesCompat.getFont(getApplicationContext(),
                R.font.annie_use_your_telescope);

        // add rank
        final TextView playerRank = new TextView(getApplicationContext());
        playerRank.setText(rank.toString());
        playerRank.setTextAppearance(R.style.TextSize);
        playerRank.setTypeface(face);
        playerRank.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.2f));
        playerRank.setGravity(Gravity.CENTER);
        playerRank.setTextColor(Color.WHITE);
        ll.addView(playerRank);

        // add name
        final TextView name = new TextView(getApplicationContext());
        name.setText(playerName);
        name.setTextAppearance(R.style.TextSize);
        name.setTypeface(face);
        name.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));
        name.setGravity(Gravity.CENTER);
        name.setTextColor(Color.WHITE);
        ll.addView(name);

        // add score
        final TextView playerScore = new TextView(getApplicationContext());
        playerScore.setText(score.toString());
        playerScore.setTextAppearance(R.style.TextSize);
        playerScore.setTypeface(face);
        playerScore.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0.2f));
        playerScore.setGravity(Gravity.CENTER);
        playerScore.setTextColor(Color.WHITE);
        ll.addView(playerScore);

        playersList.addView((View) ll);
    }

    private void sortPlayers() {
        Object[] a = players.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((HashMap.Entry<String, Integer>) o2).getValue()
                        .compareTo(((HashMap.Entry<String, Integer>) o1).getValue());
            }
        });
    }

}