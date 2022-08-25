package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class PrivacyPolicyActivity extends AppCompatActivity {
TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
back = findViewById(R.id.lback);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(PrivacyPolicyActivity.this,SettingsActivity.class);
        startActivity(intent);
    }
});
    }


}