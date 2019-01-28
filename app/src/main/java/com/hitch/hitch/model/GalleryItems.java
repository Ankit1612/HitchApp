package com.hitch.hitch.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankit Shah on 18-Sep-17.
 */
public class GalleryItems implements Parcelable{

    private String caption;
    private String image_path;

    protected GalleryItems(Parcel in){
        image_path = in.readString();
        caption = in.readString();
    }

    public static final Creator<GalleryItems> CREATOR = new Creator<GalleryItems>() {
        @Override
        public GalleryItems createFromParcel(Parcel source) {
            return new GalleryItems(source);
        }

        @Override
        public GalleryItems[] newArray(int size) {
            return new GalleryItems[size];
        }
    };

    public GalleryItems(String caption, String image_path){
        this.image_path = image_path;
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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
