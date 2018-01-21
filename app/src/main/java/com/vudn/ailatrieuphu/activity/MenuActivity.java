package com.vudn.ailatrieuphu.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vudn.ailatrieuphu.R;

/**
 * Created by admin on 18/01/2018.
 */

public class MenuActivity extends AppCompatActivity {
    public static final String KEY_USER_NAME = "key_user_name";
    private String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
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
        if (userName == null){
            Dialog dialog = new Dialog(MenuActivity.this);
            dialog.setContentView(R.layout.dialog_user_name);
            dialog.setCancelable(true);
            final EditText edtUserName = dialog.findViewById(R.id.edt_user_name);
            Button btnOK = dialog.findViewById(R.id.btn_ok);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = edtUserName.getText().toString();
                    if (userName.isEmpty()){
                        Toast.makeText(MenuActivity.this, "Tên người chơi không được để trống", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        intent.putExtra(KEY_USER_NAME, userName);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MenuActivity.this).toBundle());
                    }
                }
            });
            dialog.show();
        }else {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra(KEY_USER_NAME, userName);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }

    }
}
