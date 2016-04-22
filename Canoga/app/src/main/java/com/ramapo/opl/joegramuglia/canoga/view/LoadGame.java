package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Board;
import com.ramapo.opl.joegramuglia.canoga.model.Computer;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.Vector;

public class LoadGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
    }

    public void enter(View v) {
        EditText fileText = (EditText) findViewById(R.id.fileText);
        String input = fileText.getText().toString();
        Tournament tournament = loadTournament(input);
        Stack<Integer> rolls = new Stack<Integer>();
        switch(input) {
            case("case1"):
            case("case2"):
            case("case3"):
            case("case4"):
            case("case5"):
                rolls = loadRolls(input);
        }
        tournament.setRolls(rolls);
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

    private Stack<Integer> loadRolls(String input) {
        Stack<Integer> rolls = new Stack<Integer>();
        File file = new File(getExternalFilesDir(null), input + "Rolls.txt");
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = reader.readLine();
                while((line = reader.readLine()) != null) {
                    String[] numbers = line.split("\\s+");
                    for (int i = 0; i < numbers.length; i++) {
                        rolls.insertElementAt(Integer.parseInt(numbers[i]), 0);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rolls;
    }

    public void cancel(View v) {
        finish();
    }

    private Tournament loadTournament(String fileName) {
        Vector<String> info = getTournamentInfo(fileName);

        // Computer's info
        Board computerBoard = createBoard(info.get(0));
        int computerScore = createScore(info.get(1));

        // Human's info
        Board humanBoard = createBoard(info.get(2));
        int humanScore = createScore(info.get(3));

        // Game info
        boolean firstTurnHuman = createBool(info.get(4));
        boolean nextTurnHuman = createBool(info.get(5));

        return new Tournament(humanBoard, humanScore, computerBoard, computerScore, firstTurnHuman, nextTurnHuman);
    }

    private Board createBoard(String s) {
        s = s.trim();
        Vector<Boolean> board = new Vector<Boolean>();
        for (int i = 0; i < s.length(); i++) {
            switch(s.charAt(i)) {
                case('*'):
                    board.add(true);
                    break;
                case('1'):
                    switch(s.charAt(i+1)) {
                        case('0'):
                        case('1'):
                        case('2'):
                            i++;
                    }
                case('2'):
                case('3'):
                case('4'):
                case('5'):
                case('6'):
                case('7'):
                case('8'):
                case('9'):
                    board.add(false);
                    break;
            }
        }
        return new Board(board);
    }

    private int createScore(String s) {
        return Integer.parseInt(s.trim());
    }

    private boolean createBool(String s) {
        if (s.contains("Human")) {
            return true;
        }
        return false;
    }

    private Vector<String> getTournamentInfo(String fileName) {
        Vector<String> info = new Vector<String>();
        File file = new File(getExternalFilesDir(null), fileName + ".txt");
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                int count = 0;
                while((line = reader.readLine()) != null) {
                    switch(count) {
                        case(1):
                            // COMPUTER'S BOARD
                            String computerBoard = line.substring(line.indexOf(":")+1, line.length());
                            info.add(computerBoard);
                            break;
                        case(2):
                            // COMPUTER'S SCORE
                            String computerScore = line.substring(line.indexOf(":")+1, line.length());
                            info.add(computerScore);
                            break;
                        case(5):
                            // HUMAN'S BOARD
                            String humanBoard = line.substring(line.indexOf(":")+1, line.length());
                            info.add(humanBoard);
                            break;
                        case(6):
                            // HUMAN'S SCORE
                            String humanScore = line.substring(line.indexOf(":")+1, line.length());
                            info.add(humanScore);
                            break;
                        case(8):
                            // FIRST TURN
                            String first = line.substring(line.indexOf(":")+1, line.length());
                            info.add(first);
                            break;
                        case(9):
                            // NEXT TURN
                            String next = line.substring(line.indexOf(":")+1, line.length());
                            info.add(next);
                            break;
                    }
                    count++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return info;
    }
}