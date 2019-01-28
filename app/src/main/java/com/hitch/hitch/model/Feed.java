package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 13-Sep-17.
 */
public class Feed {

    public static final int QUESTION_BOX = 0;
    public static final int FEED_BOX  = 1;

    private String profileName, location, imgDescr, comments, timeStamp, postQuestion;
   public int feedImageUrl, profileImageUrl, type;

    public Feed(String profileName, String location, int feedImageUrl,int profileImageUrl,String postQuestion, String imgDescr, String commensts,String timeStamp, int type){
        this.profileName = profileName;
        this.location = location;
        this.feedImageUrl = feedImageUrl;
        this.profileImageUrl = profileImageUrl;
        this.imgDescr = imgDescr;
        this.comments = commensts;
        this.timeStamp = timeStamp;
        this.postQuestion = postQuestion;
        this.type = type;
    }

    public String getProfileName(){
        return profileName;
    }

    public void setProfileName(String profileName){
       this.profileName = profileName;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
       this.location = location;
    }

    public int getFeedImageUrl(){
        return feedImageUrl;
    }

    public void setFeedImageUrl(int feedImageUrl){
        this.feedImageUrl = feedImageUrl;
    }

    public int getProfileImageUrl(){
        return profileImageUrl;
    }

    public void setProfileImageUrl(int profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }

    public String getImgDescr(){
        return imgDescr;
    }

    public void setImgDescr(String imgDescr){
        this.imgDescr = imgDescr;
    }

    public String getComments(){
        return comments;
    }

    public void setComments(String comments){
        this.comments = comments;
    }


    public String getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    public String getPostQuestion(){
        return postQuestion;
    }

    public void setPostQuestion(String postQuestion){
        this.postQuestion = postQuestion;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
