package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class IntroActivity1 extends AppCompatActivity {
TextView textView;
FloatingActionButton fb2;
    String name_result;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference ;
   All_UserMmber mmber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);

        textView = findViewById(R.id.nameint);
        fb2 = findViewById(R.id.fb2);

        mmber = new All_UserMmber();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        reference = db.collection("user").document(currentuid);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            String url = task.getResult().getString("url");
                            name_result = task.getResult().getString("name");
                         textView.setText(name_result);
                        }

                    }
                });

        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity1.this,IntroActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}