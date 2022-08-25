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


public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ntRef;
    String userid;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_new);
        ntRef = database.getReference("notification").child(userid);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ntRef.keepSynced(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        toolbar=findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<NewMember> options1 =
                new FirebaseRecyclerOptions.Builder<NewMember>()
                        .setQuery(ntRef,NewMember.class)
                        .build();

        FirebaseRecyclerAdapter<NewMember,NewViewHolder> firebaseRecyclerAdapter1 =
                new FirebaseRecyclerAdapter<NewMember, NewViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull NewViewHolder holder, int position, @NonNull NewMember model) {

                        holder.setNt(getApplication(),model.getUrl(),model.getName(),model.getText(),model.getUid(),model.getSeen());

                        String name = getItem(position).getName();
                        String uid = getItem(position).getUid();
                        String url = getItem(position).getUrl();

                        holder.nametv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (userid.equals(uid)) {
                                    Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }else {
                                    Intent intent = new Intent(NotificationActivity.this,ShowUser.class);
                                    intent.putExtra("n",name);
                                    intent.putExtra("u",url);
                                    intent.putExtra("uid",userid);
                                    startActivity(intent);
                                }

                            }
                        });



                    }

                    @NonNull
                    @Override
                    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.new_layout,parent,false);

                        return new NewViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter1.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter1);


    }



    }
