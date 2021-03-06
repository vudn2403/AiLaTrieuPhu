package com.vudn.ailatrieuphu.activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
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
    private static final int MSG_SHOW_DIALOG_WHEN_ANSWER_FAIL = 102;
    private static final int MSG_END_GAME = 103;
    private static final int MSG_UPDATE_TIME = 104;
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
    private TextView txtScore;
    private TextView txtTime;

    private List<Question> questions;

    private QuestionManager questionManager;

    private int level;
    private int score;
    private int time;
    private int trueCase;
    private boolean isSelected;
    private boolean isPlaying;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        txtTime = findViewById(R.id.txt_time);
        txtScore = findViewById(R.id.txt_score);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_CHECK_QUESTION:
                        checkQuestion(msg.arg1, (View) msg.obj);
                        break;

                    case MSG_NEXT_QUESTION:
                        nextQuestion((View) msg.obj);
                        break;

                    case MSG_END_GAME:
                        backToMenuActivity();
                        break;

                    case MSG_SHOW_DIALOG_WHEN_ANSWER_FAIL:
                        showDialogWhenAnswerFail();
                        isPlaying = false;
                        break;

                    case MSG_UPDATE_TIME:
                        txtTime.setText(String.valueOf(time));
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
        isPlaying = true;
        createQuestion();
        countTime();
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
        if (!isSelected) {
            ((Button) view).setSelected(true);
            isSelected = true;
            Message msg = new Message();
            msg.what = MSG_CHECK_QUESTION;
            msg.arg1 = answer;
            msg.obj = view;
            handler.sendMessageDelayed(msg, 2000);
        }
    }

    private void checkQuestion(int answer, View view) {
        if (answer == trueCase) {
            ((Button) view).setBackgroundColor(getResources().getColor(R.color.colorGreen));
            Message msg = new Message();
            msg.what = MSG_NEXT_QUESTION;
            msg.obj = view;
            handler.sendMessageDelayed(msg, 2000);
        } else {
            ((Button) view).setBackgroundColor(getResources().getColor(R.color.colorRed));
            switch (trueCase) {
                case ANSWER_CASE_A:
                    btnCaseA.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    break;

                case ANSWER_CASE_B:
                    btnCaseB.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    break;

                case ANSWER_CASE_C:
                    btnCaseC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    break;

                case ANSWER_CASE_D:
                    btnCaseD.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    break;

                default:
                    break;
            }
            Message msg = new Message();
            msg.what = MSG_SHOW_DIALOG_WHEN_ANSWER_FAIL;
            handler.sendMessageDelayed(msg, 2000);
        }
    }

    private void showDialogWhenAnswerFail(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Bạn đã trả lời sai ở câu số " + level)
                .setMessage("Bạn sẽ ra về với phần thưởng " + score)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backToMenuActivity();
                    }
                })
                .show();
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void nextQuestion(View view) {
        if (level < 15) {
            level++;
            txtLevel.setText("Câu " + level);

            ((Button) view).setBackgroundResource(R.drawable.bg_button_answer);
            ((Button) view).setSelected(false);
            createQuestion();
            isSelected = false;
        } else {
            Toast.makeText(MainActivity.this, "Chúc mừng bạn đã trở thành triệu phú!", Toast.LENGTH_SHORT).show();
            Message msg = new Message();
            msg.what = MSG_END_GAME;
            handler.sendMessageDelayed(msg, 3000);
        }
    }

    private void createQuestion() {
        if (questions != null || !questions.isEmpty()) {
            Question question = questions.get(level - 1);
            txtQuestion.setText(question.getQuestion());
            btnCaseA.setText("A: " + question.getCaseA());
            btnCaseB.setText("B: " + question.getCaseB());
            btnCaseC.setText("C: " + question.getCaseC());
            btnCaseD.setText("D: " + question.getCaseD());
            trueCase = question.getTrueCase();
            time = 30;
            txtTime.setText(String.valueOf(time));
        }
    }

    private void countTime(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (isPlaying){
                    Message msg = new Message();
                    msg.what = MSG_UPDATE_TIME;
                    handler.sendMessage(msg);
                    if (time > 0){
                        if (!isSelected){
                            time --;
                        }
                    }
                    else {
                        Message msg_2 = new Message();
                        msg.what = MSG_SHOW_DIALOG_WHEN_ANSWER_FAIL;
                        handler.sendMessage(msg_2);
                    }
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
