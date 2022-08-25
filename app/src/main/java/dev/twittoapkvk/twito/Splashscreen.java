package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Splashscreen extends AppCompatActivity {

    ImageView imageView;
    TextView nameTv,name2Tv;
    long animTime = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);


        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs",MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn",false);


        if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

        imageView = findViewById(R.id.iv_logo_splash);
        nameTv = findViewById(R.id.tv_splash_name);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView,"y",800f);
        ObjectAnimator animatorname = ObjectAnimator.ofFloat(nameTv,"x",420f);
        animatorY.setDuration(animTime);
        animatorname.setDuration(animTime);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,animatorname);
        animatorSet.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null){
                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Splashscreen.this,WcActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);

    }
}
