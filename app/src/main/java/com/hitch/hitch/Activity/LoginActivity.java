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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hitch.hitch.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    TextView create_account;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private EditText mEmail, mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText)findViewById(R.id.login_email_name);
        mPassword = (EditText)findViewById(R.id.login_password);

        setupFirebaseAuth();
        init();
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string for null");
        if(string.equals("")){
            return true;
        } else {
            return false;
        }
    }
    
    /*
    ----------------------------------- FireBase --------------------------------------------------
    */

        private void init(){
            //initialize button for logging in
            Button login_btn = (Button)findViewById(R.id.login_button);
            login_btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: attemping to login.");
                    String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();

                    if(isStringNull(email) || isStringNull(password)){
                        Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d(TAG, "onComplete: Successful login");
                                            try{
                                                if(user.isEmailVerified()){
                                                    Log.d(TAG, "onComplete: success email is verified");
                                                    Intent i = new Intent(LoginActivity.this, ProfileDetailActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                } else {
                                                    Toast.makeText(mContext, "email is not verified \n check your email inbox", Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                }
                                            } catch (NullPointerException e){
                                                Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                            }
                                        }
                                    }
                                });
                    }
                }
            });

            create_account = (TextView)findViewById(R.id.create_account);

            create_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(i);
                }
            });


            if(mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: Setting up firebase log.");
        mAuth = FirebaseAuth.getInstance();

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
