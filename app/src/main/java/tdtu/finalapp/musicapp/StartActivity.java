package tdtu.finalapp.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tdtu.finalapp.musicapp.loginAndRegis.AddAccount;
import tdtu.finalapp.musicapp.loginAndRegis.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, AddAccount.class));
//                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        },1500);
    }
}