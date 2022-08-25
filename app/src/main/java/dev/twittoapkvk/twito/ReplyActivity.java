package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import java.util.Calendar;

public class ReplyActivity extends AppCompatActivity {



    String uid,question,post_key,key,name_result;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference ,reference2;
    ImageButton imageButton;
    AnswerMember member;
    TextView nametv,questiontv,tvreply;
    RecyclerView recyclerView;
    ImageView imageViewQue,imageViewUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference votesref,Allquetions,Allquestions,ntref;
    EditText editText;
    Boolean votechecker = false;
    String name,url,time,usertoken;
    NewMember newMember;
    String que,postkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        nametv = findViewById(R.id.name_reply_tv);
        questiontv = findViewById(R.id.que_reply_tv);
        imageViewQue = findViewById(R.id.iv_que_user);
        imageViewUser = findViewById(R.id.iv_reply_user);
        editText = findViewById(R.id.answer_tv);
        imageButton= findViewById(R.id.btn_submit);
        recyclerView = findViewById(R.id.rv_ans);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReplyActivity.this));

        newMember = new NewMember();
        member = new AnswerMember();
//
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            uid = bundle.getString("u");
            postkey = bundle.getString("p");
        }else {
            Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
        }

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            uid = extra.getString("uid");
            post_key = extra.getString("postkey");
            question = extra.getString("q");
           // key = extra.getString("key");
        }else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        Allquetions = database.getReference("AllQuestions").child(post_key).child("Answer");
        votesref = database.getReference("votes");

//        Allquestions = database.getReference("AllQuestions").child(postkey).child("Answer");
        ntref = database.getReference("notification").child(uid);


        reference = db.collection("user").document(uid);
        reference2 = db.collection("user").document(currentuid);

imageButton.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {

            saveAnswer();
        }

});




    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveAnswer() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        String answer = editText.getText().toString();
        if (answer != null){

            Calendar cdate = Calendar.getInstance();
            SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
            final  String savedate = currentdate.format(cdate.getTime());

            Calendar ctime = Calendar.getInstance();
            SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
            final String savetime = currenttime.format(ctime.getTime());

            time = savedate +":"+ savetime;

            member.setAnswer(answer);
            member.setTime(time);
            member.setName(name);
            member.setUid(userid);
            member.setUrl(url);

            String id = Allquetions.push().getKey();
            Allquetions.child(id).setValue(member);


            newMember.setName(name);
            newMember.setText("Replied To your Question: " + answer);
            newMember.setSeen("no");
            newMember.setUid(userid);
            newMember.setUrl(url);


            String key = ntref.push().getKey();
            ntref.child(key).setValue(newMember);

            sendNotification(uid,name,answer);

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Please  write answer", Toast.LENGTH_SHORT).show();
        }



    }

    private void sendNotification(String uid, String name, String answer) {

        FirebaseDatabase.getInstance().getReference().child(uid).child("token")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        usertoken = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FcmNotificationsSender notificationsSender =
                        new FcmNotificationsSender(usertoken,"Twito",name_result+" Replied to your Question: " + answer,
                                getApplicationContext(),ReplyActivity.this);

                notificationsSender.SendNotifications();

            }
        },3000);

    }


    private void notification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(nc);

            NotificationCompat.Builder  builder = new NotificationCompat.Builder(this,"n")
                    .setContentText("Twito")
                    .setSmallIcon(R.drawable.icon)
                    .setAutoCancel(true)
                    .setContentText(name_result+" Replied to your Question");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999,builder.build());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
       // question user refernce
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String url = task.getResult().getString("url");
                             name_result = task.getResult().getString("name");
                            Picasso.get().load(url).into(imageViewQue);
                            questiontv.setText(question);
                            nametv.setText(name_result);
                        }else {
                            Toast.makeText(ReplyActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
      // refernce for replying user
        reference2.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String url = task.getResult().getString("url");
                            Picasso.get().load(url).into(imageViewUser);

                        }else {
                            Toast.makeText(ReplyActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        FirebaseFirestore d = FirebaseFirestore.getInstance();
        DocumentReference reference;
        reference = d.collection("user").document(userid);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            url = task.getResult().getString("url");
                            name = task.getResult().getString("name");

                        }else {
                            Toast.makeText(ReplyActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



        FirebaseRecyclerOptions<AnswerMember> options =
                new FirebaseRecyclerOptions.Builder<AnswerMember>()
                        .setQuery(Allquetions,AnswerMember.class)
                        .build();

        FirebaseRecyclerAdapter<AnswerMember,AnsViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AnswerMember, AnsViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AnsViewholder holder, int position, @NonNull final AnswerMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();

                        holder.setAnswer(getApplication(),model.getName(),model.getAnswer()
                                ,model.getUid(),model.getTime(),model.getUrl());

                        holder.upvoteChecker(postkey);


                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentUserid.equals(userid)) {
                                    Intent intent = new Intent(ReplyActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }else {
                                    Intent intent = new Intent(ReplyActivity.this,ShowUser.class);
                                    intent.putExtra("n",name);
                                    intent.putExtra("u",url);
                                    intent.putExtra("uid",userid);
                                    startActivity(intent);
                                }


                            }
                        });


                        holder.nameTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentUserid.equals(userid)) {
                                    Intent intent = new Intent(ReplyActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }else {
                                    Intent intent = new Intent(ReplyActivity.this,ShowUser.class);
                                    intent.putExtra("n",name);
                                    intent.putExtra("u",url);
                                    intent.putExtra("uid",userid);
                                    startActivity(intent);
                                }


                            }
                        });
                        holder.upvoteTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                votechecker = true;
                                votesref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (votechecker.equals(true)){
                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                votesref.child(postkey).child(currentUserid).removeValue();

                                                votechecker = false;
                                            }else {
                                                votesref.child(postkey).child(currentUserid).setValue(true);

                                                votechecker = false;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AnsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.ans_layout,parent,false);

                        return new AnsViewholder(view);



                    }
                };
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }
}