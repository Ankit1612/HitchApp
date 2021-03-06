package com.hitch.hitch.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankit Shah on 29-Sep-17.
 */

public class Photo implements Parcelable{

    private String caption;
    private String data_created;
    private String image_path;
    private String photo_id;
    private String user_id;
    private String tags;
    private int type;

    public static final int FEED_BOX  = 0;


    public Photo() {
    }

    protected Photo(Parcel in){
        image_path = in.readString();
        caption = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>(){
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public Photo(String caption, String data_created, String image_path, String photo_id, String user_id, String tags) {
        this.caption = caption;
        this.data_created = data_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.tags = tags;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getData_created() {
        return data_created;
    }

    public void setData_created(String data_created) {
        this.data_created = data_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Photo{" +
                "caption='" + caption + '\'' +
                ", data_created='" + data_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_path);
        dest.writeString(caption);
    }
}
