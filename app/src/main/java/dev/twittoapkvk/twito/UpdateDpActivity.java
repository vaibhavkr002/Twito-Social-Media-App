package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
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
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

public class UpdateDpActivity extends AppCompatActivity {

    EditText etname,etBio,etProfession,etEmail,etWeb,education,currentcity,hometown,mobileno,gender,
            birthday,othernames,hobby,relation,fammemb;
    Button button,updatedp;
    ImageView imageView,camra1,image1;
    ProgressBar progressBar;
    Uri imageUri;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    private static final int PICK_IMAGE =1;
    private static final int PICK_BAKIMAGE =1;
    All_UserMmber member;
    String currentUserId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dp);

        member = new All_UserMmber();
        image1=findViewById(R.id.image7);

        education = findViewById(R.id.education);
       currentcity = findViewById(R.id.currentcity);
      hometown = findViewById(R.id.hometown);
        mobileno = findViewById(R.id.mobileno);
     gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        othernames = findViewById(R.id.otherNames);
      hobby = findViewById(R.id.hobby);
      camra1 = findViewById(R.id.camera7);
        relation = findViewById(R.id.relation);
       fammemb = findViewById(R.id.familymemb);
        imageView = findViewById(R.id.iv_cp7);
        etBio = findViewById(R.id.et_bio_cp7);
        etEmail = findViewById(R.id.et_email_cp7);
        etname = findViewById(R.id.et_name_cp7);
        etProfession = findViewById(R.id.et_profession_cp7);
        updatedp = findViewById(R.id.updatedp);
        etWeb = findViewById(R.id.et_web_cp7);
        button = findViewById(R.id.btn_cp7);
        progressBar = findViewById(R.id.progressbar_cp7);
