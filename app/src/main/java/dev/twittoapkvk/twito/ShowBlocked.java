package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowBlocked extends AppCompatActivity {

    RecyclerView recyclerView ;
    TextView textView;
    DatabaseReference blockRef,blocklistref ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String currentuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blocked);

        textView = findViewById(R.id.back);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid = user.getUid();

        blockRef = database.getReference("Block users").child(currentuid);
        blocklistref = database.getReference("Blocklist").child(currentuid);
        recyclerView = findViewById(R.id.rv_block);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ShowBlocked.this,SettingsActivity.class);
        startActivity(intent);
    }
});

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<NewMember> options1 =
                new FirebaseRecyclerOptions.Builder<NewMember>()
                        .setQuery(blocklistref, NewMember.class)
                        .build();

        FirebaseRecyclerAdapter<NewMember, BlockVH> firebaseRecyclerAdapter1 =
                new FirebaseRecyclerAdapter<NewMember, BlockVH>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull BlockVH holder, int position, @NonNull NewMember model) {


                        String name = getItem(position).getName();
                        String url = getItem(position).getUrl();
                        String uid = getItem(position).getUid();


                        holder.setBlockList(getApplication(),model.getUrl(),
                                model.getName(),model.getText(),model.getUid(),model.getSeen());

                        holder.unblocktv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                unBlock(uid);
                            }
                        });


                    }



                    @NonNull
                    @Override
                    public BlockVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.block_user_item, parent, false);

                        return new BlockVH(view);
                    }
                };


        firebaseRecyclerAdapter1.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter1);
    }

    private void unBlock(String uid) {

        blockRef.child(uid).removeValue();

        Query query = blocklistref.orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();

                    Toast.makeText(ShowBlocked.this, "Unblocked", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}