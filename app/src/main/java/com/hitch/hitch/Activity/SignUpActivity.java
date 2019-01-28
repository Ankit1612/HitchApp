package com.hitch.hitch.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.R;
import com.hitch.hitch.model.Users;
import com.hitch.hitch.utils.FirebaseMethods;
import com.hitch.hitch.utils.StringManuplation;

import butterknife.Bind;

public class SignUpActivity extends AppCompatActivity{

    private static final String TAG = "SignUpActivity";

    private EditText emailid;
    private EditText password;
    private EditText mUsername;
    private Button signupbtn;
    private TextView backtologin;
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private String email;
    private String pass;
    private String username;
    private String append = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = SignUpActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        emailid = (EditText) findViewById(R.id.signup_email_id);
        password = (EditText) findViewById(R.id.signup_password);
        mUsername = (EditText) findViewById(R.id.fullname);
        signupbtn = (Button)findViewById(R.id.signup_button);
        backtologin = (TextView) findViewById(R.id.backtologin);

        setupFirebaseAuth();
        init();

    }

    public void init(){
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = emailid.getText().toString();
                 pass = password.getText().toString();
                 username = mUsername.getText().toString();

                if(CheckInputs(email, pass, username)){
                    firebaseMethods.registerNewEmail(email, pass, username);
                }
            }
        });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean CheckInputs(String email,String password, String confirmpass){
        Log.d(TAG, "ifCheckInputs: Check inputs empty or not");
        if (email.equals("") || password.equals("") || confirmpass.equals("")){
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkIfUsernameExists(final String newusername){
        Log.d(TAG, "checkIfUsernameExists: checking if "+newusername+ " already exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(newusername);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if(singleSnapshot.exists()){
                        Log.d(TAG, "onDataChange: FOUND a MATCH: "+ singleSnapshot.getValue(Users.class).getUsername());

                          append = Long.toString(Math.round(Math.random() * 89999) + 10000);
                      //  append = myRef.push().getKey().substring(3,7);
                        Log.d(TAG, "onDataChange: user name already exists, appending random string: "+ append);
                    }
                }
                String mUsername = "";
                mUsername = "@"+newusername.toLowerCase()+append;

                //add new user to database
                firebaseMethods.addNewUser(email,mUsername, username);

                Toast.makeText(mContext, "SignUp successful. sending verification email", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /*
    ----------------------------------- FireBase --------------------------------------------------
    */

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkIfUsernameExists(StringManuplation.condenseUsername(username));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();
                    
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
