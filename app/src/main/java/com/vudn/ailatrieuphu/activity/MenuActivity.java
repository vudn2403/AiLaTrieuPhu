package com.vudn.ailatrieuphu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vudn.ailatrieuphu.R;

/**
 * Created by admin on 18/01/2018.
 */

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeComponents();
    }

    private void initializeComponents() {
        findViewById(R.id.btn_play_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_play_game:
                        startGame();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }
}