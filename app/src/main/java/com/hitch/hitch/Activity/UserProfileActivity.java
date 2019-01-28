package com.hitch.hitch.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.TabPagerAdapter;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.model.UserSettings;
import com.hitch.hitch.model.Users;
import com.hitch.hitch.utils.FirebaseMethods;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "UserProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseMethods firebaseMethods;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage, mCoverImage;
    private TextView mFullName, mUsername, mStatus, mHometown, mWebsite, mDOB;
    private int mMaxScrollSize;
    private CircleImageView mCircleProfileImage;
    private Context mContext;
    private FloatingActionButton fab;

    TabPagerAdapter adapter;
    CharSequence Titles[]={"Post","Photos","Details"};

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageButton setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);

        mCoverImage = (ImageView)findViewById(R.id.profile_backdrop);
        mCircleProfileImage = (CircleImageView)findViewById(R.id.profile_image);
        mFullName = (TextView)findViewById(R.id.fullnameprofile);
        mUsername = (TextView)findViewById(R.id.usernameprofile);
        mStatus = (TextView)findViewById(R.id.statusprofile);
        mHometown = (TextView)findViewById(R.id.locationprofile);
        mWebsite = (TextView)findViewById(R.id.websiteprofile);
        mDOB = (TextView)findViewById(R.id.dobprofile);
        mStatus = (TextView)findViewById(R.id.statusprofile);
        firebaseMethods = new FirebaseMethods(this);
        setUpTabs();
        setupFirebaseAuth();

        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        setting = (ImageButton)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, AccountSettingActivity.class);
                startActivity(i);
            }
        });

        Button editprofile = (Button)findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        //FAB code to update status
        fab = (FloatingActionButton)findViewById(R.id.fabbtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: FAB opening ");
                Intent intent = new Intent(UserProfileActivity.this, Status.class);
                startActivity(intent);
            }
        });

      /*  ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton search = (ImageButton)findViewById(R.id.search);
        ImageButton notification = (ImageButton)findViewById(R.id.notification);
        ImageButton profile = (ImageButton)findViewById(R.id.profile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });*/

    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, UserProfileActivity.class));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    void setUpTabs(){
        adapter =  new TabPagerAdapter(this.getSupportFragmentManager(),Titles,Titles.length);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setProfileWidgets(UserSettings userSettings){

        //Users user = userSettings.getUsers();
        UserAccountSettings settings = userSettings.getUserAccountSettings();

        // getProfilePhot not define in UserAccountSetting
        mUsername.setText(settings.getUsername());
        mFullName.setText(settings.getFullname());
        mHometown.setText(settings.getHometown());
        mWebsite.setText(settings.getWebsite_blogs());
        mDOB.setText(settings.getDob());
        mStatus.setText(settings.getStatus());


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
