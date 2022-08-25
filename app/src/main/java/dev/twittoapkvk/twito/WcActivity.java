package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;



public class WcActivity extends AppCompatActivity {
    private Button startbutton;
    private TextView alreadyacc;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wc);

        startbutton=findViewById(R.id.startbutton);
        alreadyacc = findViewById(R.id.allreayhavAcc);
        login = findViewById(R.id.login);


        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WcActivity.this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WcActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

        alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WcActivity.this ,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }


}