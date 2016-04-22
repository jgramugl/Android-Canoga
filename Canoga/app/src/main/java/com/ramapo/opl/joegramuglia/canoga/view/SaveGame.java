package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Board;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveGame extends AppCompatActivity {

    public static Tournament tournament;

    /**
     * Creates the save game UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");
    }

    /**
     * The yes button on click listener
     * @param v - a view, The yes button
     */
    public void yes(View v) {
        saveGame();
        exit();
    }

    /**
     * The no button on click listener
     * @param v - a view, The no button
     */
    public void no(View v) {
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

    /**
     * Saves the current state of the game
     */
    private void saveGame() {
        File file = new File(getExternalFilesDir(null), "save.txt");
        try {
            FileOutputStream f = new FileOutputStream(file);
            f.write("Computer:\n".getBytes());
            f.write(("    Squares:" + serializeBoard(tournament.getGame().getComputerBoard()) + "\n").getBytes());
            f.write(("    Score: " + String.valueOf(tournament.getComputerScore()) + "\n").getBytes());
            f.write("\n".getBytes());
            f.write("Human:\n".getBytes());
            f.write(("    Squares:" + serializeBoard(tournament.getGame().getHumanBoard()) + "\n").getBytes());
            f.write(("    Score: " + String.valueOf(tournament.getHumanScore()) + "\n").getBytes());
            f.write("\n".getBytes());
            f.write(("First Turn: " + serializeTurn(tournament.getGame().isFirstTurnHuman()) + "\n").getBytes());
            f.write(("Next Turn: " + serializeTurn(tournament.getGame().isNextTurnHuman()) + "\n").getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes a board
     * @param board - a board, The board to serialize
     * @return - String, The serialized board
     */
    private String serializeBoard(Board board) {
        String concat = "";
        for (int i = 0; i < board.getSize(); i++) {
            if (board.isCovered(i)) {
                concat += " *";
            } else {
                concat += " " + (i + 1);
            }
        }
        return concat;
    }

    /**
     * Serializes a turn
     * @param turn - boolean, A turn to serialize
     * @return - String, The serialized turn
     */
    private String serializeTurn(boolean turn) {
        if (turn) {
            return "Human";
        }
        return "Computer";
    }

    /**
     * Exits the game
     */
    private void exit() {
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
