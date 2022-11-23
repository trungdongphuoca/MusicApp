package tdtu.finalapp.musicapp.loginAndRegis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tdtu.finalapp.musicapp.MainActivity;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;


public class LoginActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button LoginBtn;
    ProgressBar progressBar;
    TextView createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        LoginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        createAccount = findViewById(R.id.signupBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        createAccount.setOnClickListener(tv -> startActivity(new Intent(LoginActivity.this, AddAccount.class)));
    }
    void login(){
        String email = emailEditText.getText().toString();
        String pass = passwordEditText.getText().toString();

        boolean checkData = checkValidData(email,pass);

        if(!checkData) return;
        loginOnDB(email,pass);
    }

    void loginOnDB(String email, String pass){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeProgress(false);
                if(task.isSuccessful()){ //email and password is correct
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){ // account has already verified
                        //go to main
                        ToastNotification.makeTextToShow(LoginActivity.this, "Successful");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else{ //not verified
                        ToastNotification.makeTextToShow(LoginActivity.this, "Email  is not verified");
                    }
                }
                else{ // login fail
                    ToastNotification.makeTextToShow(LoginActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            LoginBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            LoginBtn.setVisibility(View.VISIBLE);
        }
    }
    boolean checkValidData( String email, String pass){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("email invalid");
            return false;
        }
        if(pass.length()<6){
            passwordEditText.setError("length of Password must more than 6 charaters");
            return  false;
        }
        return true;
    }
}