toolbar=findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();


        documentReference = db.collection("user").document(currentUserId);
        storageReference = FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference = database.getReference("All Users");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();
            }
        });

        updatedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDp();
            }
        });

       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        camra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });


    }

    private void updateProfile() {
        final String name = etname.getText().toString();
        final String bio = etBio.getText().toString();
        final String prof = etProfession.getText().toString();
        final String web = etWeb.getText().toString();
        final String email =etEmail.getText().toString();
        final String educations =education.getText().toString();
        final String currentscity =currentcity.getText().toString();
        final String hometowns =hometown.getText().toString();
        final String mobilesno =mobileno.getText().toString();
        final String genders =gender.getText().toString();
        final String birthdays =birthday.getText().toString();
        final String othernamed =othernames.getText().toString();
        final String hobbies =hobby.getText().toString();
        final String relationes =relation.getText().toString();
        final String fammembers =fammemb.getText().toString();

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
                transaction.update(sDoc,"education",educations);
                transaction.update(sDoc,"currentcity",currentscity);
                transaction.update(sDoc,"hometown",hometowns);
                transaction.update(sDoc,"mobileno",mobilesno);
                transaction.update(sDoc,"gender",genders);
                transaction.update(sDoc,"birthday",birthdays);
                transaction.update(sDoc,"othernames",othernamed);
                transaction.update(sDoc,"hobby",hobbies);
                transaction.update(sDoc,"relation",relationes);
                transaction.update(sDoc,"fammemb",fammembers);

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateDpActivity.this, "Updated", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateDpActivity.this, "Failed, Please upload Profile photo.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                data != null || data.getData() != null) {
            imageUri = data.getData();


            Picasso.get().load(imageUri).into(imageView);

        }

        if (requestCode == PICK_BAKIMAGE || resultCode == RESULT_OK ||
                data != null || data.getData() != null) {
            imageUri = data.getData();



            Picasso.get().load(imageUri).into(image1);
        }

    }
    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }

    private void uploadDp() {

        final String name = etname.getText().toString();
        final String bio = etBio.getText().toString();
        final String web = etWeb.getText().toString();
        final String prof = etProfession.getText().toString();
        final String email = etEmail.getText().toString();
        final String educations =education.getText().toString();
        final String currentscity =currentcity.getText().toString();
        final String hometowns =hometown.getText().toString();
        final String mobilesno =mobileno.getText().toString();
        final String genders =gender.getText().toString();
        final String birthdays =birthday.getText().toString();
        final String othernamed =othernames.getText().toString();
        final String hobbies =hobby.getText().toString();
        final String relationes =relation.getText().toString();
        final String fammembers =fammemb.getText().toString();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(bio)  || !TextUtils.isEmpty(web)  || !TextUtils.isEmpty(prof)
                || !TextUtils.isEmpty(email) ||!TextUtils.isEmpty(educations) ||!TextUtils.isEmpty(currentscity) ||!TextUtils.isEmpty(hometowns) ||!TextUtils.isEmpty(mobilesno) ||!TextUtils.isEmpty(genders) ||
                !TextUtils.isEmpty(birthdays) ||!TextUtils.isEmpty(othernamed) ||!TextUtils.isEmpty(hobbies) ||!TextUtils.isEmpty(relationes) ||!TextUtils.isEmpty(fammembers) ||imageUri != null ){

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();

                        Map<String,String > profile = new HashMap<>();
                        profile.put("name",name);
                        profile.put("prof",prof);
                        profile.put("url",downloadUri.toString());
                        profile.put("email",email);
                        profile.put("web",web);
                        profile.put("bio",bio);
                        profile.put("education",educations);
                        profile.put("currentcity",currentscity);
                        profile.put("hometown",hometowns);
                        profile.put("mobileno",mobilesno);
                        profile.put("gender",genders);
                        profile.put("birthday",birthdays);
                        profile.put("othernames",othernamed);
                        profile.put("hobby",hobbies);
                        profile.put("relation",relationes);
                        profile.put("fammemb",fammembers);
                        profile.put("uid",currentUserId);
                        profile.put("privacy","Public");

                        member.setName(name);
                        member.setProf(prof);
                        member.setUid(currentUserId);
                        member.setUrl(downloadUri.toString());

                        databaseReference.child(currentUserId).setValue(member);

                        documentReference.set(profile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(UpdateDpActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
//                                                Intent intent = new Intent(UpdateDpActivity.this, ProfileFragment4.class);
//                                                startActivity(intent);
                                            }
                                        },2000);
                                    }
                                });
                    }

                }
            });

        }else {
            Toast.makeText(this, "Please fill all Fields", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();


        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {
                            String nameResult = task.getResult().getString("name");
                            String bioResult = task.getResult().getString("bio");
                            String emailResult = task.getResult().getString("email");
                            String webResult = task.getResult().getString("web");
                            String url = task.getResult().getString("url");
                            String profResult = task.getResult().getString("prof");
                            String eduResult = task.getResult().getString("education");
                            String currentcityResult = task.getResult().getString("currentcity");
                            String hometownResult = task.getResult().getString("hometown");
                            String mobilenoResult = task.getResult().getString("mobileno");
                            String genderResult = task.getResult().getString("gender");
                            String birthdayResult = task.getResult().getString("birthday");
                            String othernamesResult = task.getResult().getString("othernames");
                            String hobbyResult = task.getResult().getString("hobby");
                            String relationResult = task.getResult().getString("relation");
                            String fammembResult = task.getResult().getString("fammemb");


                            etname.setText(nameResult);
                            etBio.setText(bioResult);
                            etEmail.setText(emailResult);
                            etWeb.setText(webResult);
                            education.setText(eduResult);
                            currentcity.setText(currentcityResult);
                            hometown.setText(hometownResult);
                            mobileno.setText(mobilenoResult);
                            gender.setText(genderResult);
                            etProfession.setText(profResult);
                            birthday.setText(birthdayResult);
                            othernames.setText(othernamesResult);
                            hobby.setText(hobbyResult);
                            relation.setText(relationResult);
                            fammemb.setText(fammembResult);


                        } else {
                            Toast.makeText(UpdateDpActivity.this, "No profile ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}