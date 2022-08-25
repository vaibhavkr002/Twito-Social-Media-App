package dev.twittoapkvk.twito;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.Calendar;

public class PostActivity extends AppCompatActivity {


    ImageView imageView,uploadvideo,uploadpic,back;
    ImageButton btnupload;
    ProgressBar progressBar;
    private Uri selectedUri;
    private  static final int PICK_IMAGE = 1;
    private  static  final int PICK_VIDEO = 2;
    UploadTask uploadTask;
    EditText etdesc;
    VideoView videoView;
    String url,name;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db1,db2,db3;

    MediaController mediaController;
    String type;
    Postmember postmember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

back = findViewById(R.id.back);
        postmember = new Postmember();
        mediaController = new MediaController(this);
          btnupload = findViewById(R.id.btn_uploadfile_post);
        progressBar = findViewById(R.id.pb_post);
        imageView = findViewById(R.id.iv_post);
        videoView= findViewById(R.id.vv_post);
   uploadpic = findViewById(R.id.uploadpic);
   uploadvideo = findViewById(R.id.uploadvideo);
        etdesc = findViewById(R.id.et_desc_post);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(PostActivity.this,MainActivity.class);
        startActivity(intent);
    }
});
        storageReference = FirebaseStorage.getInstance().getReference("User posts");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        db1 = database.getReference("All images").child(currentuid);
        db2 = database.getReference("All videos").child(currentuid);
        db3 = database.getReference("All posts");

        etdesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Dopost();
            }
        });

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"image"),PICK_IMAGE);
            }
        });
        uploadvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                // intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"video"),PICK_VIDEO);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==PICK_IMAGE){
                selectedUri = data.getData();
                Picasso.get().load(selectedUri).into(imageView);
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.INVISIBLE);
                type = "iv";
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            }
            if (requestCode==PICK_VIDEO){
                selectedUri = data.getData();
                videoView.setMediaController(mediaController);
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                videoView.setVideoURI(selectedUri);
                videoView.start();
                type = "vv";
                Toast.makeText(this, "Video selected", Toast.LENGTH_SHORT).show();
            }else {
                // Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("user").document(currentuid);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {
                            name = task.getResult().getString("name");
                            url = task.getResult().getString("url");


                        } else {
                            Toast.makeText(PostActivity.this, "error", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void Dopost(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentuid = user.getUid();

        final String desc = etdesc.getText().toString();

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final  String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());


        final String time = savedate +":"+ savetime;


        if (TextUtils.isEmpty(desc) || selectedUri != null){

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(selectedUri));
            uploadTask = reference.putFile(selectedUri);

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

                        if (type.equals("iv")){
                            postmember.setDesc(desc);
                            postmember.setName(name);
                            postmember.setPostUri(downloadUri.toString());
                            postmember.setTime(time);
                            postmember.setUid(currentuid);
                            postmember.setUrl(url);
                            postmember.setType("iv");

                            // for image
                            String id = db1.push().getKey();
                            db1.child(id).setValue(postmember);
                            // for both
                            String id1 = db3.push().getKey();
                            db3.child(id1).setValue(postmember);

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(PostActivity.this, "Post uploaded", Toast.LENGTH_SHORT).show();
                        }else if (type.equals("vv")){

                            postmember.setDesc(desc);
                            postmember.setName(name);
                            postmember.setPostUri(downloadUri.toString());
                            postmember.setTime(time);
                            postmember.setUid(currentuid);
                            postmember.setUrl(url);
                            postmember.setType("vv");

                            // for video
                            String id3 = db2.push().getKey();
                            db2.child(id3).setValue(postmember);

                            // for both
                            String id4 = db3.push().getKey();
                            db3.child(id4).setValue(postmember);

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(PostActivity.this, "Post Uploaded", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(PostActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
            });

        }else {
            Toast.makeText(this, "Please fill all Fields", Toast.LENGTH_SHORT).show();
        }

        }
    }
