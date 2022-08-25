package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IntroActivity4 extends AppCompatActivity {
FloatingActionButton fb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro4);

        fb5= findViewById(R.id.fb5);

        fb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity4.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}