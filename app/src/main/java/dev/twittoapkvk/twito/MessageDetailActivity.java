package dev.twittoapkvk.twito;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MessageDetailActivity extends AppCompatActivity {
Button Mutemsg,chatthemes,sharedmnf,restrict,report,block;
ImageView imageView;
    DatabaseReference rootref1,rootref2,typingref,cancelRef;
TextView textView;
    MessageMember messageMember;
    String  receiver_name,receiver_uid,sender_uid,url,usertoken;
    Uri uri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Mutemsg = findViewById(R.id.mutemsgs);
        chatthemes = findViewById(R.id.chatthemes);
        sharedmnf = findViewById(R.id.sharedmnf);
        restrict = findViewById(R.id.restrict);
        imageView= findViewById(R.id.iv_ms_item);
        textView = findViewById(R.id.msname);
        report = findViewById(R.id.report);
        block = findViewById(R.id.block);


        cancelRef = database.getInstance().getReference("cancel");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            url = bundle.getString("u");
            receiver_name = bundle.getString("n");
            receiver_uid = bundle.getString("uid");
        }else {
            //  Toast.makeText(this, "user missing", Toast.LENGTH_SHORT).show();
        }



        Picasso.get().load(url).into(imageView);
        textView.setText(receiver_name);

        
        Mutemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        chatthemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        sharedmnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        restrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageDetailActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}