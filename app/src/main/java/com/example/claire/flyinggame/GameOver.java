package com.example.claire.flyinggame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Get intent
        Intent intent = getIntent();
        //int score = intent.getIntExtra(GameView.EXTRA_MESSAGE, 0);
        String scoreDisplayed = "Your score: " + GameView.scoreString;
        TextView textView = findViewById(R.id.textView);
        textView.setText(scoreDisplayed);

        int score;
        try {
            SharedPreferences prefs = this.getSharedPreferences("best", Context.MODE_PRIVATE);
            score = prefs.getInt("highscore", 0);
            if (score < Integer.parseInt(GameView.scoreString)) {
                score = Integer.parseInt(GameView.scoreString);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("highscore", score);
                editor.commit();
            }
        } catch (NullPointerException e) {
            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", Integer.parseInt(GameView.scoreString));
            editor.commit();
            score = Integer.parseInt(GameView.scoreString);
        }

        /*
        //high score
        String highscore;
        try {
            InputStream tempHighscore = openFileInput("Highscore");
            char[] data = new char[tempHighscore.available()];
            for(int i = 0; i < data.length; i++) {
                data[i] = (char) tempHighscore.read();
            }
            highscore = String.valueOf(data);
        } catch (Exception e) {
            String highscoreName = "Highscore";
            highscore = "" + GameView.scoreString;
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(highscore, Context.MODE_PRIVATE);
                outputStream.write(highscoreName.getBytes());
                outputStream.close();
            } catch (Exception f) {
                e.printStackTrace();
            }
        } */

        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Highscore: " + score);
    }
}
