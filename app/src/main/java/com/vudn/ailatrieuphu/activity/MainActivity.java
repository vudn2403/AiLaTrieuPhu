package com.vudn.ailatrieuphu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vudn.ailatrieuphu.R;
import com.vudn.ailatrieuphu.model.Question;
import com.vudn.ailatrieuphu.model.QuestionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/12/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MSG_CHECK_QUESTION = 100;
    private static final int MSG_NEXT_QUESTION = 101;
    private static final int MSG_END_GAME = 102;
    private static final int ANSWER_CASE_A = 1;
    private static final int ANSWER_CASE_B = 2;
    private static final int ANSWER_CASE_C = 3;
    private static final int ANSWER_CASE_D = 4;

    private TextView txtLevel;
    private TextView txtQuestion;
    private Button btnCaseA;
    private Button btnCaseB;
    private Button btnCaseC;
    private Button btnCaseD;

    private List<Question> questions;

    private QuestionManager questionManager;

    private int level;
    private int trueCase;
    private boolean isSelected;

    private Handler handler;
    private Message msg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        createGame();
    }

    private void initializeComponent() {
        questionManager = new QuestionManager(getApplicationContext());
        questions = new ArrayList<>();
        txtLevel = findViewById(R.id.txt_level);
        txtQuestion = findViewById(R.id.txt_question);
        btnCaseA = findViewById(R.id.btn_case_a);
        btnCaseA.setOnClickListener(this);
        btnCaseB = findViewById(R.id.btn_case_b);
        btnCaseB.setOnClickListener(this);
        btnCaseC = findViewById(R.id.btn_case_c);
        btnCaseC.setOnClickListener(this);
        btnCaseD = findViewById(R.id.btn_case_d);
        btnCaseD.setOnClickListener(this);
        msg = new Message();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_CHECK_QUESTION:
                        checkQuestion(msg.arg1, (View)msg.obj);
                        break;

                    case MSG_NEXT_QUESTION:
                        nextQuestion();
                        break;

                    case MSG_END_GAME:
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    private void createGame() {
        level = 1;
        txtLevel.setText("Câu " + level);
        questions = questionManager.createListQuestion();
        createQuestion();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_case_a:
                checkAnswer(ANSWER_CASE_A, view);
                break;

            case R.id.btn_case_b:
                checkAnswer(ANSWER_CASE_B, view);
                break;

            case R.id.btn_case_c:
                checkAnswer(ANSWER_CASE_C, view);
                break;

            case R.id.btn_case_d:
                checkAnswer(ANSWER_CASE_D, view);
                break;

            default:
                break;
        }
    }

    private void checkAnswer(int answer, View view) {
        if (!isSelected){
            ((Button) view).setSelected(true);
            isSelected = true;
            msg.what = MSG_CHECK_QUESTION;
            msg.arg1 = answer;
            msg.obj = view;
            handler.sendMessageDelayed(msg, 2000);
        }
    }

    private void checkQuestion(int answer, View view){
        if (answer == trueCase){
            ((Button) view).setBackgroundColor(Color.parseColor("colorBlue"));
        }else {
            ((Button) view).setBackgroundColor(Color.parseColor("colorRed"));
        }
        msg.what = MSG_NEXT_QUESTION;
        handler.sendMessageDelayed(msg, 2000);
    }

    private void nextQuestion() {
        if (level < 15) {
            level++;
            txtLevel.setText("Câu " + level);
            createQuestion();
            isSelected = false;
        } else {
            Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
            msg.what = MSG_END_GAME;
            handler.sendMessageDelayed(msg, 2000);
        }
    }

    private void createQuestion() {
        if (questions != null || !questions.isEmpty()) {
            Question question = questions.get(level - 1);
            txtQuestion.setText(question.getQuestion());
            btnCaseA.setText("A: " + question.getCaseA());
            btnCaseB.setText("B: " +question.getCaseB());
            btnCaseC.setText("C: " +question.getCaseC());
            btnCaseD.setText("D: " +question.getCaseD());
            trueCase = question.getTrueCase();
        }
    }
}
