package com.icdominguez.duckhunt.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.icdominguez.duckhunt.R;
import com.icdominguez.duckhunt.common.Constants;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView tvCounter, tvTimer, tvUsername;
    ImageView ivDuck;
    int counter = 0;
    int screenWidth;
    int screenHeight;
    Random random;
    boolean gameOver = false;
    String userId, username;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = FirebaseFirestore.getInstance();

        findViews();
        events();
        initScreen();
        initCountDown();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "pixel.ttf");
        tvCounter.setTypeface(typeface);
        tvTimer.setTypeface(typeface);
        tvUsername.setTypeface(typeface);

        Bundle extras = getIntent().getExtras();
        username = extras.getString(Constants.EXTRA_USERNAME);
        userId = extras.getString(Constants.EXTRA_ID);
        tvUsername.setText(username);

        moveDuck();
    }

    private void initCountDown() {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                tvTimer.setText("0s");
                gameOver = true;
                showDialogGameOver();
                saveResultFireStore();
            }
        }.start();
    }

    private void showDialogGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.game_hunted_ducks) + counter + getString(R.string.game_duck)).setTitle(getString(R.string.game_gameOver));

        builder.setPositiveButton(getString(R.string.game_restart), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                counter = 0;
                tvCounter.setText("0");
                gameOver = false;
                initCountDown();
                moveDuck();
            }
        });
        builder.setNegativeButton("Ver ranking", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent i = new Intent(GameActivity.this, RankingActivity.class);
                startActivity(i);
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void initScreen() {
        // 1. Get the size of the screen where we are running the application
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        // 2. Initialize the object to generate random numbers
        random = new Random();
    }

    private void findViews() {
        tvCounter = findViewById(R.id.textViewCounter);
        tvTimer = findViewById(R.id.textViewTimer);
        tvUsername = findViewById(R.id.textViewUsername);
        ivDuck = findViewById(R.id.imageViewDuck);
    }

    private void events() {
        ivDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gameOver) {
                    counter++;
                    tvCounter.setText(String.valueOf(counter));

                    ivDuck.setImageResource(R.drawable.duck_clicked);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ivDuck.setImageResource(R.drawable.duck);
                            moveDuck();
                        }
                    }, 500);
                }
            }
        });
    }

    private void moveDuck() {
        int min = 0;
        int maxWidth = screenWidth - ivDuck.getWidth();
        int maxHeigth = screenHeight - ivDuck.getHeight();

        // 2 random numbers are generated one for the X coordinate and the other for the Y coordinate
        int randomX = random.nextInt(((maxWidth - min) + 1) + min);
        int randomY = random.nextInt(((maxHeigth - min) + 1) + min);

        // We use random numbers to move the duck to that position
        ivDuck.setX(randomX);
        ivDuck.setY(randomY);
    }

    private void saveResultFireStore() {
        db.collection("users").document(userId).update("ducks", counter);
    }

}