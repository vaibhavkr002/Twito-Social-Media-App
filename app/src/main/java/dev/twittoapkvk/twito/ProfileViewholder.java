package dev.twittoapkvk.twito;

import android.app.Application;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileViewholder extends RecyclerView.ViewHolder {

    TextView textViewName,textViewProfession;
    Button vp_ll;
    TextView namell,namefollower,professionFollower;
    All_UserMmber member;
    UploadTask uploadTask;
    DatabaseReference blockref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
     Button viewUserprofile;
    RelativeLayout relativeLayout;
    ImageView imageView,iv_ll,iv_follower,imageView1;
    CardView cardView,vpfollower,sendmessagebtn;
    public ProfileViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfile(FragmentActivity fragmentActivity, String name, String uid, String prof,
                           String url){



        cardView = itemView.findViewById(R.id.cardview_profile);
        textViewName = itemView.findViewById(R.id.tv_name_profile);
        viewUserprofile = itemView.findViewById(R.id.viewUser_profile);
        imageView = itemView.findViewById(R.id.profile_imageview);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();


        blockref = database.getReference("Block users").child(currentuid);

        blockref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(uid)){
                    textViewName.setText("Blocked user");
                    viewUserprofile.setVisibility(View.GONE);


                }else {

                    Picasso.get().load(url).into(imageView);
                    textViewName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setProfileInchat(Application application, String name, String uid, String prof,
                                 String url){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        ImageView imageView = itemView.findViewById(R.id.iv_ch_item);
       imageView1 = itemView .findViewById(R.id.calls);
        TextView nametv = itemView.findViewById(R.id.name_ch_item_tv);
       sendmessagebtn = itemView.findViewById(R.id.send_messagech_item_btn);
 relativeLayout = itemView.findViewById(R.id.rl);



        blockref = database.getReference("Block users").child(userid);

        blockref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(uid)){
                   relativeLayout.setVisibility(View.GONE);
                   sendmessagebtn.setVisibility(View.GONE);
                }else {

                    if (userid.equals(uid)){

                      relativeLayout.setVisibility(View.GONE);
                        sendmessagebtn.setVisibility(View.GONE);
                    }else {
                        Picasso.get().load(url).into(imageView);
                        nametv.setText(name);
                       // proftv.setText(prof);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (userid.equals(uid)){
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);

            sendmessagebtn.setVisibility(View.INVISIBLE);

        }else {
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);

        }

    }

    public void setFollower( Application application, String name, String url,
            String profession, String bio, String privacy, String email, String followers, String website){

        iv_follower = itemView.findViewById(R.id.iv_follower);
        professionFollower = itemView.findViewById(R.id.profession_follower);
        namefollower = itemView.findViewById(R.id.name_follower);
        vpfollower = itemView.findViewById(R.id.vp_follower);

        Picasso.get().load(url).into(iv_follower);
        namefollower.setText(name);
        professionFollower.setText(profession);



    }


    public void setLikeduser(Application application, String name, String uid, String prof, String url) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        vp_ll = itemView.findViewById(R.id.vp_ll);
        namell = itemView.findViewById(R.id.name_ll);
        iv_ll = itemView.findViewById(R.id.iv_ll);

        Picasso.get().load(url).into(iv_ll);
        namell.setText(name);

    }
}
