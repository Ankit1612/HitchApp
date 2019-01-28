package com.hitch.hitch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.hitch.hitch.R;
import com.hitch.hitch.model.Feed;
import com.hitch.hitch.model.Photo;
import com.hitch.hitch.model.UserAccountSettings;
import com.hitch.hitch.utils.FirebaseMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommonRecyclerAdapter";

    private List<Photo> feedData;
    private List<UserAccountSettings> accountData;
    private Context context;

    public CommonRecyclerAdapter(Context context, List<Photo> feedData, List<UserAccountSettings> accountData) {
        this.feedData = feedData;
        this.context = context;
        this.accountData = accountData;
    }

    private class MyFeedViewHolder extends RecyclerView.ViewHolder{
        private TextView profileName, location, imgDescrip, timeStamp, showComments;
        private ImageView feedImage;
        private CircleImageView profileImage;
        private ImageButton likeBtn, commentBtn;
        private RelativeLayout commentBox, editComment;

        private MyFeedViewHolder(final View view){
            super(view);
            profileImage = (CircleImageView)view.findViewById(R.id.profile_image);
            profileName = (TextView)view.findViewById(R.id.profileNameFeed);
            location = (TextView)view.findViewById(R.id.location);
            feedImage = (ImageView)view.findViewById(R.id.feedImage);
            imgDescrip = (TextView)view.findViewById(R.id.imgDescription);
            timeStamp = (TextView)view.findViewById(R.id.timeSpan);
            showComments = (TextView)view.findViewById(R.id.userComments);
            commentBtn = (ImageButton)view.findViewById(R.id.btnComments);
            likeBtn = (ImageButton)view.findViewById(R.id.btnLike);
            commentBox = (RelativeLayout)view.findViewById(R.id.relLayout2);
            editComment = (RelativeLayout)view.findViewById(R.id.relLayout4);

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: comment button click");
                    commentBox.setVisibility(View.VISIBLE);
                }
            });

            showComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: show all comments clicked");
                    commentBox.setVisibility(View.VISIBLE);
                    editComment.setVisibility(View.GONE);
                }
            });
        }

    }

 /*   public class MyQuestionViewHolder extends RecyclerView.ViewHolder{

        public TextView profileName, location, timeStamp, userComments, postQuestion;
        public CircleImageView profileImage;

        public MyQuestionViewHolder(View view){
            super(view);
            profileImage = (CircleImageView)view.findViewById(R.id.profile_image);
            profileName = (TextView)view.findViewById(R.id.profileNameFeed);
            location = (TextView)view.findViewById(R.id.location);
            postQuestion = (TextView)view.findViewById(R.id.postQuestion);
            timeStamp = (TextView)view.findViewById(R.id.timeSpan);
            userComments = (TextView)view.findViewById(R.id.userComments);
        }
    }
*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       /* View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tab_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;*/

        View view;
        switch (viewType) {
         /*   case Photo.QUESTION_BOX:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_feed, parent, false);
                MyQuestionViewHolder po = new MyQuestionViewHolder(view);
                return po;*/

            case Photo.FEED_BOX:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
                MyFeedViewHolder fe = new MyFeedViewHolder(view);
                return fe;
        }
        return null;


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        MyFeedViewHolder mholder = (MyFeedViewHolder) viewHolder;


        Glide.with(context)
                .load(feedData.get(position).getImage_path())
                .override(1280, 720)
                .fitCenter()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mholder.feedImage);

        mholder.timeStamp.setText(timeStamp(position));
       Log.d(TAG, "onBindViewHolder: profile name: "+accountData.get(position).getUsername());
        mholder.profileName.setText(accountData.get(position).getUsername());
        Log.d(TAG, "onBindViewHolder: profile name: "+accountData.get(position).getProfile_image());

     /*   Glide.with(context)
                .load(accountData.get(position).getProfile_image())
                .centerCrop()
                .fitCenter()
                .priority(Priority.LOW)
                .into(mholder.profileImage);*/


     /*   Glide.with(context)
                .load(feedData.get(position).get())
                .centerCrop()
                .fitCenter()
                .priority(Priority.LOW)
                .into(mholder.profileImage);

        //   viewHolder.text.setText(itemsData.get(position));
     /*   Photo feed = feedData.get(position);
        if(feed != null) {
            switch (feed.getType()) {
              /*  case Feed.QUESTION_BOX:
                    MyQuestionViewHolder qholder = (MyQuestionViewHolder) viewHolder;
                    qholder.profileName.setText(feed.getProfileName());
                    qholder.timeStamp.setText(feed.getTimeStamp());
                    qholder.userComments.setText(feed.getComments());
                    qholder.postQuestion.setText(feed.getPostQuestion());

                    Glide.with(context)
                            .load(feed.getProfileImageUrl())
                            .centerCrop()
                            .fitCenter()
                            .priority(Priority.LOW)
                            .into(qholder.profileImage);
                    break;*/

              /*  case Feed.FEED_BOX:
                    MyFeedViewHolder mholder = (MyFeedViewHolder) viewHolder;
                //    mholder.profileName.setText(feed.getProfileName());
                //    mholder.location.setText(feed.getLocation());
                    mholder.imgDescrip.setText(feed.getCaption());
                    mholder.timeStamp.setText(feed.getData_created());
                //    mholder.userComments.setText(feed.getComments());

                    Glide.with(context)
                            .load(feed.getImage_path())
                            .override(1280, 720)
                            .fitCenter()
                            .priority(Priority.LOW)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mholder.feedImage);

               /*     Glide.with(context)
                            .load(feed.getProfileImageUrl())
                            .centerCrop()
                            .fitCenter()
                            .priority(Priority.LOW)
                            .into(mholder.profileImage);
                    break;
            }
        }*/
    }

    private String timeStamp(int position){
        String timestampDiff = getTimestampDifference(position);
        if(!timestampDiff.equals("0")){
            return timestampDiff + "Days ago";
        } else {
            return "Today";
        }
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
        final String photoTimestamp = feedData.get(position).getData_created();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime())/1000/60/60/24)));

        } catch (ParseException e){
            Log.d(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }


    @Override
    public int getItemCount() {
        if(feedData != null)
            return feedData.size();

        if(accountData != null)
            return accountData.size();

        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if(feedData != null){
            Photo object = feedData.get(position);
            if(object != null){
                return object.getType();
            }
        }
        return 0;
    }

}
