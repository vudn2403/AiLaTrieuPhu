package com.vudn.ailatrieuphu.activity;

import android.os.Bundle;
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
    }

    private void createGame() {
        level = 1;
        txtLevel.setText("Câu 1");
        questions = questionManager.createListQuestion();
        createQuestion();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_case_a:
                checkQuestion(ANSWER_CASE_A);
                break;

            case R.id.btn_case_b:
                checkQuestion(ANSWER_CASE_B);
                break;

            case R.id.btn_case_c:
                checkQuestion(ANSWER_CASE_C);
                break;

            case R.id.btn_case_d:
                checkQuestion(ANSWER_CASE_D);
                break;
        }
    }

    private void checkQuestion(int answer) {
        if (answer == trueCase){
            Toast.makeText(this, "Bạn đã trả lời đúng!", Toast.LENGTH_SHORT).show();
            nextQuestion();
        }else {
            Toast.makeText(this, "Bạn đã trả lời sai!", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextQuestion() {
        if (level < 15){
            level ++;
            txtLevel.setText("Câu " + level);
            createQuestion();
        }else {
            Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
        }
    }

    private void createQuestion() {
        if (questions != null || !questions.isEmpty()){
            Question question = questions.get(level-1);
            txtQuestion.setText(question.getQuestion());
            btnCaseA.setText(question.getCaseA());
            btnCaseB.setText(question.getCaseB());
            btnCaseC.setText(question.getCaseC());
            btnCaseD.setText(question.getCaseD());
            trueCase = question.getTrueCase();
        }
    }
}
