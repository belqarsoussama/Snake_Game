package com.example.snake_game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout board = findViewById(R.id.board);
        RelativeLayout border = findViewById(R.id.relativeLayout);
        LinearLayout lilu = findViewById(R.id.lilu);
        Button upButton = findViewById(R.id.up);
        Button downButton = findViewById(R.id.down);
        Button leftButton = findViewById(R.id.left);
        Button rightButton = findViewById(R.id.right);
        Button pauseButton = findViewById(R.id.pause);
        Button newGame = findViewById(R.id.new_game);
        Button resume = findViewById(R.id.resume);
        Button playAgain = findViewById(R.id.playagain);
        Button score = findViewById(R.id.score);
        Button score2 = findViewById(R.id.score2);

        ImageView meat = new ImageView(this);
        ImageView snake = new ImageView(this);

        List<ImageView> snakeSegments = new ArrayList<>();
        snakeSegments.add(snake);

        Handler handler = new Handler();
        final long[] delayMillis = {30L};
        final String[] currentDirection = {"right"};
        final int[] scorex = {0};

        board.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        score2.setVisibility(View.INVISIBLE);

        newGame.setOnClickListener(v -> {
        board.setVisibility(View.VISIBLE);
        newGame.setVisibility(View.INVISIBLE);
        resume.setVisibility(View.INVISIBLE);
        score2.setVisibility(View.VISIBLE);

        snake.setImageResource(R.drawable.snake);
        snake.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        board.addView(snake);

        float[] snakePos = {snake.getX(), snake.getY()};

        meat.setImageResource(R.drawable.meat);
        meat.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        board.addView(meat);

        Random random = new Random();
        int randomX = random.nextInt(801) - 400;
        int randomY = random.nextInt(801) - 400;
        meat.setX(randomX);
        meat.setY(randomY);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = snakeSegments.size() - 1; i > 0; i--) {
                snakeSegments.get(i).setX(snakeSegments.get(i - 1).getX());
                snakeSegments.get(i).setY(snakeSegments.get(i - 1).getY());
            }

                switch (currentDirection[0]) {
                    case "up":
                    snakePos[1] -= 10;
                    if (snakePos[1] < -490) {
                        snakePos[1] = -490;
                        border.setBackgroundColor(getResources().getColor(R.color.red));
                        playAgain.setVisibility(View.VISIBLE);
                        currentDirection[0] = "pause";
                        lilu.setVisibility(View.INVISIBLE);
                        score.setText("Your score is " + scorex[0]);
                        score.setVisibility(View.VISIBLE);
                        score2.setVisibility(View.INVISIBLE);
                    }
                    snake.setTranslationY(snakePos[1]);
                    break;
                    case "down":
                    snakePos[1] += 10;
                    float maxY = board.getHeight() / 2f - snake.getHeight() + 30;
                    if (snakePos[1] > maxY) {
                        snakePos[1] = maxY;
                        border.setBackgroundColor(getResources().getColor(R.color.red));
                        playAgain.setVisibility(View.VISIBLE);
                        currentDirection[0] = "pause";
                        lilu.setVisibility(View.INVISIBLE);
                        score.setText("Your score is " + scorex[0]);
                        score.setVisibility(View.VISIBLE);
                        score2.setVisibility(View.INVISIBLE);
                    }
                    snake.setTranslationY(snakePos[1]);
                    break;
                    case "left":
                    snakePos[0] -= 10;
                    if (snakePos[0] < -490) {
                        snakePos[0] = -490;
                        border.setBackgroundColor(getResources().getColor(R.color.red));
                        playAgain.setVisibility(View.VISIBLE);
                        currentDirection[0] = "pause";
                        lilu.setVisibility(View.INVISIBLE);
                        score.setText("Your score is " + scorex[0]);
                        score.setVisibility(View.VISIBLE);
                        score2.setVisibility(View.INVISIBLE);
                    }
                    snake.setTranslationX(snakePos[0]);
                    break;
                    case "right":
                    snakePos[0] += 10;
                    float maxX = board.getHeight() / 2f - snake.getHeight() + 30;
                    if (snakePos[0] > maxX) {
                        snakePos[0] = maxX;
                        border.setBackgroundColor(getResources().getColor(R.color.red));
                        playAgain.setVisibility(View.VISIBLE);
                        currentDirection[0] = "pause";
                        lilu.setVisibility(View.INVISIBLE);
                        score.setText("Your score is " + scorex[0]);
                        score.setVisibility(View.VISIBLE);
                        score2.setVisibility(View.INVISIBLE);
                    }
                    snake.setTranslationX(snakePos[0]);
                    break;
                    case "pause":
                    break;
                }

                checkFoodCollision(snake, meat, snakeSegments, random);
                handler.postDelayed(this, delayMillis[0]);
            }

            private void checkFoodCollision(ImageView snake, ImageView meat, List<ImageView> snakeSegments, Random random) {
                float distance = (float) Math.sqrt(Math.pow(snake.getX() - meat.getX(), 2) + Math.pow(snake.getY() - meat.getY(), 2));
                if (distance < 50) {
                    ImageView newSnakeSegment = new ImageView(MainActivity.this);
                    newSnakeSegment.setImageResource(R.drawable.snake);
                    newSnakeSegment.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    board.addView(newSnakeSegment);
                    snakeSegments.add(newSnakeSegment);

                    int newRandomX = random.nextInt(801) - 400;
                    int newRandomY = random.nextInt(801) - 400;
                    meat.setX(newRandomX);
                    meat.setY(newRandomY);

                    delayMillis[0]--;
                    scorex[0]++;
                    score2.setText("Score: " + scorex[0]);
                }
            }
        };

        handler.postDelayed(runnable, delayMillis[0]);

        upButton.setOnClickListener(v1 -> currentDirection[0] = "up");
        downButton.setOnClickListener(v1 -> currentDirection[0] = "down");
        leftButton.setOnClickListener(v1 -> currentDirection[0] = "left");
        rightButton.setOnClickListener(v1 -> currentDirection[0] = "right");
        pauseButton.setOnClickListener(v1 -> {
        currentDirection[0] = "pause";
        board.setVisibility(View.INVISIBLE);
        newGame.setVisibility(View.VISIBLE);
        resume.setVisibility(View.VISIBLE);
    });
        resume.setOnClickListener(v1 -> {
        currentDirection[0] = "right";
        board.setVisibility(View.VISIBLE);
        newGame.setVisibility(View.INVISIBLE);
        resume.setVisibility(View.INVISIBLE);
    });
        playAgain.setOnClickListener(v1 -> recreate());
    });
    }
}
