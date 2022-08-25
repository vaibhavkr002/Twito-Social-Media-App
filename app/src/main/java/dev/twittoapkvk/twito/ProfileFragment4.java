package dev.twittoapkvk.twito;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment4 extends Fragment implements View.OnClickListener{

   ImageView imageView;
   ImageView image,noti;
   TextView nameEt,bioEt ,feeds,postTv,newtv,followertv,followersss,profEt,webEt,study,currentsitty,from,hobbiees;
    CardView  imageButtonMenu,psted,imageButtonEdit,btnsendmessage,addpst;

    DocumentReference reference;
    FirebaseFirestore firestore;

    Uri imageUri;
    String url,userid;
    private static int PICK_IMAGE=1;
    int followerno,post1,post2,newcount;

    FirebaseAuth mAuth;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db1,db2,db3,ntRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profilefragment,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("user").document(userid);

        mAuth= FirebaseAuth.getInstance();

        db1 = database.getReference("followers").child(userid);
        db2 = database.getReference("All images").child(userid);
        db3 = database.getReference("All videos").child(userid);


        noti = getActivity().findViewById(R.id.noti);
        ntRef = database.getReference("notification").child(userid);
        feeds = getActivity().findViewById(R.id.feeds);
        imageView = getActivity().findViewById(R.id.iv_f1);
        image = getActivity().findViewById(R.id.image);
        nameEt = getActivity().findViewById(R.id.tv_name_f1);
        profEt = getActivity().findViewById(R.id.tv_prof_f1);
        bioEt = getActivity().findViewById(R.id.tv_bio_f1);
        study= getActivity().findViewById(R.id.study);
        currentsitty = getActivity().findViewById(R.id.currentcities);
        from = getActivity().findViewById(R.id.from);
        hobbiees = getActivity().findViewById(R.id.hobbiees);

        webEt = getActivity().findViewById(R.id.tv_web_f1);
        addpst = getActivity().findViewById(R.id.ib_addpst_f1);
        postTv = getActivity().findViewById(R.id.tv_post_f1);
        followersss = getActivity().findViewById(R.id.followersss);
        btnsendmessage = getActivity().findViewById(R.id.btn_sendmessage_f1);
        followertv = getActivity().findViewById(R.id.tv_followers_f1);
        newtv = getActivity().findViewById(R.id.tv_newf1);
       psted = getActivity().findViewById(R.id.psted);


        imageButtonEdit = getActivity().findViewById(R.id.ib_edit_f1);
        imageButtonMenu = getActivity().findViewById(R.id.ib_menu_f1);
        postTv.setOnClickListener(this);

       imageButtonMenu.setOnClickListener(this);
       imageButtonEdit.setOnClickListener(this);
       noti.setOnClickListener(this);
       addpst.setOnClickListener(this);
       imageView.setOnClickListener(this);
       followersss.setOnClickListener(this);
       btnsendmessage.setOnClickListener(this);
       webEt.setOnClickListener(this);
     psted.setOnClickListener(this);
     feeds.setOnClickListener(this);
       followertv.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_edit_f1:
                Intent intent = new Intent(getActivity(),UpdateDpActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                showBottomSheet();
                break;

            case R.id.ib_addpst_f1:
                showBottomsheetpost();
                break;


            case R.id.noti:
                Intent intent9 = new Intent(getActivity(),NotificationActivity.class);
                Toast.makeText(getActivity(), "Work in progress...", Toast.LENGTH_SHORT).show();
                startActivity(intent9);
                changeSeen();
                break;


            case R.id.tv_post_f1:
                Intent intent5 = new Intent(getActivity(),IndividualPost.class);
                changeSeen();
                startActivity(intent5);
                break;
            case R.id.btn_sendmessage_f1:
                Intent in = new Intent(getActivity(),ChatActivity.class);
                startActivity(in);
                break;
            case R.id.psted:
                Intent intent4 = new Intent(getActivity(),IndividualPost.class);
                startActivity(intent4);

                break;

            case R.id.feeds:
                Intent intent11 = new Intent(getActivity(),IndividualPost.class);
                startActivity(intent11);

                break;

            case R.id.followersss:
                Intent followersss = new Intent(getActivity(),FollowerActivity.class);
                followersss.putExtra("u",userid);
                startActivity(followersss);


            case R.id.tv_followers_f1:
                    Intent follower = new Intent(getActivity(),FollowerActivity.class);
                    follower.putExtra("u",userid);
                    startActivity(follower);

                break;
            case R.id.tv_web_f1:
                try {
                    String url = webEt.getText().toString();
                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
                    intent2.setData(Uri.parse(url));
                    startActivity(intent2);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void showBottomsheetpost() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.f4_bottomsheet);

        TextView tvcp = dialog.findViewById(R.id.tv_cpf4);
        TextView tvcs = dialog.findViewById(R.id.tv_csf4);


        tvcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });

        tvcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentstory = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentstory.setType("image/*");
                startActivityForResult(intentstory,PICK_IMAGE);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }
    private void showBottomSheet() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_menu);

       CardView logout,privacy,settings,delete;

        logout = dialog.findViewById(R.id.logout_profile);
      //  privacy = dialog.findViewById(R.id.privacy_profile);
        delete = dialog.findViewById(R.id.del_profile);
        settings = dialog.findViewById(R.id.settings_profile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Logout")
                        .setMessage("Are you sure to Logout")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mAuth.signOut();

                                FirebaseDatabase.getInstance().getReference("Token").child(userid).child("token").removeValue();
                                startActivity(new Intent(getActivity(),LoginActivity.class));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Account")
                        .setMessage("Are you sure to delete?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //   StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl()

                                deleteImage();
                                reference.delete()

                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(getActivity(), "Account delete failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();



            }
        });
