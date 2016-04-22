package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Board;
import com.ramapo.opl.joegramuglia.canoga.model.Game;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

import java.util.Stack;
import java.util.Vector;

public class HumanGame extends AppCompatActivity {

    private static Tournament tournament;
    private Vector<Integer> currentHumanColors;
    private Vector<Integer> currentComputerColors;
    private int rollTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_game);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");

        currentHumanColors = new Vector<Integer>();
        currentComputerColors = new Vector<Integer>();

        if (!tournament.getGame().getHumanBoard().is7UpCovered()) {
            findViewById(R.id.rollDieButton).setVisibility(View.GONE);
        } else {
            findViewById(R.id.rollDieButton).setVisibility(View.VISIBLE);
        }


        LinearLayout humanLayout = (LinearLayout) findViewById(R.id.humanBoard);
        for (int i = 0; i < tournament.getGame().getHumanBoard().getSize(); i++) {
            Button button = new Button(this);
            button.setTextSize(45);
            button.setText(String.valueOf(i + 1));
            button.setId(1000000 + i);
            if (tournament.getGame().getHumanBoard().isCovered(i)) {
                button.setBackgroundColor(Color.BLACK);
                currentHumanColors.add(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
                currentHumanColors.add(Color.LTGRAY);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    int position = Integer.parseInt(b.getText().toString()) - 1;
                    ColorDrawable background = (ColorDrawable) v.getBackground();
                    int color = background.getColor();
                    if (findColor(currentComputerColors, Color.GREEN)) {
                        switch (color) {
                            case(Color.LTGRAY):
                            case(Color.YELLOW):
                            case(Color.BLACK):
                                v.setBackgroundColor(Color.RED);
                                currentHumanColors.setElementAt(Color.RED, position);
                                break;
                            case(Color.RED):
                            case(Color.GREEN):
                                if (tournament.getGame().getHumanBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentHumanColors.setElementAt(Color.BLACK, position);
                                }
                                else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentHumanColors.setElementAt(Color.LTGRAY, position);
                                }
                                break;
                        }
                    }
                    else {
                        switch(color) {
                            case(Color.LTGRAY):
                            case(Color.YELLOW):
                                v.setBackgroundColor(Color.GREEN);
                                currentHumanColors.setElementAt(Color.GREEN, position);
                                break;
                            case(Color.BLACK):
                                v.setBackgroundColor(Color.RED);
                                currentHumanColors.setElementAt(Color.RED, position);
                                break;
                            case(Color.RED):
                                if (tournament.getGame().getHumanBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentHumanColors.setElementAt(Color.BLACK, position);
                                }
                                else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentHumanColors.setElementAt(Color.LTGRAY, position);
                                }
                                break;
                            case(Color.GREEN):
                                if (tournament.getGame().getHumanBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentHumanColors.setElementAt(Color.BLACK, position);
                                }
                                else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentHumanColors.setElementAt(Color.LTGRAY, position);
                                }
                                if (!findColor(currentHumanColors, Color.GREEN)) {
                                    for (int i = 0; i < currentComputerColors.size(); i++) {
                                        if (currentComputerColors.get(i) == Color.RED && tournament.getGame().getComputerBoard().isCovered(i)) {
                                            Button computerButton = (Button) findViewById(2000000 + i);
                                            computerButton.setBackgroundColor(Color.GREEN);
                                            currentComputerColors.setElementAt(Color.GREEN, i);
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
            });
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
                currentComputerColors.add(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.LTGRAY);
                currentComputerColors.add(Color.LTGRAY);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    int position = Integer.parseInt(b.getText().toString()) - 1;
                    ColorDrawable background = (ColorDrawable) v.getBackground();
                    int color = background.getColor();
                    if (findColor(currentHumanColors, Color.GREEN)) {
                        switch (color) {
                            case(Color.LTGRAY):
                            case(Color.YELLOW):
                            case(Color.BLACK):
                                v.setBackgroundColor(Color.RED);
                                currentComputerColors.setElementAt(Color.RED, position);
                                break;
                            case(Color.RED):
                            case(Color.GREEN):
                                if (tournament.getGame().getComputerBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentComputerColors.setElementAt(Color.BLACK, position);
                                }
                                else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentComputerColors.setElementAt(Color.LTGRAY, position);
                                }
                                break;
                        }

                    }
                    else {
                        switch (color) {
                            case (Color.BLACK):
                            case (Color.YELLOW):
                                v.setBackgroundColor(Color.GREEN);
                                currentComputerColors.setElementAt(Color.GREEN, position);
                                break;
                            case (Color.LTGRAY):
                                v.setBackgroundColor(Color.RED);
                                currentComputerColors.setElementAt(Color.RED, position);
                                break;
                            case (Color.RED):
                                if (tournament.getGame().getComputerBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentComputerColors.setElementAt(Color.BLACK, position);
                                } else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentComputerColors.setElementAt(Color.LTGRAY, position);
                                }
                                break;
                            case (Color.GREEN):
                                if (tournament.getGame().getComputerBoard().isCovered(position)) {
                                    v.setBackgroundColor(Color.BLACK);
                                    currentComputerColors.setElementAt(Color.BLACK, position);
                                } else {
                                    v.setBackgroundColor(Color.LTGRAY);
                                    currentComputerColors.setElementAt(Color.LTGRAY, position);
                                }
                                if (!findColor(currentComputerColors, Color.GREEN)) {
                                    for (int i = 0; i < currentHumanColors.size(); i++) {
                                        if (currentHumanColors.get(i) == Color.RED && !tournament.getGame().getHumanBoard().isCovered(i)) {
                                            Button humanButton = (Button) findViewById(1000000 + i);
                                            humanButton.setBackgroundColor(Color.GREEN);
                                            currentHumanColors.setElementAt(Color.GREEN, i);
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
            });
            computerLayout.addView(button);
        }
    }

    private boolean findColor(Vector<Integer> currentColors, int color) {
        for (int i = 0; i < currentColors.size(); i++) {
            if (currentColors.get(i) == color) {
                return true;
            }
        }
        return false;
    }

    public void confirm(View v) {
        TextView invalidSelection = (TextView) findViewById(R.id.invalidSelection);
        TextView invalidCombination = (TextView) findViewById(R.id.invalidCombination);
        invalidSelection.setVisibility(View.GONE);
        invalidCombination.setVisibility(View.GONE);
        if (findColor(currentHumanColors, Color.RED) || findColor(currentComputerColors, Color.RED)) {
            invalidSelection.setVisibility(View.VISIBLE);
        } else {
            Stack<Integer> humanCombination = new Stack<Integer>();
            for (int i = currentHumanColors.size()-1; i >= 0; i--) {
                if (currentHumanColors.get(i) == Color.GREEN) {
                    humanCombination.push(i);
                }
            }

            Stack<Integer> computerCombination = new Stack<Integer>();
            for (int i = currentComputerColors.size()-1; i >= 0; i--) {
                if (currentComputerColors.get(i) == Color.GREEN) {
                    computerCombination.push(i);
                }
            }

            if (!humanCombination.isEmpty() && !computerCombination.isEmpty()) {
                invalidSelection.setVisibility(View.VISIBLE);
            } else {
                Game game = tournament.getGame();
                if (!humanCombination.isEmpty()) {
                    if (game.takeHumanTurn(rollTotal, humanCombination, true)) {
                        game.coverHumanBoard(humanCombination);
                        displayBoards();
                        checkWin();
                    } else {
                        findViewById(R.id.invalidCombination).setVisibility(View.VISIBLE);
                    }
                } else {
                    if(game.takeHumanTurn(rollTotal, computerCombination, false)) {
                        game.uncoverComputerBoard(computerCombination);
                        displayBoards();
                        checkWin();
                    } else {
                        findViewById(R.id.invalidCombination).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void displayBoards() {
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

    public void checkWin() {
        if (!tournament.getGame().getHuman().getAllowUncover()) {
            if (tournament.getGame().getHumanBoard().isAllCovered()) {
                findViewById(R.id.confirmButton).setVisibility(View.GONE);
                findViewById(R.id.hintButton).setVisibility(View.GONE);
                findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);
            }
            else {
                Intent intent = new Intent(this, HumanGame.class);
                intent.putExtra("tournament", tournament);
                startActivity(intent);
            }
        } else {
            if (tournament.getGame().getHumanBoard().isAllCovered() || tournament.getGame().getComputerBoard().isAllUncovered()) {
                findViewById(R.id.confirmButton).setVisibility(View.GONE);
                findViewById(R.id.hintButton).setVisibility(View.GONE);
                findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(this, HumanGame.class);
                intent.putExtra("tournament", tournament);
                startActivity(intent);
            }
        }
    }

    public void hint(View v) {
        TextView logicTitle = (TextView) findViewById(R.id.logicTitle);
        String logic = "";
        if (tournament.getGame().getHuman().getAllowUncover()) {
            logic = tournament.getGame().getHuman().makeBestMove(tournament.getGame().getHumanBoard(), tournament.getGame().getComputerBoard(), rollTotal);
        } else {
            logic = tournament.getGame().getHuman().makeBestMove(tournament.getGame().getHumanBoard(), new Board(0), rollTotal);
        }
        logicTitle.setText(logic);
        logicTitle.setVisibility(View.VISIBLE);
        Stack<Integer> combination = tournament.getGame().getHumanCombination();
        if (!combination.empty()) {
            if (tournament.getGame().getHumanBoardFlag()) {
                for (int i = 0; i < combination.size(); i++) {
                    Button button = (Button) findViewById(1000000 + combination.get(i));
                    button.setBackgroundColor(Color.YELLOW);
                    currentHumanColors.setElementAt(Color.YELLOW, combination.get(i));
                }
            } else {
                for (int i = 0; i < combination.size(); i++) {
                    Button button = (Button) findViewById(2000000 + combination.get(i));
                    button.setBackgroundColor(Color.YELLOW);
                    currentHumanColors.setElementAt(Color.YELLOW, combination.get(i));
                }
            }
        }
    }

    public void rollDie(View v) {
        v.setVisibility(View.GONE);
        findViewById(R.id.rollDiceButton).setVisibility(View.GONE);

        rollTotal = tournament.rollDie();

        displayButtons();
    }

    public void rollDice(View v) {
        findViewById(R.id.rollDieButton).setVisibility(View.GONE);
        v.setVisibility(View.GONE);

        rollTotal = tournament.rollDie() + tournament.rollDie();

        displayButtons();
    }

    private void displayButtons() {
        TextView rollTotalTitle = (TextView) findViewById(R.id.rollTotalTitle);
        rollTotalTitle.setText("Total Rolled: " + String.valueOf(rollTotal));

        tournament.getGame().getHuman().makeBestMove(tournament.getGame().getHumanBoard(), tournament.getGame().getComputerBoard(), rollTotal);
        Stack<Integer> combination = tournament.getGame().getHumanCombination();
        if (!combination.empty()) {
            findViewById(R.id.confirmButton).setVisibility(View.VISIBLE);
            findViewById(R.id.hintButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.endTurnButton).setVisibility(View.VISIBLE);
        }
    }

    public void endTurn(View v) {
        if (tournament.getGame().getHumanBoard().isAllCovered() || (tournament.getGame().getComputerBoard().isAllUncovered() && tournament.getGame().getHuman().getAllowUncover())) {
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
