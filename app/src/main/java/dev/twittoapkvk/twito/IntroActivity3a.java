package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IntroActivity3a extends AppCompatActivity {
FloatingActionButton fb4a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_activity3a);
        fb4a = findViewById(R.id.fb4a);

        fb4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity3a.this,IntroActivity3.class);
                startActivity(intent);
                finish();
            }
        });
    }
}