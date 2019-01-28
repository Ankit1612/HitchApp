package com.hitch.hitch.Activity;



import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.FeedAdapter;
import com.hitch.hitch.model.Feed;
import com.hitch.hitch.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FeedAdapter adapter;
    private List<Photo> mPhotos;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageButton home;
    private ImageButton search;
    private ImageButton notification;
    private ImageButton profile;
    private ImageButton gallery;

    private ImageButton galleryPost;

    private Context mContext = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      //  Toolbar toptoolbar = (Toolbar)findViewById(R.id.toptoolbar);
      //  setSupportActionBar(toptoolbar);

        mPhotos = new ArrayList<>();
        adapter = new FeedAdapter(this,mPhotos);

        mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView = (RecyclerView)findViewById(R.id.rvFeed);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        setupFirebaseAuth();

        setupNavButton();


      /*  Feed feed = new Feed("Ankit Shah","Nanital, Pantnagar",0,R.drawable.sqr,"Is today any event going on in the www.google.com college if any body know please let me know soon thank you very much in advance my email id is shahankit1612@gmail.com and phone number is +91 8439524849","Fun in nanital with Friends","Nice Click","28 min",Feed.QUESTION_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",R.drawable.img1,R.drawable.img1,null,"Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img2,R.drawable.img2,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img3,R.drawable.img3,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.img11,R.drawable.img11,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",0,R.drawable.img5,"Is today any event going on in the www.google.com college if any body know please let me know soon thank you very much in advance my email id is shahankit1612@gmail.com and phone number is +91 8439524849","Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.QUESTION_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img6,R.drawable.img6,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img7,R.drawable.img7,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.giphy,R.drawable.img8,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",R.drawable.img9,R.drawable.img9,null,"Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",0,R.drawable.img10,"Is today any event going on in the www.google.com college if any body know please let me know soon thank you very much in advance my email id is shahankit1612@gmail.com and phone number is +91 8439524849","Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.QUESTION_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img11,R.drawable.img11,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.img12,R.drawable.img12,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",R.drawable.img13,R.drawable.img13,null,"Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img14,R.drawable.img14,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img15,R.drawable.img15,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.img16,R.drawable.img16,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",R.drawable.giphy_two,R.drawable.img17,null,"Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img18,R.drawable.img18,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img19,R.drawable.img19,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.img20,R.drawable.img20,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",R.drawable.img21,R.drawable.img21,null,"Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img22,R.drawable.img22,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img23,R.drawable.img23,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ankit Shah","Nanital, Pantnagar",R.drawable.img24,R.drawable.img24,null,"Fun in nanital with Friends","Nice Click","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Nishi Arya","GBPUAT, Pantnagar",0,R.drawable.img25,"Is today any event going on in the www.google.com college if any body know please let me know soon thank you very much in advance my email id is shahankit1612@gmail.com and phone number is +91 8439524849","Fun at Nanital enjoying with friends.","Mast pic hai","28 min",Feed.QUESTION_BOX);
        feedList.add(0, feed);

        feed = new Feed("Anjali Rawat","Laxmi Nagar, Pantnagar",R.drawable.img26,R.drawable.img26,null,"Fun at Nanital enjoying with friends.","Sahi hai yr","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        feed = new Feed("Ruby Mishra","Nanital, Pantnagar",R.drawable.img27,R.drawable.img27,null,"Fun at Nanital enjoying with friends.","Cute ness overloades","28 min",Feed.FEED_BOX);
        feedList.add(0, feed);

        adapter.notifyDataSetChanged();*/
    }

    private void setupNavButton(){
        home = (ImageButton)findViewById(R.id.home);
        search = (ImageButton)findViewById(R.id.search);
        notification = (ImageButton)findViewById(R.id.notification);
        profile = (ImageButton)findViewById(R.id.profile);
        gallery = (ImageButton)findViewById(R.id.gallery);

        galleryPost = (ImageButton)findViewById(R.id.galleryPost);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(i);
            }
        });

        galleryPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(i);
            }
        });

    }

    /*
    ----------------------------------- FireBase --------------------------------------------------
     */

    /*
     * check if the user is logged in
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: Checking if the user is logged in ");

        if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check current user login
                checkCurrentUser(user);

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

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
