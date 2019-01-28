package com.hitch.hitch.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class EducationalDetailActivity extends AppCompatActivity {

    private static final String TAG = "EducationalDetailActivi";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private EditText universityname;
    private EditText collegename;
    private EditText location;
    private EditText batch;
    private EditText graduate;
    private EditText degreeprogramme;
    private EditText branch;

    private String University = "";
    private String College = "";
    private String Location = "";
    private String Batch = "";
    private String Graduate = "";
    private String Degree = "";
    private String Branch = "";

    private String userID;
    private Context mContext;


  //  String[] graduation = {"Diploma","Undergraduate", "PostGraduate", "Doctrate"};
  //  String[] degree = {"Polytechnic","B.tech/BE","B.Arch","B.Sc","BBA","M.tech","M.Sc","MBA","Phd"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_detail);

        universityname = (EditText)findViewById(R.id.unvi_name);
        collegename = (EditText)findViewById(R.id.college_name);
        location = (EditText)findViewById(R.id.college_place);
        batch = (EditText)findViewById(R.id.batch_start_date);
        graduate = (EditText)findViewById(R.id.graduate);
        degreeprogramme = (EditText)findViewById(R.id.degree);
        branch = (EditText)findViewById(R.id.branch);



        mContext = EducationalDetailActivity.this;
        //Graduation List
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, graduation);
        AutoCompleteTextView acTextViewGra = (AutoCompleteTextView)findViewById(R.id.graduate);
        acTextViewGra.setThreshold(1);
        acTextViewGra.setAdapter(adapter);*/

        //Degree List
      /*  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, degree);
        AutoCompleteTextView acTextViewDeg = (AutoCompleteTextView)findViewById(R.id.degree);
        acTextViewDeg.setThreshold(1);
        acTextViewDeg.setAdapter(adapter);*/

        Button next = (Button)findViewById(R.id.edu_detail_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if(CheckInputs(Location, Batch, Graduate, Degree, Branch)) {
                    adddobhometown();
                    Intent i = new Intent(EducationalDetailActivity.this, WorkDetailActivity.class);
                    startActivity(i);
            //    }
            }
        });

        setupFirebaseAuth();
    }

    private void adddobhometown(){

        University = universityname.getText().toString();
        College = collegename.getText().toString();
        Location = location.getText().toString();
        Batch = batch.getText().toString();
        Graduate = graduate.getText().toString();
        Degree = degreeprogramme.getText().toString();
        Branch = branch.getText().toString();

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_university))
                .setValue(University);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_college_name))
                .setValue(College);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_college_location))
                .setValue(Location);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_batch_start_date))
                .setValue(Batch);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_graduate))
                .setValue(Graduate);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_degree_programme))
                .setValue(Degree);
        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_branch))
                .setValue(Branch);
    }

 /*   private boolean CheckInputs(String Location,String Batch,String Graduate,String Degree,String Branch){
        Log.d(TAG, "ifCheckInputs: Check inputs empty or not");
        if (Location.equals("") || Batch.equals("") || Graduate.equals("") || Degree.equals("") || Branch.equals("")){
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
