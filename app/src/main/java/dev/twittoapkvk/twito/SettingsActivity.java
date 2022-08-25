package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class SettingsActivity extends AppCompatActivity {

    Button button,privactbtn,cahngepass,delaccnbtn,privacypolicy,blockedaacnt;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        button = findViewById(R.id.darkmode);
        blockedaacnt = findViewById(R.id.blockedAccounts);
        privactbtn = findViewById(R.id.privacymode);
        toolbar = findViewById(R.id.toolbar);
        cahngepass = findViewById(R.id.forgotpas);
        privacypolicy= findViewById(R.id.privacyPolicy);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs",MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn",false);


        if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            button.setText("Disable Dark Mode");
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            button.setText("Enable Dark Mode");
        }
cahngepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ForgotPasswordActivity2.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

        blockedaacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ShowBlocked.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

       privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, PrivacyPolicyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isDarkModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn",false);
                    editor.apply();

                    button.setText("Enable Dark Mode");
                }else {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn",true);
                    editor.apply();

                    button.setText("Disable Dark Mode");

                }


            }
        });

        privactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, PrivacyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }
}