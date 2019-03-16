package com.example.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //change to customize the game
    int highestPossibleCalculationResult = 60; //has to be an even number!
    int timerLength = 15; //in seconds

    //do not change!
    boolean gameFinished;
    int correctAnswers;
    int numberAnswers;

    Button textAnswer;
    TextView remainingTime;
    TextView calculation;
    Button leftTopButton;
    Button rightTopButton;
    Button leftBottomButton;
    Button rightBottomButton;
    Button[] buttonArray;
    Button correctButton;
    Button playAgainButton;
    ImageView clock;
    Button startButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAnswer = findViewById(R.id.buttonCorrectAnswers);
        clock = findViewById(R.id.clockImageView);
        remainingTime = findViewById(R.id.timeTextView);
        calculation = findViewById(R.id.calculateTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        leftTopButton = findViewById(R.id.button1);
        rightTopButton = findViewById(R.id.button2);
        leftBottomButton = findViewById(R.id.button3);
        rightBottomButton = findViewById(R.id.button4);
        startButton = findViewById(R.id.startButton);

        buttonArray = new Button[]{leftTopButton, rightTopButton,
                                   leftBottomButton,
                                   rightBottomButton};
        startButton.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        setVisibilityOfGameLayout(View.INVISIBLE);
    }

    public void startGame(View view){
        startButton.setVisibility(View.GONE);
        setVisibilityOfGameLayout(View.VISIBLE);
        setDefault();
        createNewCalculation();
        startTimer();
    }

    private void setDefault(){
        gameFinished = false;
        correctAnswers = 0;
        numberAnswers = 0;
        playAgainButton.setVisibility(View.INVISIBLE);
        textAnswer.setText(correctAnswers+"/"+numberAnswers);
        calculation.setTextColor(Color.BLACK);
    }

    private void startTimer(){
        new CountDownTimer(timerLength*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime.setText(millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                calculation.setText("DONE!");
                calculation.setTextColor(Color.argb(255,216,27,0));
                playAgainButton.setVisibility(View.VISIBLE);
		        gameFinished = true;
            }
        }.start();
    }

    private void createNewCalculation(){
            Random random = new Random();
            int firstNumber = random.nextInt(highestPossibleCalculationResult/2);
            int secondNumber = random.nextInt(highestPossibleCalculationResult/2);
            int result = firstNumber + secondNumber;
            calculation.setText(firstNumber + " + " + secondNumber);

            int buttonResult = random.nextInt(4);
            ArrayList<Integer> buttonTexts = new ArrayList<Integer>();
            for (int i = 0; i < 4; i++) {
                int randomNumber = random.nextInt(highestPossibleCalculationResult);
                while (buttonTexts.contains(randomNumber) || randomNumber == result) {
                    randomNumber = random.nextInt(highestPossibleCalculationResult);
                }
                if (i != buttonResult) {
                    buttonArray[i].setText("" + randomNumber);
                    buttonTexts.add(randomNumber);
                } else {
                    buttonArray[i].setText("" + result);
                    correctButton = buttonArray[i];
                }
            }
    }

    public void testIfGivenAnswerIsCorrect(View view){
        if(!gameFinished) {
            if (view.equals(correctButton)) {
                updateCorrectAnswers(true);
            } else {
                updateCorrectAnswers(false);
            }
            createNewCalculation();
        }
    }

    private void updateCorrectAnswers(boolean answerIsCorrect){
        numberAnswers++;
        if(answerIsCorrect){
            correctAnswers++;
        }
        textAnswer.setText(correctAnswers+"/"+numberAnswers);
    }

    private void setVisibilityOfGameLayout(int visibility){
        if(visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            clock.setVisibility(visibility);
            textAnswer.setVisibility(visibility);
            remainingTime.setVisibility(visibility);
            leftTopButton.setVisibility(visibility);
            rightTopButton.setVisibility(visibility);
            leftBottomButton.setVisibility(visibility);
            rightBottomButton.setVisibility(visibility);
            calculation.setVisibility(visibility);

        }
    }
}
