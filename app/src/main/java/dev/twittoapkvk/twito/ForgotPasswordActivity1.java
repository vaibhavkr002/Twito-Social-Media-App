package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity1 extends AppCompatActivity {


    private Button forPassword;
    private EditText foremail;
    private String email;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);

        auth = FirebaseAuth.getInstance() ;
        forPassword = findViewById(R.id.forgotPassword);
        foremail = findViewById(R.id.forgotemail);

        forPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();

            }
        });
    }

    private void validateData() {
        email = foremail.getText().toString();
        if(email.isEmpty()){
            foremail.setError("Required");

        }else{
            forgotPassword();
        }
    }

    private void forgotPassword() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity1.this, "Check your Email", Toast.LENGTH_SHORT).show();

                            finish();
                        }else{
                            Toast.makeText(ForgotPasswordActivity1.this, "Error !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}