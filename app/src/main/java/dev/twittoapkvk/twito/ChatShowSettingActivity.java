package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;


public class ChatShowSettingActivity extends AppCompatActivity {
Button gotoprofile,accountsettings, leagal;
TextView textView;

    Uri imageUri;
    Toolbar toolbar;
    String url,userid;
    private static int PICK_IMAGE=1;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_show_setting);

//        activestatus= findViewById(R.id.activestatus);
//
//        peoples= findViewById(R.id.peoples);
//        mangestorage= findViewById(R.id.managestorage);
        accountsettings= findViewById(R.id.accountsettings);
        leagal= findViewById(R.id.policiesndlegal);
//        problem= findViewById(R.id.reportproblem);
        textView = findViewById(R.id.back);
        gotoprofile = findViewById(R.id.gottoprofile);
        androidx.appcompat.widget.Toolbar toolbar1;

//  activestatus.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Toast.makeText(ChatShowSettingActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
//      }
//  });
//
//  peoples.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Toast.makeText(ChatShowSettingActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
//      }
//  });
//
//  mangestorage.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Toast.makeText(ChatShowSettingActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
//      }
//  });
//
//  problem.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Toast.makeText(ChatShowSettingActivity.this, "Tis feature is coming soon!c", Toast.LENGTH_SHORT).show();
//      }
//  });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatShowSettingActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
  gotoprofile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Fragment fragment = new ProfileFragment4();
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.container1,fragment).commit();
      }
  });

        accountsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChatShowSettingActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        leagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChatShowSettingActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

    }
}