package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ramapo.opl.joegramuglia.canoga.R;

public class WinnersScreen extends AppCompatActivity {

    /**
     * Creates the winners screen UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners_screen);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");

        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setText(winner);
    }

    /**
     * The continue button on click listener
     * @param v - a view, The continue button
     */
    public void cont(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
