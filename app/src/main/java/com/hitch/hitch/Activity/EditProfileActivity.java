package com.hitch.hitch.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.hitch.hitch.utils.FirebaseMethods;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit Shah on 24-Sep-17.
 */

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseMethods firebaseMethods;

    //edit profile widgets
    private CircleImageView mCircleImageView;
    private TextView mChangeProfilePhoto;
    private EditText mProfileName, mDOB, mHometown;
    private EditText mUniversirty, mCollegeName, mCollegePlace, mBatch, mGraduate, mDegree, mBranch;
    private EditText mAwards, mProjects, mSociety, mWebsite;
    private EditText mHobbies, mAboutMe;

    private UserSettings mUserSettings;

    private String userID;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        mContext = EditProfileActivity.this;
        initWidgets();
        
        //back button to ProfielActivity
        ImageView backarrow = (ImageView)findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: return back to profileactivity");
                finish();
            }
        });

        //Save button to save data in database
        ImageView saveprofile = (ImageView)findViewById(R.id.saveprofile);
        saveprofile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save the data");
                saveProfileSettings();
            }
        });


        setupFirebaseAuth();
        getIncomingIntent();

    }

    private void initWidgets(){
        mCircleImageView = (CircleImageView)findViewById(R.id.profile_image);
        mChangeProfilePhoto = (TextView) findViewById(R.id.changeprofilephoto);
        mProfileName = (EditText)findViewById(R.id.editprofilename);
        mDOB = (EditText)findViewById(R.id.editdob);
        mHometown = (EditText)findViewById(R.id.edithometown);
        mUniversirty = (EditText)findViewById(R.id.edituniversityname);
        mCollegeName = (EditText)findViewById(R.id.editcollegename);
        mCollegePlace = (EditText)findViewById(R.id.editcollegelocation);
        mBatch = (EditText)findViewById(R.id.editbatchstart);
        mGraduate = (EditText)findViewById(R.id.editgraduation);
        mDegree = (EditText)findViewById(R.id.editdegree);
        mBranch = (EditText)findViewById(R.id.editbranch);
        mAwards = (EditText)findViewById(R.id.editaward);
        mProjects = (EditText)findViewById(R.id.editproject);
        mSociety = (EditText)findViewById(R.id.editsociety);
        mWebsite = (EditText)findViewById(R.id.editwebsite);
        mHobbies = (EditText)findViewById(R.id.edithobbies);
        mAboutMe = (EditText)findViewById(R.id.editaboutmyself);

        firebaseMethods = new FirebaseMethods(this);
    }

    private void setProfileWidgets(UserSettings userSettings){

        mUserSettings = userSettings;

       // Users user = userSettings.getUsers();
        UserAccountSettings settings = userSettings.getUserAccountSettings();

        // getProfilePhot not define in UserAccountSetting
        mProfileName.setText(settings.getFullname());
        mDOB.setText(settings.getDob());
        mHometown.setText(settings.getHometown());
        mUniversirty.setText(settings.getUniversity());
        mCollegeName.setText(settings.getCollege_name());
        mCollegePlace.setText(settings.getCollege_location());
        mBatch.setText(settings.getBatch_start_date());
        mGraduate.setText(settings.getGraduate());
        mDegree.setText(settings.getDegree_programme());
        mBranch.setText(settings.getBranch());
        mAwards.setText(settings.getAwards());
        mProjects.setText(settings.getProject_publication());
        mSociety.setText(settings.getSociety_ngo());
        mWebsite.setText(settings.getWebsite_blogs());
        mHobbies.setText(settings.getHobbies());
        mAboutMe.setText(settings.getAbout_me());

        mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: changing profile photo");

                Intent intent = new Intent(EditProfileActivity.this, ShareActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void saveProfileSettings(){
        final String fullname = mProfileName.getText().toString();
        final String dob = mDOB.getText().toString();
        final String hometown = mHometown.getText().toString();
        final String university = mUniversirty.getText().toString();
        final String college = mCollegeName.getText().toString();
        final String place = mCollegePlace.getText().toString();
        final String batch = mBatch.getText().toString();
        final String graduate = mGraduate.getText().toString();
        final String degree = mDegree.getText().toString();
        final String branch = mBranch.getText().toString();
        final String awards = mAwards.getText().toString();
        final String projects = mProjects.getText().toString();
        final String society = mSociety.getText().toString();
        final String website = mWebsite.getText().toString();
        final String hobbies = mHobbies.getText().toString();
        final String aboutme = mAboutMe.getText().toString();

        //update user account settings
        if(!mUserSettings.getUserAccountSettings().getFullname().equals(fullname)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_fullname))
                    .setValue(fullname);
        }
        if(!mUserSettings.getUserAccountSettings().getDob().equals(dob)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_dob))
                    .setValue(dob);
        }
        if(!mUserSettings.getUserAccountSettings().getHometown().equals(hometown)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_hometown))
                    .setValue(hometown);
        }
        if(!mUserSettings.getUserAccountSettings().getUniversity().equals(university)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_university))
                    .setValue(university);
        }
        if(!mUserSettings.getUserAccountSettings().getCollege_name().equals(college)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_college_name))
                    .setValue(college);
        }
        if(!mUserSettings.getUserAccountSettings().getCollege_location().equals(place)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_college_location))
                    .setValue(place);
        }
        if(!mUserSettings.getUserAccountSettings().getBatch_start_date().equals(batch)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_batch_start_date))
                    .setValue(batch);
        }
        if(!mUserSettings.getUserAccountSettings().getGraduate().equals(graduate)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_graduate))
                    .setValue(graduate);
        }
        if(!mUserSettings.getUserAccountSettings().getDegree_programme().equals(degree)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_degree_programme))
                    .setValue(degree);
        }
        if(!mUserSettings.getUserAccountSettings().getBranch().equals(branch)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_branch))
                    .setValue(branch);
        }
        if(!mUserSettings.getUserAccountSettings().getAwards().equals(awards)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_awards))
                    .setValue(awards);
        }
        if(!mUserSettings.getUserAccountSettings().getProject_publication().equals(projects)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_project_publication))
                    .setValue(projects);
        }
        if(!mUserSettings.getUserAccountSettings().getSociety_ngo().equals(society)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_society_ngo))
                    .setValue(society);
        }
        if(!mUserSettings.getUserAccountSettings().getWebsite_blogs().equals(website)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_website_blogs))
                    .setValue(website);
        }
        if(!mUserSettings.getUserAccountSettings().getHobbies().equals(hobbies)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_hobbies))
                    .setValue(hobbies);
        }
        if(!mUserSettings.getUserAccountSettings().getAbout_me().equals(aboutme)){
            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_about_me))
                    .setValue(aboutme);
        }
    }

    //receving image from gallery to change profile
    private void getIncomingIntent(){
        Intent intent = getIntent();
        Log.d(TAG, "getIncomingIntent: New incoming imgUrl");

        if(intent.hasExtra(getString(R.string.selected_image)) || intent.hasExtra(getString(R.string.selected_bitmap))) {
            if (intent.getStringExtra(getString(R.string.return_to_fragment)).equals(getString(R.string.edit_profile_fragment))) {
                if (intent.hasExtra(getString(R.string.selected_image))) {
                    //set the new profile picture
                    firebaseMethods = new FirebaseMethods(mContext);
                    firebaseMethods.uploadNewPhoto(getString(R.string.profile_photo), null, 0,
                            intent.getStringExtra(getString(R.string.selected_image)), null);
                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    firebaseMethods = new FirebaseMethods(mContext);
                    firebaseMethods.uploadNewPhoto(getString(R.string.profile_photo), null, 0,
                            null, (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
                }
            }
        }
    }

        /*
    ----------------------------------- FireBase --------------------------------------------------
    */

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();

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
                // ...
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // retrive information from database
                setProfileWidgets(firebaseMethods.getUserSettings(dataSnapshot));
                //retive images from the database
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
