package dev.twittoapkvk.twito;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ChatActivity extends AppCompatActivity {
    DocumentReference reference1;
    DatabaseReference profileRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    EditText searchEt;
    ImageView imageView;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        searchEt = findViewById(R.id.search_userch);
        recyclerView = findViewById(R.id.rv_ch);
        recyclerView.setHasFixedSize(true);
        reference1 = db.collection("user").document(currentuid);
        imageView = findViewById(R.id.iv_f4);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        profileRef = database.getReference("All Users");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent intent = new Intent(ChatActivity.this,ChatShowSettingActivity.class);
startActivity(intent);
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String query = searchEt.getText().toString();
                Query search = profileRef.orderByChild("name").startAt(query).endAt(query+"\uf0ff");

                FirebaseRecyclerOptions<All_UserMmber> options1 =
                        new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                                .setQuery(search,All_UserMmber.class)
                                .build();

                FirebaseRecyclerAdapter<All_UserMmber,ProfileViewholder> firebaseRecyclerAdapter1 =
                        new FirebaseRecyclerAdapter<All_UserMmber, ProfileViewholder>(options1) {
                            @Override
                            protected void onBindViewHolder(@NonNull ProfileViewholder holder, int position, @NonNull All_UserMmber model) {

                                String userid = user.getUid();
                                final String postkey = getRef(position).getKey();

                                holder.setProfileInchat(getApplication(), model.getName(), model.getUid(), model.getProf(), model.getUrl());


                                String name = getItem(position).getName();
                                String url = getItem(position).getUrl();
                                String uid = getItem(position).getUid();

//
//                                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        if (currentuid.equals(userid)) {
//                                            Intent intent = new Intent(ChatActivity.this,MainActivity.class);
//                                            startActivity(intent);
//
//                                        }else {
//                                            Intent intent = new Intent(ChatActivity.this,ShowUser.class);
//                                            intent.putExtra("n",name);
//                                            intent.putExtra("u",url);
//                                            intent.putExtra("uid",userid);
//                                            startActivity(intent);
//                                        }
//
//
//                                    }
//                                });
//

                                holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChatActivity.this, MessageActivity.class);
                                        intent.putExtra("n", name);
                                        intent.putExtra("u", url);
                                        intent.putExtra("uid", uid);
                                        startActivity(intent);
                                    }
                                });
holder.imageView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(ChatActivity.this, "This feature is coming soon...", Toast.LENGTH_SHORT).show();
    }
});
                            }

                            @NonNull
                            @Override
                            public ProfileViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.chat_profile_item,parent,false);

                                return new ProfileViewholder(view);
                            }
                        };

                firebaseRecyclerAdapter1.startListening();
                recyclerView.setAdapter(firebaseRecyclerAdapter1);

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();




        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {


            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

                Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                startActivity(intent);

            }
        };
        TedPermission.with(ChatActivity.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .check();


        FirebaseRecyclerOptions<All_UserMmber> options1 =
                new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                        .setQuery(profileRef,All_UserMmber.class)
                        .build();

        FirebaseRecyclerAdapter<All_UserMmber,ProfileViewholder> firebaseRecyclerAdapter1 =
                new FirebaseRecyclerAdapter<All_UserMmber, ProfileViewholder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProfileViewholder holder, int position, @NonNull All_UserMmber model) {


                        final String postkey = getRef(position).getKey();

                        holder.setProfileInchat(getApplication(),model.getName(),model.getUid(),model.getProf(),model.getUrl());


                        String  name = getItem(position).getName();
                        String  url = getItem(position).getUrl();
                        String uid = getItem(position).getUid();


                        holder.sendmessagebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChatActivity.this,MessageActivity.class);
                                intent.putExtra("n",name);
                                intent.putExtra("u",url);
                                intent.putExtra("uid",uid);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProfileViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.chat_profile_item,parent,false);

                        return new ProfileViewholder(view);
                    }
                };

        firebaseRecyclerAdapter1.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter1);


        reference1.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String url = task.getResult().getString("url");

                            Picasso.get().load(url).into(imageView);
                        }else {
                            Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}