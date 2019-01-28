package com.hitch.hitch.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.R;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.model.UserSettings;
import com.hitch.hitch.model.Users;
import com.hitch.hitch.utils.FirebaseMethods;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProfileDetailActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseMethods firebaseMethods;

    private TextView profilename, username;
    private EditText hometown, dob;
    private Button next;
    private CircleImageView profileImage;

    private String dateofbirth;
    private String homeTown;

    private String userID;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        profilename = (TextView)findViewById(R.id.profile_name);
        username = (TextView)findViewById(R.id.user_name);
        hometown = (EditText)findViewById(R.id.hometown);
        dob = (EditText)findViewById(R.id.dob);
        profileImage = (CircleImageView)findViewById(R.id.profile_image);

        mContext = ProfileDetailActivity.this;

        firebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();

        next = (Button)findViewById(R.id.profile_detail_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    if(CheckInputs(dateofbirth, homeTown)){
                    adddobhometown();
                    Log.d(TAG, "ProfileName: username: "+ dateofbirth);
                    Log.d(TAG, "UserName: username: "+ homeTown);
                    Intent i = new Intent(ProfileDetailActivity.this, AboutDetailActivity.class);
                    startActivity(i);
              // }
            }
        });


    }

    private void adddobhometown(){

        dateofbirth = dob.getText().toString();
        homeTown = hometown.getText().toString();

        Log.d(TAG, "adddobhometown: dob" + dateofbirth);
        Log.d(TAG, "adddobhometown: hometown" + homeTown);

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_dob))
                .setValue(dateofbirth);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_hometown))
                .setValue(homeTown);
    }

    private void setUserandProfileName(UserSettings userSettings){

        Users user = userSettings.getUsers();
        UserAccountSettings settings = userSettings.getUserAccountSettings();

        Log.d(TAG, "setUserandProfileName: username: "+ user.getUsername());
        Log.d(TAG, "setUserandProfileName: username: "+ settings.getFullname());


        username.setText(user.getUsername());
        profilename.setText(settings.getFullname());
    }

   /* private boolean CheckInputs(String dateofbirth,String homeTown){
        Log.d(TAG, "ifCheckInputs: Check inputs empty or not");
        if (dateofbirth.equals("") || homeTown.equals("")){
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/
/*
--------------------------------------firebase----------------------------------------------------
 */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    userID = mAuth.getCurrentUser().getUid();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrive information from database
               setUserandProfileName(firebaseMethods.getUserSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