//
//

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),SettingsActivity.class));

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void deleteImage() {

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {

                            String Url = task.getResult().getString("url");
                            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url);
                            reference.delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                        }
                                    });

                        } else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                    data != null || data.getData() != null) {
                imageUri = data.getData();

                String url = imageUri.toString();
                Intent intent = new Intent(getActivity(),StoryActivity.class);
                intent.putExtra("u",url);
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

            Toast.makeText(getActivity(), "Error"+e, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){

                            String nameResult = task.getResult().getString("name");
                            String bioResult = task.getResult().getString("bio");
                            String emailResult = task.getResult().getString("email");
                            String webResult = task.getResult().getString("web");
                            url = task.getResult().getString("url");
                            String profResult = task.getResult().getString("prof");
                            String eduResult= task.getResult().getString("education");
                            String currentcityResult= task.getResult().getString("currentcity");
                            String fromResult= task.getResult().getString("hometown");
                            String hobbyResult= task.getResult().getString("hobby");


                            Picasso.get().load(url).into(imageView);
                            Picasso.get().load(url).into(image);
                            nameEt.setText(nameResult);
                            bioEt.setText(bioResult);
                            webEt.setText(webResult);
                            profEt.setText(profResult);
                            study.setText(eduResult);
                            currentsitty.setText(currentcityResult);
                            from.setText(fromResult);
                            hobbiees.setText(hobbyResult);


                        }else {
//
                            Intent intent = new Intent(getActivity(),UpdateDpActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Please create profile.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        Query query = ntRef.orderByChild("seen").equalTo("no");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    newcount = (int) snapshot.getChildrenCount();
                    newtv.setText(Integer.toString(newcount) +" ");
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        followerno = (int)snapshot.getChildrenCount();
                        followertv.setText(Integer.toString(followerno)+" ");


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        post1 = (int)snapshot.getChildrenCount();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                db3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        post2 = (int)snapshot.getChildrenCount();
                        String total = Integer.toString(post1+post2);
                        postTv.setText(total+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void changeSeen(){

        Map<String,Object > profile = new HashMap<>();
        profile.put("seen","yes");

        ntRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataSnapshot.getRef().updateChildren(profile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
