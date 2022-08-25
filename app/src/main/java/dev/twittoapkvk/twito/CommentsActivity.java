package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommentsActivity extends AppCompatActivity {

    ImageView usernameImageview;
    TextView usernameTextview;
    ImageButton commentsBtn;
    EditText commentsEdittext;
    String url,name,post_key,userid,bundleuid;
    DatabaseReference Commentref,userCommentref,likesref,ntref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String name_result,age_result,Url,uid,bio_result,web_result,email_result,usertoken;
    RecyclerView recyclerView;
    Boolean likeChecker = false;
TextView back;
    NewMember newMember;
    CommentsMember commentsMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentsMember = new CommentsMember();
        back = findViewById(R.id.back);
        newMember = new NewMember();
        recyclerView = findViewById(R.id.recycler_view_comments);

      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(CommentsActivity.this,MainActivity.class);
              startActivity(intent);
          }
      });
        recyclerView.setHasFixedSize(true);
        //   MediaController mediaController;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentsBtn = findViewById(R.id.btn_comments);
        usernameImageview = findViewById(R.id.imageviewUser_comment);
        commentsEdittext = findViewById(R.id.et_comments);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            url = extras.getString("url");
            name = extras.getString("name");
            post_key = extras.getString("postkey");
            bundleuid = extras.getString("uid");
        }else {

        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        Commentref = database.getReference("All Posts").child(post_key).child("Comments");

        likesref = database.getReference("comment likes");
        userCommentref = database.getReference("User Posts").child(userid);

        ntref = database.getReference("notification").child(bundleuid);

        commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("user").document(userid);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String url = task.getResult().getString("url");
                        Picasso.get().load(url).into(usernameImageview);

                        if (task.getResult().exists()){
                            name_result = task.getResult().getString("name");
                            age_result = task.getResult().getString("age");
                            bio_result = task.getResult().getString("bio");
                            email_result = task.getResult().getString("email");
                            web_result = task.getResult().getString("website");
                            Url = task.getResult().getString("url");
                            uid = task.getResult().getString("uid");
                        }
                    }
                });


        FirebaseRecyclerOptions<CommentsMember> options =
                new FirebaseRecyclerOptions.Builder<CommentsMember>()
                        .setQuery(Commentref,CommentsMember.class)
                        .build();

        FirebaseRecyclerAdapter<CommentsMember,CommentsViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<CommentsMember, CommentsViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CommentsViewholder holder, int position, @NonNull CommentsMember model) {


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserId = user.getUid();
                        final String postkey = getRef(position).getKey();
                        String time = getItem(position).getTime();

                        holder.setComment(getApplication(),model.getComment(),model.getTime(),model.getUrl(),model.getUsername(),model.getUid());

                        holder.LikeChecker(postkey);
                        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                showDialog(name,url,time,userid,postkey);
                                return false;
                            }
                        });

                        holder.likebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                likeChecker = true;

                                likesref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (likeChecker.equals(true)){
                                            if (snapshot.child(postkey).hasChild(currentUserId)){
                                                likesref.child(postkey).child(currentUserId).removeValue();
                                                likeChecker = false;

                                            }else {
                                                likesref.child(postkey).child(currentUserId).setValue(true);
                                                likeChecker = false;
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
                    public CommentsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.comments_item,parent,false);

                        return new CommentsViewholder(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    private void showDialog(String name, String url, String time, String userid, String postkey) {

        final Dialog dialog = new Dialog(CommentsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.cmnt_del_bottomsheet);

        TextView deletes = dialog.findViewById(R.id.deletes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();

        if (userid.equals(currentUserid)){
            deletes.setVisibility(View.VISIBLE);

        }else {
            deletes.setVisibility(View.INVISIBLE);

        }
       deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = Commentref.orderByChild("time").equalTo(time);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();

                            Toast.makeText(CommentsActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void comment() {

        Calendar callfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new
                SimpleDateFormat("dd-MMMM-yyyy");
        final  String savedate = currentdate.format(callfordate.getTime());


        Calendar callfortime = Calendar.getInstance();
        SimpleDateFormat currenttime = new
                SimpleDateFormat("HH:mm:ss");
        final  String savetime = currenttime.format(callfortime.getTime());

        String time = savedate+":"+savetime;
        String comment = commentsEdittext.getText().toString();
        if (comment != null){

            commentsMember.setComment(comment);
            commentsMember.setUsername(name_result);
            commentsMember.setUid(uid);
            commentsMember.setTime(time);
            commentsMember.setUrl(Url);

            String pushkey = Commentref.push().getKey();
            Commentref.child(pushkey).setValue(commentsMember);

            commentsEdittext.setText("");


            newMember.setName(name);
            newMember.setUid(userid);
            newMember.setUrl(Url);
            newMember.setSeen("no");
            newMember.setText("Commented on your post: " + comment);

            String key = ntref.push().getKey();
            ntref.child(key).setValue(newMember);
            sendNotification(bundleuid,name_result,comment);

            Toast.makeText(this, "Commented", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Please write comment", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendNotification(String bundleuid, String name_result, String comment){

        FirebaseDatabase.getInstance().getReference().child(bundleuid).child("token")
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
                        new FcmNotificationsSender(usertoken,"Twito",name_result+" Commented on your post: " + comment,
                                getApplicationContext(),CommentsActivity.this);

                notificationsSender.SendNotifications();

            }
        },3000);

    }
}
