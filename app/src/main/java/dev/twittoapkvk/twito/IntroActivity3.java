package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IntroActivity3 extends AppCompatActivity {
FloatingActionButton fb4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);
        fb4=findViewById(R.id.fb4);

        fb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity3.this,IntroActivity4.class);
                startActivity(intent);
                finish();
            }
        });
    }
}