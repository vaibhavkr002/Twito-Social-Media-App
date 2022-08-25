package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ShavedQuestionActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference ;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedquestion);

        recyclerView = findViewById(R.id.rv_related);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();

        reference = database.getReference("favoriteList").child(currentUserid);


        FirebaseRecyclerOptions<QuestionMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionMember>()
                        .setQuery(reference,QuestionMember.class)
                        .build();

        FirebaseRecyclerAdapter<QuestionMember,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final QuestionMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final  String postkey = getRef(position).getKey();

                        holder.setitemRelated(getApplication(),model.getName(),model.getUrl(),model.getUserid(),model.getKey()
                                ,model.getQuestion(),model.getPrivacy(),model.getTime());

                        final String que = getItem(position).getQuestion();
//                        final String name = getItem(position).getName();
//                        final String url = getItem(position).getUrl();
//                        final  String time = getItem(position).getTime();
//                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();


                        holder.replybtn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ShavedQuestionActivity.this,ReplyActivity.class);
                                intent.putExtra("uid",userid);
                                intent.putExtra("q",que);
                                intent.putExtra("postkey",postkey);
                                startActivity(intent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.saveque_item,parent,false);

                        return new Viewholder_Question(view);



                    }
                };
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}