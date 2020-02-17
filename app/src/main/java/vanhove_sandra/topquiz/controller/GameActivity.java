package vanhove_sandra.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import vanhove_sandra.topquiz.R;
import vanhove_sandra.topquiz.model.Question;
import vanhove_sandra.topquiz.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView agQuestion;

    private Button aqButtonOne,aqButtonTwo,aqButtonThree,aqButtonFour;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mScore;
    private int mNumberOfQuestion;
    public static final String BUNDLE_EXTRA_SCORE = "BULDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "CurrentScore";
    public static final String BINDLE_STATE_QUESTION =  "CurrentQuestion";

    private boolean mEnabledTouchEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // lier la question
        agQuestion = findViewById(R.id.activity_game_question_text);

        mQuestionBank = this.generateQuestions();
        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestion =  savedInstanceState.getInt(BUNDLE_STATE_SCORE);
        }else{
            mScore = 0;
            mNumberOfQuestion = 4;
        }


        // lier les boutons
        aqButtonOne = findViewById(R.id.activity_game_answer1_btn);
        aqButtonTwo = findViewById(R.id.activity_game_answer2_btn);
        aqButtonThree = findViewById(R.id.activity_game_answer3_btn);
        aqButtonFour = findViewById(R.id.activity_game_answer4_btn);



        //Use the tag property to 'name' the buttons
        aqButtonOne.setTag(0);
        aqButtonTwo.setTag(1);
        aqButtonThree.setTag(2);
        aqButtonFour.setTag(3);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

        aqButtonOne.setOnClickListener(this);
        aqButtonTwo.setOnClickListener(this);
        aqButtonThree.setOnClickListener(this);
        aqButtonFour.setOnClickListener(this);

        mEnabledTouchEvent = true;
    }
    private void displayQuestion(final Question question){
        agQuestion.setText(question.getQuestion());
        aqButtonOne.setText(question.getChoiceList().get(0));
        aqButtonTwo.setText(question.getChoiceList().get(1));
        aqButtonThree.setText(question.getChoiceList().get(2));
        aqButtonFour.setText(question.getChoiceList().get(3));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BINDLE_STATE_QUESTION, mNumberOfQuestion);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int resonseIndex = (int) v.getTag();

        if (resonseIndex == mCurrentQuestion.getAnswerIndex()){
            //good answer
            Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
            mScore++;
        }else{
            //wrong answer
            Toast.makeText(this, "Wrong answer !!", Toast.LENGTH_SHORT).show();
        }
        mEnabledTouchEvent =  false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mEnabledTouchEvent =  true;
                if(--mNumberOfQuestion == 0){
                    endGame();
                }else{
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnabledTouchEvent && super.dispatchTouchEvent(ev);
    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("well done!")
                .setMessage("Your score is" + mScore)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
    }


}
