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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.R;
import com.hitch.hitch.utils.FirebaseMethods;

public class AboutDetailActivity extends AppCompatActivity {

    private static final String TAG = "AboutDetailActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private Button next;
    private EditText hobbies;
    private EditText aboutme;

    private String Hobbies;
    private String Aboutme;
    private String userID;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_detail);

        mContext = AboutDetailActivity.this;

        hobbies = (EditText)findViewById(R.id.hobbies);
        aboutme = (EditText)findViewById(R.id.aboutme);
        next = (Button)findViewById(R.id.about_detail_next);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if(CheckInputs(Hobbies, Aboutme)){
                    Log.d(TAG, "onClick: hobbies "+ Hobbies);
                    Log.d(TAG, "onClick: Aboutme "+ Aboutme);
                    addhobbiesabout();
                    Intent i = new Intent(AboutDetailActivity.this, EducationalDetailActivity.class);
                    startActivity(i);
                    finish();
              //  }
            }
        });
        setupFirebaseAuth();
    }

    private void addhobbiesabout(){

        Hobbies = hobbies.getText().toString();
        Aboutme = aboutme.getText().toString();

        Log.d(TAG, "onCreate: Hobbies "+Hobbies);
        Log.d(TAG, "onCreate: Aout me "+ Aboutme);

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_hobbies))
                .setValue(Hobbies);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_about_me))
                .setValue(Aboutme);
    }


 /*   private boolean CheckInputs(String Hobbies,String Aboutme){
        Log.d(TAG, "ifCheckInputs: Check inputs empty or not");
        Log.d(TAG, "CheckInputs: Hobbies "+ Hobbies);
        Log.d(TAG, "CheckInputs: Aboutme "+ Aboutme);

        if (Hobbies.equals("") || Aboutme.equals("")){
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
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
                    userID = user.getUid();
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
