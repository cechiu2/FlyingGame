package com.example.claire.flyinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Get intent
        Intent intent = getIntent();
        int score = intent.getIntExtra(GameView.EXTRA_MESSAGE, 0);
        String scoreDisplayed = "Your score: " + GameView.scoreString;
        TextView textView = findViewById(R.id.textView);
        textView.setText("Your score: " + score);
    }
}
