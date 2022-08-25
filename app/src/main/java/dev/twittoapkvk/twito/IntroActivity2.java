package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IntroActivity2 extends AppCompatActivity {
FloatingActionButton fb3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);
        fb3 = findViewById( R.id.fb3);

        fb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity2.this,IntroActivity3a.class);
                startActivity(intent);
                finish();
            }
        });
    }
}