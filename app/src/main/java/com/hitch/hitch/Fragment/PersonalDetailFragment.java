package com.hitch.hitch.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PersonalDetailFragment extends Fragment {

    private static final String TAG = "PersonalDetailFragment";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseMethods firebaseMethods;

    private TextView university;
    private TextView college;
    private TextView location;
    private TextView graduate;
    private TextView degree;
    private TextView branch;
    private TextView batch;
    private TextView award;
    private TextView project;
    private TextView society;
    private TextView websites;
    private TextView aboutme;
    private TextView hobbies;

    private Context mContext;

    public PersonalDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_detail, container, false);
        mContext = getActivity();
        initWidget(view);

        firebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();

        return view;
    }

    private void initWidget(View view){
        university = (TextView)view.findViewById(R.id.universityName);
        college = (TextView)view.findViewById(R.id.collegeName);
        location = (TextView)view.findViewById(R.id.placeName);
        graduate = (TextView)view.findViewById(R.id.graduateName);
        degree = (TextView)view.findViewById(R.id.degreeName);
        branch = (TextView)view.findViewById(R.id.branchName);
        batch = (TextView)view.findViewById(R.id.yearhName);
        award = (TextView)view.findViewById(R.id.awardName);
        project = (TextView)view.findViewById(R.id.projectName);
        society = (TextView)view.findViewById(R.id.societyName);
        websites = (TextView)view.findViewById(R.id.blogName);
        aboutme = (TextView)view.findViewById(R.id.aboutName);
        hobbies = (TextView)view.findViewById(R.id.hobbiesName);
    }

    private void setProfileWidgets(UserSettings userSettings){

        //Users user = userSettings.getUsers();
        UserAccountSettings settings = userSettings.getUserAccountSettings();

        // getProfilePhot not define in UserAccountSetting
        university.setText(settings.getUniversity());
        college.setText(settings.getCollege_name());
        location.setText(settings.getCollege_location());
        graduate.setText(settings.getGraduate());
        degree.setText(settings.getDegree_programme());
        branch.setText(settings.getBranch());
        batch.setText(settings.getBatch_start_date());
        award.setText(settings.getAwards());
        project.setText(settings.getProject_publication());
        society.setText(settings.getSociety_ngo());
        websites.setText(settings.getWebsite_blogs());
        aboutme.setText(settings.getAbout_me());
        hobbies.setText(settings.getHobbies());


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
