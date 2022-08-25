package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class UpdateProfile extends AppCompatActivity {

    EditText etname,etBio,etProfession,etEmail,etWeb;
    Button button;
    ImageView imageView,camra1,imaget;
    Uri imageUri;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    private static final int PICK_IMAGE =1;
    DocumentReference documentReference ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String  currentuid= user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference = database.getReference("All Users");
        documentReference = db.collection("user").document(currentuid);

        etBio = findViewById(R.id.et_bio_up);
        camra1 = findViewById(R.id.camera6);
        imaget = findViewById(R.id.image6);
        imageView = findViewById(R.id.iv_cp6);
        etEmail = findViewById(R.id.et_email_up);
        etname = findViewById(R.id.et_name_up);
        etProfession = findViewById(R.id.et_profession_up);
        etWeb = findViewById(R.id.et_web_up);
        button = findViewById(R.id.btn_up);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();

            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();



        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){
                            String nameResult = task.getResult().getString("name");
                            String bioResult = task.getResult().getString("bio");
                            String emailResult = task.getResult().getString("email");
                            String webResult = task.getResult().getString("web");
                            String url = task.getResult().getString("url");
                            String profResult = task.getResult().getString("prof");


                            etname.setText(nameResult);
                            etBio.setText(bioResult);
                            etEmail.setText(emailResult);
                            etWeb.setText(webResult);
                            etProfession.setText(profResult);
                        }else {
                            Toast.makeText(UpdateProfile.this, "No profile ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void updateProfile() {

        final String name = etname.getText().toString();
        final String bio = etBio.getText().toString();
        final String prof = etProfession.getText().toString();
        final String web = etWeb.getText().toString();
        final String email =etEmail.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid1= user.getUid();
        final  DocumentReference sDoc = db.collection("user").document(currentuid1);
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
              DocumentSnapshot snapshot = transaction.get(sDoc);


                transaction.update(sDoc, "name",name );
                transaction.update(sDoc,"prof",prof);
                transaction.update(sDoc,"email",email);
                transaction.update(sDoc,"web",web);
                transaction.update(sDoc,"bio",bio);


                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateProfile.this, "updated", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });


        }


}

