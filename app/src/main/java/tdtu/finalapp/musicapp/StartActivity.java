package tdtu.finalapp.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.finalapp.musicapp.MainPackage.MainActivity;
import tdtu.finalapp.musicapp.loginAndRegis.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser UserIsSigned = FirebaseAuth.getInstance().getCurrentUser();

                if(UserIsSigned == null){
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
                else{
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }

                finish();
            }
        },1500);
    }
}