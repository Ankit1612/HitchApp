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

public class WorkDetailActivity extends AppCompatActivity {

    private static final String TAG = "WorkDetailActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private EditText awards;
    private EditText projects;
    private EditText society;
    private EditText website;

    private String Awards;
    private String Projects;
    private String Society;
    private String Website;

    private Context mContext;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        awards = (EditText)findViewById(R.id.awards);
        projects = (EditText)findViewById(R.id.projects);
        society = (EditText)findViewById(R.id.society);
        website = (EditText)findViewById(R.id.websites);



        mContext = WorkDetailActivity.this;

        Button btn = (Button)findViewById(R.id.work_detail_next);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if(CheckInputs(Awards,Projects,Society,Website)) {
                    adddobhometown();
                    Intent i = new Intent(WorkDetailActivity.this, FinishDetailActivity.class);
                    startActivity(i);
             //   }
            }
        });

        setupFirebaseAuth();
    }

    private void adddobhometown(){

        Awards = awards.getText().toString();
        Projects = projects.getText().toString();
        Society = society.getText().toString();
        Website = website.getText().toString();

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_awards))
                .setValue(Awards);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_project_publication))
                .setValue(Projects);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_society_ngo))
                .setValue(Society);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_website_blogs))
                .setValue(Website);
    }

   /* private boolean CheckInputs(String Awards,String Projects,String Society,String Website){
        Log.d(TAG, "ifCheckInputs: Check inputs empty or not");
        if (Awards.equals("") || Projects.equals("") || Society.equals("") || Website.equals("")){
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/

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
