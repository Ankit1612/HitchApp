package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class NotificationItems {

    private String Name, timeSpan, notification;
    private int profileImage, image;

    public NotificationItems(String Name, String timeSpan, String notification, int profileImage, int image){
        this.Name = Name;
        this.timeSpan = timeSpan;
        this.notification = notification;
        this.profileImage = profileImage;
        this.image = image;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getTimeSpan(){
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan){
        this.timeSpan = timeSpan;
    }

    public String getNotification(){
        return notification;
    }

    public void setNotification(String notification){
        this.notification = notification;
    }

    public int getProfileImage(){
        return profileImage;
    }

    public void setProfileImage(int profileImage){
        this.profileImage = profileImage;
    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }
}
