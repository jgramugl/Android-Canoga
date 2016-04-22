package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

public class ScoreScreen extends AppCompatActivity {

    private static Tournament tournament;

    /**
     * Creates the score screen UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");

        TextView oldHuman = (TextView) findViewById(R.id.oldHuman);
        TextView oldComputer = (TextView) findViewById(R.id.oldComputer);
        TextView gameHuman = (TextView) findViewById(R.id.gameHuman);
        TextView gameComputer = (TextView) findViewById(R.id.gameComputer);
        TextView newHuman = (TextView) findViewById(R.id.newHuman);
        TextView newComputer = (TextView) findViewById(R.id.newComputer);

        tournament.getGame().calcScores();

        int oldHumanScore = tournament.getHumanScore();
        int oldComputerScore = tournament.getComputerScore();
        int gameHumanScore = tournament.getGame().getHumanScore();
        int gameComputerScore = tournament.getGame().getComputerScore();
        int newHumanScore = oldHumanScore + gameHumanScore;
        int newComputerScore = oldComputerScore + gameComputerScore;

        tournament.setHumanScore(newHumanScore);
        tournament.setComputerScore(newComputerScore);

        oldHuman.setText(String.valueOf(oldHumanScore));
        oldComputer.setText(String.valueOf(oldComputerScore));
        gameHuman.setText(String.valueOf(gameHumanScore));
        gameComputer.setText(String.valueOf(gameComputerScore));
        newHuman.setText(String.valueOf(newHumanScore));
        newComputer.setText(String.valueOf(newComputerScore));
    }

    /**
     * The yes button on click listener
     * @param v - a view, The yes button
     */
    public void yes(View v) {
        Intent intent = new Intent(this, BoardSize.class);
        intent.putExtra("tournament", tournament);
        startActivity(intent);
    }

    /**
     * The no button on click listener
     * @param v - a view, The no button
     */
    public void no(View v) {
        Intent intent = new Intent(this, WinnersScreen.class);
        if (tournament.getHumanScore() > tournament.getComputerScore()) {
            intent.putExtra("winner", "HUMAN WINS!");
        } else if (tournament.getHumanScore() < tournament.getComputerScore()) {
            intent.putExtra("winner", "COMPUTER WINS!");
        } else {
            // DRAW
            intent.putExtra("winner", "DRAW!");
        }
        startActivity(intent);
    }
}
