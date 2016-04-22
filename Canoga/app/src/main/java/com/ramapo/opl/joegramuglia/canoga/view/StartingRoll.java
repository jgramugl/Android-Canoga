package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Board;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

public class StartingRoll extends AppCompatActivity {

    private static Tournament tournament;
    private int size;

    /**
     * Creates the starting roll UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_roll);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");
        size = intent.getIntExtra("size", 0);
    }

    /**
     * The roll button on click listener
     * @param v - a view, The roll button
     */
    public void roll(View v) {
        TextView humanRoll1 = (TextView) findViewById(R.id.humanRoll1);
        TextView humanRoll2 = (TextView) findViewById(R.id.humanRoll2);
        TextView computerRoll1 = (TextView) findViewById(R.id.computerRoll1);
        TextView computerRoll2 = (TextView) findViewById(R.id.computerRoll2);

        int hRoll1 = tournament.rollDie();
        int hRoll2 = tournament.rollDie();
        int cRoll1 = tournament.rollDie();
        int cRoll2 = tournament.rollDie();

        humanRoll1.setText(String.valueOf(hRoll1));
        humanRoll2.setText(String.valueOf(hRoll2));
        computerRoll1.setText(String.valueOf(cRoll1));
        computerRoll2.setText(String.valueOf(cRoll2));

        int hSum = hRoll1 + hRoll2;
        int cSum = cRoll1 + cRoll2;

        TextView humanTotal = (TextView) findViewById(R.id.humanTotal);
        TextView computerTotal = (TextView) findViewById(R.id.computerTotal);
        humanTotal.setText("Total: " + String.valueOf(hSum));
        computerTotal.setText("Total: " + String.valueOf(cSum));
        humanTotal.setVisibility(View.VISIBLE);
        computerTotal.setVisibility(View.VISIBLE);

        if (hSum > cSum) {
            findViewById(R.id.humanWinner).setVisibility(View.VISIBLE);
            findViewById(R.id.rollButton).setVisibility(View.GONE);
            findViewById(R.id.continueButton).setVisibility(View.VISIBLE);
            if (tournament.getGame().getHumanScore() == 0 && tournament.getGame().getComputerScore() == 0) {
                tournament.newGame(size, true);
            } else if (tournament.getGame().getHumanScore() > 0) {
                if (tournament.getGame().isFirstTurnHuman()) {
                    // COMPUTER HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    computerBoard.cover(sumScore(tournament.getGame().getHumanScore()) - 1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                } else {
                    // HUMAN HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    humanBoard.cover(sumScore(tournament.getGame().getHumanScore())-1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                }
            } else {
                if (tournament.getGame().isFirstTurnHuman()) {
                    // COMPUTER HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    computerBoard.cover(sumScore(tournament.getGame().getComputerScore())-1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                } else {
                    // HUMAN HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    humanBoard.cover(sumScore(tournament.getGame().getComputerScore()) - 1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                }
            }
        }
        if (hSum < cSum) {
            findViewById(R.id.computerWinner).setVisibility(View.VISIBLE);
            findViewById(R.id.rollButton).setVisibility(View.GONE);
            findViewById(R.id.continueButton).setVisibility(View.VISIBLE);
            if (tournament.getGame().getHumanScore() == 0 && tournament.getGame().getComputerScore() == 0) {
                tournament.newGame(size, false);
            } else if (tournament.getGame().getHumanScore() > 0) {
                if (tournament.getGame().isFirstTurnHuman()) {
                    // COMPUTER HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    computerBoard.cover(sumScore(tournament.getGame().getHumanScore()) - 1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                } else {
                    // HUMAN HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    humanBoard.cover(sumScore(tournament.getGame().getHumanScore())-1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                }
            } else {
                if (tournament.getGame().isFirstTurnHuman()) {
                    // COMPUTER HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    computerBoard.cover(sumScore(tournament.getGame().getComputerScore())-1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                } else {
                    // HUMAN HANDICAP
                    Board humanBoard = new Board(size);
                    Board computerBoard = new Board(size);
                    humanBoard.cover(sumScore(tournament.getGame().getComputerScore()) - 1);
                    tournament.handicapGame(humanBoard, computerBoard, true);
                }
            }
        }
        else {
            Button roll = (Button) findViewById(R.id.rollButton);
            roll.setText("Reroll");
        }
    }

    /**
     * Sums the digits of a score for the handicap
     * @param score - int, The score to sum together
     * @return - int, The square to cover for the handicap
     */
    private int sumScore(int score) {
        int sum = 0;
        while (score > 0) {
            sum += score % 10;
            score /= 10;
        }
        while (sum > 9) {
            sum -= 9;
        }
        return sum;
    }

    /**
     * The continue button on click listener
     * @param v - a view, The continue button
     */
    public void cont(View v) {
        if (tournament.getGame().isNextTurnHuman()) {
            Intent intent = new Intent(this, HumanGame.class);
            intent.putExtra("tournament", tournament);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ComputerGame.class);
            intent.putExtra("tournament", tournament);
            startActivity(intent);
        }
    }
}
