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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class AddAccount extends AppCompatActivity {
    EditText emailEditText,passwordEditText,confirmEditText;
    Button addAccount;
    ProgressBar progressBar;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmEditText = findViewById(R.id.ConfirmPasswordEditText);

        addAccount = findViewById(R.id.CreateBtn);
        progressBar = findViewById(R.id.progressBar);
        tx = findViewById(R.id.loginBtn);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        tx.setOnClickListener(tv -> startActivity(new Intent(AddAccount.this, LoginActivity.class)));
    }

    void createAccount(){
        String email = emailEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        String confirm = confirmEditText.getText().toString();

        boolean checkData = checkValidData(email,pass,confirm);

        if(!checkData) return;
        createAccountOnDB(email,pass);
    }
    void createAccountOnDB(String email, String pass){
        changeProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(AddAccount.this,
                new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeProgress(false);
                        if(task.isSuccessful()  ){
                            ToastNotification.makeTextToShow(AddAccount.this, "Create Account Successfully!!, now get back the login!!");
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();

                        }
                        else{
                            ToastNotification.makeTextToShow(AddAccount.this,task.getException().getLocalizedMessage());
                        }
                    }
                });
    }
    void changeProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            addAccount.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            addAccount.setVisibility(View.VISIBLE);
        }
    }
    boolean checkValidData( String email, String pass,String confirm){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("email invalid");
            return false;
        }
        if(pass.length()<6){
            passwordEditText.setError("length of Password must more than 6 charaters");
            return  false;
        }
        if(!pass.equals(confirm)){
            confirmEditText.setError("Confirm password wrong");
            return  false;
        }
        return true;
    }
}