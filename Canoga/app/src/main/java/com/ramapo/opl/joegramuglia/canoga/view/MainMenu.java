package com.ramapo.opl.joegramuglia.canoga.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ramapo.opl.joegramuglia.canoga.R;
import com.ramapo.opl.joegramuglia.canoga.model.Tournament;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainMenu extends AppCompatActivity {

    /**
     * Creates the main menu UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //http://stackoverflow.com/questions/23703778/exit-android-application-programmatically
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    /**
     * The new game button on click listener
     * @param v - a view, The new game button
     */
    public void NewGame(View v) {
        System.out.println("Creating new game...");
        Intent intent = new Intent(this, BoardSize.class);
        Tournament tournament = new Tournament();
        tournament.newGame(9, false);
        intent.putExtra("tournament", tournament);
        startActivity(intent);
    }

    /**
     * The load game button on click listener
     * @param v - a view, The load game button
     */
    public void LoadGame(View v) {
        System.out.println("Loading game...");
        Intent intent = new Intent(this, LoadGame.class);
        startActivity(intent);
    }

    /**
     * The exit game button on click listener
     * @param v - a view, The exit game button
     */
    public void ExitGame(View v) {
        System.out.println("Exiting game... ");
        finish();
    }
}
