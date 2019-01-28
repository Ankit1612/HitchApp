package com.hitch.hitch.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hitch.hitch.Activity.HomeActivity;
import com.hitch.hitch.R;
import com.hitch.hitch.model.Photo;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.model.UserSettings;
import com.hitch.hitch.model.Users;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Ankit Shah on 24-Sep-17.
 */

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String userID;

    private Context mContext;

    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void uploadNewPhoto(String photoType, final String caption,final int count,final String imgUrl, Bitmap bm){
        Log.d(TAG, "uploadNewPhoto: attempting to upload new photo");

        final FilePaths filePaths = new FilePaths();
        //new photo
        if(photoType.equals(mContext.getString(R.string.new_photo))){
            Log.d(TAG, "uploadNewPhoto: uploading new photos");

            StorageReference mStorageRef = storageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + userID + "/photo" + (count+1));

            //convert our imageUrl to bitmap
            if(bm == null) {
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = mStorageRef.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUri = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "upload photo sucessful.", Toast.LENGTH_SHORT).show();

                    //add new photos to photos  node and user_photos node
                    addPhotoToDatabase(caption, firebaseUri.toString());

                    //navigate to main feed so user can see their photos
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "photo upload progress: "+ String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "%done.");
                }
            });
        }
        //upload new profile photo
        else if(photoType.equals(mContext.getString(R.string.profile_photo))){
            Log.d(TAG, "uploadNewPhoto: uploading new profile photos");

            StorageReference mStorageRef = storageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + userID + "/profile_photo");

            //convert our imageUrl to bitmap
            if(bm == null) {
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = mStorageRef.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUri = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "upload photo sucessful.", Toast.LENGTH_SHORT).show();

                    //insert into 'user_account_setting' node
                    setProfilePhoto(firebaseUri.toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "photo upload progress: "+ String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "%done.");
                }
            });
        }
    }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile photos: "+url);

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for (DataSnapshot ds:dataSnapshot
        .child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    /**
     *  Register new email and password to Firebase
     */
    public void registerNewEmail(final String email, String password,final String fullname){
        Log.d(TAG, "registerNewEmail: register new email and password");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else if(task.isSuccessful()){

                            // send verification email
                            sendVerificationEmail();

                            Toast.makeText(mContext, R.string.auth_success,
                                    Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();

                            databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                                    .child(userID)
                                    .child(mContext.getString(R.string.field_fullname))
                                    .setValue(fullname);
                        }
                    }
                });
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){

                           }else {
                               Toast.makeText(mContext,"couldn't send veification email",Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
        }
    }

    private String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }

    private void addPhotoToDatabase(String caption, String url){
        Log.d(TAG, "addPhotoToDatabase: Adding photos to database");

        String tags= StringManuplation.getTags(caption);
        String newPhotoKey = databaseReference.child(mContext.getString(R.string.dbname_photos)).push().getKey();
        Photo photo = new Photo();
        photo.setCaption(caption);
        photo.setData_created(getTimeStamp());
        photo.setImage_path(url);
        photo.setTags(tags);
        photo.setUser_id(userID);
        photo.setPhoto_id(newPhotoKey);

        //insert into database
        databaseReference.child(mContext.getString(R.string.dbname_user_photos)).child(userID).child(newPhotoKey).setValue(photo);
        databaseReference.child(mContext.getString(R.string.dbname_photos)).child(newPhotoKey).setValue(photo);

    }

    /**
     * add new user in database with email and username
     * @param email
     * @param username
     */
    public void addNewUser(String email, String username, String fullname){
        Users user = new Users(userID, email, username);
        UserAccountSettings settings = new UserAccountSettings(
                "Nothing to Say.",
                "No awards.",
                "YYYY",
                "No branch",
                "Unknown",
                "Don't know.",
                "No image",
                "No degree",
                "16 December",
                fullname,
                "Not graduate",
                "No hobbies",
                "No hometown",
                "No image",
                "No projects",
                "Not join yet",
                "",
                username,
                email,
                "Update your status...",
                "1234567890abc",
                userID);

        databaseReference.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        databaseReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);
    }

    /**
     * retrive user data from the database
     * @param dataSnapshot
     * @return
     */

    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retriving user account settings from the firebase database.");

        UserAccountSettings settings = new UserAccountSettings();
        Users user = new Users();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            //retriving data from user_account_setting node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))){
                Log.d(TAG, "getUserAccountSettings: datasnapshot" + ds);

                try {
                    settings.setUsername(
                            ds.child(userID)
                            .getValue(UserAccountSettings.class)
                            .getUsername()
                    );
                    settings.setAbout_me(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getAbout_me()
                    );
                    settings.setAwards(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getAwards()
                    );
                    settings.setBatch_start_date(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getBatch_start_date()
                    );
                    settings.setBranch(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getBranch()
                    );
                    settings.setCollege_location(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getCollege_location()
                    );
                    settings.setCollege_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getCollege_name()
                    );
                    settings.setCover_page_image(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getCover_page_image()
                    );
                    settings.setDegree_programme(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDegree_programme()
                    );
                    settings.setDob(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDob()
                    );
                    settings.setFullname(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFullname()
                    );
                    settings.setGraduate(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getGraduate()
                    );
                    settings.setHobbies(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getHobbies()
                    );
                    settings.setHometown(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getHometown()
                    );
                    settings.setProfile_image(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_image()
                    );
                    settings.setProject_publication(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProject_publication()
                    );
                    settings.setSociety_ngo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getSociety_ngo()
                    );
                    settings.setUniversity(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUniversity()
                    );
                    settings.setWebsite_blogs(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getWebsite_blogs()
                    );
                    settings.setStatus(
                            ds.child(userID)
                            .getValue(UserAccountSettings.class)
                            .getStatus()
                    );
                    settings.setUser_id(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUser_id()
                    );
                } catch (NullPointerException e){
                    Log.d(TAG, "getUserAccountSettings: nullpointerexception" + e.getMessage());
                }
            }

            //retriving data from user node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                Log.d(TAG, "getUserAccountSettings: datasnapshot" + ds);

                user.setUsername(
                        ds.child(userID)
                                .getValue(Users.class)
                                .getUsername()
                );

                user.setEmail_id(
                        ds.child(userID)
                                .getValue(Users.class)
                                .getEmail_id()
                );

                user.setUser_id(
                        ds.child(userID)
                                .getValue(Users.class)
                                .getUser_id()
                );
            }

        }
        return new UserSettings(user, settings);
    }

}
