package com.example.ballke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BallView extends View {
    private Paint ballPaint;
    private int ballX;
    private int ballY;
    private int ballRadius;
    private int ballSpeedX;
    private int ballSpeedY;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 800;
    private static final int BALL_RADIUS = 50;
    private static final int BALL_SPEED = 10;

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the paint object for drawing the ball
        ballPaint = new Paint();
        ballPaint.setColor(Color.RED);
        ballPaint.setStyle(Paint.Style.FILL);

        // Set initial ball properties
        ballRadius = BALL_RADIUS;
        ballX = SCREEN_WIDTH / 2;
        ballY = SCREEN_HEIGHT / 2;

        // Generate random initial direction for the ball
        Random random = new Random();
        ballSpeedX = (random.nextBoolean() ? 1 : -1) * BALL_SPEED;
        ballSpeedY = (random.nextBoolean() ? 1 : -1) * BALL_SPEED;

        // Start the timer to update the ball's position
        startTimer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the ball on the canvas
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);

        // Display instructions or feedback text
        String text = "Follow the ball with your eyes!";
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText(text, (canvas.getWidth() - textPaint.measureText(text)) / 2, canvas.getHeight() - 50, textPaint);
    }

    private void startTimer() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update the ball's position based on speed and direction
                ballX += ballSpeedX;
                ballY += ballSpeedY;

                // Check if the ball reaches the screen boundaries
                if (ballX - ballRadius < 0 || ballX + ballRadius > getWidth()) {
                    ballSpeedX *= -1; // Reverse the horizontal direction
                }
                if (ballY - ballRadius < 0 || ballY + ballRadius > getHeight()) {
                    ballSpeedY *= -1; // Reverse the vertical direction
                }

                // Redraw the view
                invalidate();

                // Schedule the next update
                startTimer();
            }
        }, 16); // Delay of approximately 60 FPS (1000 milliseconds / 60 frames)
    }
}
