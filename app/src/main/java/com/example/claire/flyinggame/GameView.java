package com.example.claire.flyinggame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * hm...
 */
public class GameView extends View {

    //access score in game over screen
    public static String scoreString;
    public static String EXTRA_MESSAGE = "com.example.claire.flyinggame.SCORE";

    //canvas
    private int canvasWidth;
    private int canvasHeight;

    //bird
    //private Bitmap bird;
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    //blue ball
    private int blueX;
    private int blueY;
    private int blueSpeed = 15;
    //private Paint bluePaint = new Paint();
    private Bitmap cs125[] = new Bitmap[1];

    //black ball
    private int blackX;
    private int blackY;
    private int blackSpeed = 20;
    //private Paint blackPaint = new Paint();
    private Bitmap checkstyle[] = new Bitmap[1];

    //background
    private Bitmap bgImage;

    //score
    private Paint scorePaint = new Paint();
    private int score;

    //level
    private Paint levelPaint = new Paint();

    //life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;

    //status check
    private boolean touch_flg = false;


    public GameView(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird3);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird4);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        cs125[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cs125);
        //bluePaint.setColor(Color.BLUE);
        //bluePaint.setAntiAlias(false);

        checkstyle[0] = BitmapFactory.decodeResource(getResources(), R.drawable.checkstyle);
        //blackPaint.setColor(Color.BLACK);
        //blackPaint.setAntiAlias(false);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        //first position
        birdY = 500;
        score = 0;
        life_count = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bgImage, 0, 0, null);

        // Bird
        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight() * 3;

        birdY += birdSpeed;
        if (birdY < minBirdY) birdY = minBirdY;
        if (birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed += 2;

        if (touch_flg) {
            //flap wings
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            touch_flg = false;
        } else {
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }

        //blue
        blueX -= blueSpeed;
        if (hitCheck(blueX, blueY)) {
            score += 10;
            blueX = -100;
        }
        if (blueX < 0) {
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        //canvas.drawCircle(blueX, blueY, 10, bluePaint);
        canvas.drawBitmap(cs125[0], blueX, blueY, null);

        //black
        blackX -= blackSpeed;
        if (hitCheck(blackX, blackY)) {
            blackX = -100;
            life_count--;
            if (life_count == 0) {
                //game over
                //Log.v("MESSAGE", "GAME OVER");
                Intent intent = new Intent;
                intent.putExtra(EXTRA_MESSAGE, score);
                scoreString = "" + score;
                startActivity(intent);
            }
        }
        if (blackX < 0) {
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        //canvas.drawCircle(blackX, blackY, 20, blackPaint);
        canvas.drawBitmap(checkstyle[0], blackX, blackY, null);

        //score
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        //level
        canvas.drawText("Health:", canvasWidth / 2, 60, levelPaint);

        //life
        for (int i = 0; i < 3; i++) {
            int x = (int)(600 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < life_count) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitCheck(int x, int y) {
        if (birdX < x && x < (birdX + bird[0].getWidth()) &&
                birdY < y && y < (birdY + bird[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_flg = true;
            birdSpeed = -20;
        }
        return true;
    }
}
