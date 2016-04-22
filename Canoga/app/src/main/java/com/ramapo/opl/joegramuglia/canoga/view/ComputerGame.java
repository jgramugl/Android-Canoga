package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

public class ComputerGame extends AppCompatActivity {

    private static Tournament tournament;
    private int rollTotal;

    /**
     * Creates the computer's turn UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_game);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");

        LinearLayout humanLayout = (LinearLayout) findViewById(R.id.humanBoard);
        for (int i = 0; i < tournament.getGame().getHumanBoard().getSize(); i++) {
            Button button = new Button(this);
            button.setTextSize(45);
            button.setText(String.valueOf(i + 1));
            button.setId(1000000 + i);
            if (tournament.getGame().getHumanBoard().isCovered(i)) {
                button.setBackgroundColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
            }
            humanLayout.addView(button);
        }

        LinearLayout computerLayout = (LinearLayout) findViewById(R.id.computerBoard);
        for (int i = 0; i < tournament.getGame().getComputerBoard().getSize(); i++) {
            Button button = new Button(this);
            button.setTextSize(45);
            button.setText(String.valueOf(i + 1));
            button.setId(2000000 + i);
            if (tournament.getGame().getComputerBoard().isCovered(i)) {
                button.setBackgroundColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
            }
            computerLayout.addView(button);
        }

        if (tournament.getGame().getComputer().rollDie(tournament.getGame().getComputerBoard(), tournament.getGame().getHumanBoard())) {
            rollTotal = tournament.rollDie();
        } else {
            rollTotal = tournament.rollDie() + tournament.rollDie();
        }

        TextView rollTotalTitle = (TextView) findViewById(R.id.rollTotalTitle);
        rollTotalTitle.setText("Total Rolled: " + String.valueOf(rollTotal));

        TextView logicTitle = (TextView) findViewById(R.id.logicTitle);
        String logic = tournament.getGame().getComputer().makeBestMove(tournament.getGame().getComputerBoard(), tournament.getGame().getHumanBoard(), rollTotal);
        logicTitle.setText(logic);
        logicTitle.setVisibility(View.VISIBLE);
    }

    /**
     * Take turn button on click listenter
     * @param v - a view, The take turn button
     */
    public void takeTurn(View v) {
        if (tournament.getGame().takeComputerTurn(rollTotal)) {
            if (tournament.getGame().getComputerBoardFlag()) {
                tournament.getGame().coverComputerBoard(tournament.getGame().getComputerCombination());
            } else {
                tournament.getGame().uncoverHumanBoard(tournament.getGame().getComputerCombination());
            }

            displayBoards();

            if (checkWin()) {
                v.setVisibility(View.GONE);
                findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(this, ComputerGame.class);
                intent.putExtra("tournament", tournament);
                startActivity(intent);
            }
        } else {
            v.setVisibility(View.GONE);
            findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Displays the boards
     */
    private void displayBoards() {
        for (int i = 0; i < tournament.getGame().getHumanBoard().getSize(); i++) {
            Button button = (Button) findViewById(1000000 + i);
            if (tournament.getGame().getHumanBoard().isCovered(i)) {
                button.setBackgroundColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
            }
        }
        for (int i = 0; i < tournament.getGame().getComputerBoard().getSize(); i++) {
            Button button = (Button) findViewById(2000000 + i);
            if (tournament.getGame().getComputerBoard().isCovered(i)) {
                button.setBackgroundColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    /**
     * Checks for the computer won
     * @return - boolean, True if the computer won
     */
    private boolean checkWin() {
        if (tournament.getGame().getHumanBoard().isAllUncovered() && tournament.getGame().getComputer().getAllowUncover() || tournament.getGame().getComputerBoard().isAllCovered()) {
            return true;
        }
        return false;
    }

    /**
     * The end turn button on click listener
     * @param v - a view, The end turn button
     */
    public void endTurn(View v) {
        if (checkWin()) {
            Intent intent = new Intent(this, ScoreScreen.class);
            intent.putExtra("tournament", tournament);
            startActivity(intent);
        } else {
            tournament.getGame().allowUncover();
            Intent intent = new Intent(this, SaveGame.class);
            intent.putExtra("tournament", tournament);
            startActivity(intent);
        }
    }
}
