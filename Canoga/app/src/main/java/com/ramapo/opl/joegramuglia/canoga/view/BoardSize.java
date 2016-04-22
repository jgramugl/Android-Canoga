package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

import java.io.Serializable;

public class BoardSize extends AppCompatActivity {

    private static Tournament tournament;

    /**
     * Creates the board size UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_size);
        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("tournament");
    }

    /**
     * The enter button on click listener
     * @param v - a view, The enter button
     */
    public void enter(View v) {
        // Check for valid board size
        findViewById(R.id.errorMessage).setVisibility(View.GONE);
        EditText boardText = (EditText) findViewById(R.id.boardText);
        try{
            int size = Integer.parseInt(boardText.getText().toString());
            switch (size) {
                case(9):
                case(10):
                case(11):
                    Intent intent = new Intent(this, StartingRoll.class);
                    intent.putExtra("tournament", tournament);
                    intent.putExtra("size", size);
                    startActivity(intent);
                    break;
                default:
                    findViewById(R.id.errorMessage).setVisibility(View.VISIBLE);
            }
        } catch (NumberFormatException e) {
            findViewById(R.id.errorMessage).setVisibility(View.VISIBLE);
        }
    }

    /**
     * The cancel button on click listener
     * @param v - a view, The cancel button
     */
    public void cancel(View v) {
        finish();
    }
}
