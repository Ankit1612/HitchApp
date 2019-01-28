package com.hitch.hitch.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.CommonRecyclerAdapter;
import com.hitch.hitch.adapter.GalleryImageAdapter;
import com.hitch.hitch.model.Feed;
import com.hitch.hitch.model.Photo;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.utils.FirebaseMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    List<Photo> imageList = new ArrayList<>();
    List<UserAccountSettings> accountList = new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;

    private Photo mPhoto;
    private UserAccountSettings mUserAccountSettings;
    private CommonRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_tab_fragment,null);
        setupFirebaseAuth();
        setUpView(root);
        return root;
    }

    void setUpView(ViewGroup root){
        ButterKnife.bind(this, root);
        setUPList();
    }

    void setUPList(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference refrecence = mFirebaseDatabase.getInstance().getReference();

        Query query = refrecence
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: adding photos: "+ singleSnapshot.getValue(Photo.class));
                    mPhoto = singleSnapshot.getValue(Photo.class);
                    Log.d(TAG, "onDataChange: photo "+mPhoto);
                    imageList.add(mPhoto);
                }
                adapter = new CommonRecyclerAdapter(getActivity(), imageList, null);
               recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });

        Query query2 = refrecence
                .child(getString(R.string.dbname_user_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                Log.d(TAG, "onDataChange: adding photos: "+ dataSnapshot.getValue(UserAccountSettings.class));
                mUserAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                Log.d(TAG, "onDataChange: photo "+mUserAccountSettings);
                accountList.add(mUserAccountSettings);
                adapter = new CommonRecyclerAdapter(getActivity(), null, accountList);
                recyclerView.setAdapter(adapter);
                //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });

      //  adapter = new CommonRecyclerAdapter(getActivity(), imageList, accountList);
     //   recyclerView.setAdapter(adapter);

    }

    /*
    ----------------------------------- FireBase --------------------------------------------------
    */

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
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
            }
        };

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
