package com.hitch.hitch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.Activity.ProfileDetailActivity;
import com.hitch.hitch.Activity.UserProfileActivity;
import com.hitch.hitch.R;
import com.hitch.hitch.model.Comment;
import com.hitch.hitch.model.Feed;
import com.hitch.hitch.model.Photo;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.model.Users;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit Shah on 13-Sep-17.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "FeedAdapter";

    private DatabaseReference mReference;
    private String currentUsername = "";
    private Context context;
    private List<Photo> feedList;

    private RelativeLayout commentBox;

    int total_types;

    public class MyFeedViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName, location, imgDescrip, timeStamp, userComments;
        public  ImageView feedImage;
        public CircleImageView profileImage;
        public ImageButton commentBtn;

        UserAccountSettings settings = new UserAccountSettings();
        Users user = new Users();
        StringBuilder users;
        Photo photo;


        public MyFeedViewHolder(View view){
            super(view);
            profileImage = (CircleImageView)view.findViewById(R.id.profile_image);
            profileName = (TextView)view.findViewById(R.id.profileNameFeed);
            location = (TextView)view.findViewById(R.id.location);
            feedImage = (ImageView)view.findViewById(R.id.feedImage);
            imgDescrip = (TextView)view.findViewById(R.id.imgDescription);
            timeStamp = (TextView)view.findViewById(R.id.timeSpan);
            userComments = (TextView)view.findViewById(R.id.userComments);
            commentBox = (RelativeLayout) view.findViewById(R.id.relLayout2);
            users = new StringBuilder();

            commentBtn = (ImageButton) view.findViewById(R.id.btnComments);
            
        }
    }

    public class MyQuestionViewHolder extends RecyclerView.ViewHolder{

        public TextView profileName, timeStamp, userComments, postQuestion;
        public CircleImageView profileImage;
        public ImageButton commentBtn;

        public MyQuestionViewHolder(View view){
            super(view);
            profileImage = (CircleImageView)view.findViewById(R.id.profile_image);
            profileName = (TextView)view.findViewById(R.id.profileNameFeed);
            postQuestion = (TextView)view.findViewById(R.id.postQuestion);
            timeStamp = (TextView)view.findViewById(R.id.timeSpan);
            userComments = (TextView)view.findViewById(R.id.userComments);

            commentBtn = (ImageButton) view.findViewById(R.id.btnComments);

        }
    }

    public FeedAdapter(Context context, List<Photo> feedList){
        this.context = context;
        this.feedList = feedList;
        total_types = feedList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
    switch (viewType) {
        case Feed.QUESTION_BOX:
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_feed, parent, false);
            MyQuestionViewHolder po = new MyQuestionViewHolder(view);
            return po;

        case Feed.FEED_BOX:
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            MyFeedViewHolder fe = new MyFeedViewHolder(view);
            return fe;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Photo feed = feedList.get(position);
        if(feed != null){
            switch (feed.getType()){
            case Feed.QUESTION_BOX:
                MyQuestionViewHolder qholder = (MyQuestionViewHolder)holder;
              /*  qholder.profileName.setText(feed.getProfileName());
                qholder.timeStamp.setText(feed.getTimeStamp());
                qholder.userComments.setText(feed.getComments());
                qholder.postQuestion.setText(feed.getPostQuestion());

                Glide.with(context)
                        .load(feed.getProfileImageUrl())
                        .centerCrop()
                        .fitCenter()
                        .priority(Priority.LOW)
                        .into(qholder.profileImage);*/
                break;

                case Feed.FEED_BOX:
                    final MyFeedViewHolder mholder = (MyFeedViewHolder)holder;
                  //  mholder.location.setText(feed.getLocation());

                    //set the caption
                    mholder.imgDescrip.setText(feedList.get(position).getCaption());

                    // set time stamp
                    mholder.timeStamp.setText(timeStamp(position));

                    // set the comments
                 /*   List<Comment> comments = feedList.get(position).getComments();
                    mholder.userComments.setText("View all " + comments.size() + " comments.");
                    mholder.userComments.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: loaading comment thread for " + getItem(position).getPhoto_id());
                            commentBox.setVisibility(View.VISIBLE);
                        }
                    });*/

                    Glide.with(context)
                            .load(feedList.get(position).getImage_path())
                            .override(1280, 720)
                            .fitCenter()
                            .priority(Priority.LOW)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mholder.feedImage);

                    // get the profile image and profile name
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference
                            .child(context.getString(R.string.dbname_user_account_settings))
                            .orderByChild(context.getString(R.string.field_profile_id))
                            .equalTo(feedList.get(position).getUser_id());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                               // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getFullname();
                                Log.d(TAG, "onDataChange: found user: " 
                                        + singleSnapshot.getValue(UserAccountSettings.class).getFullname());
                                
                                mholder.profileName.setText(singleSnapshot.getValue(UserAccountSettings.class).getFullname());
                                mholder.profileName.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d(TAG, "onClick: navigating to profile of useer : "
                                        + mholder.user.getUsername());

                                        Intent intent = new Intent(context, UserProfileActivity.class);
                                        intent.putExtra(context.getString(R.string.calling_activity),
                                                context.getString(R.string.home_activity));
                                  //      intent.putExtra(context.getString(R.string.intent_user), mholder.user);
                                        context.startActivity(intent);
                                    }
                                });

                                Glide.with(context)
                                        .load(singleSnapshot.getValue(UserAccountSettings.class).getProfile_image())
                                        .centerCrop()
                                        .fitCenter()
                                        .priority(Priority.LOW)
                                        .into(mholder.profileImage);
                                mholder.profileImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d(TAG, "onClick: navigating to profile of useer : "
                                                + mholder.user.getUsername());

                                        Intent intent = new Intent(context, UserProfileActivity.class);
                                        intent.putExtra(context.getString(R.string.calling_activity),
                                                context.getString(R.string.home_activity));
                                     //   intent.putExtra(context.getString(R.string.intent_user), mholder.user);
                                        context.startActivity(intent);
                                    }
                                });

                                mholder.settings = singleSnapshot.getValue(UserAccountSettings.class);
                                mholder.userComments.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //comment thread
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        if(feedList == null)
            return 0;
        return feedList.size();
    }


    @Override
    public int getItemViewType(int position) {

    if(feedList != null){
        Photo object = feedList.get(position);
        if(object != null){
            return object.getType();
            }
        }
        return 0;
    }

    /**
     * Return the string the number of days ago the post was made
     * @return
     */
    private String getTimestampDifference(int position){
        Log.d(TAG, "getTimestampDifference:  get the time difference ");

        String difference = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        Date today = cal.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = feedList.get(position).getData_created();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime())/1000/60/60/24)));

        } catch (ParseException e){
            Log.d(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }

    private String timeStamp(int position){
        String timestampDiff = getTimestampDifference(position);
        if(!timestampDiff.equals("0")){
            return timestampDiff + "Days ago";
        } else {
            return "Today";
        }
    }

    private void getCurrentProfilename(){
        Log.d(TAG, "getCurrentProfilename: retreving user account settings");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(context.getString(R.string.dbname_users))
                .orderByChild(context.getString(R.string.field_profile_id))
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getFullname();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
