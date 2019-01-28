package com.hitch.hitch.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.hitch.hitch.R;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ankit Shah on 29-Sep-17.
 */

public class GridImageAdapter extends ArrayAdapter<String>{

    private static final String TAG = "GridImageAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResources;
    private String mAppend;
    private ArrayList<String> imageURLs;

    public GridImageAdapter(Context mContext, int layoutResources, ArrayList<String> imageURLs) {
        super(mContext, layoutResources, imageURLs);
        this.mContext = mContext;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResources = layoutResources;
        this.imageURLs = imageURLs;
    }

    private static class ViewHolder{
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResources, parent,false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.gridImageView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imgURL = getItem(position);
        Log.d(TAG, "getView: imageUrl "+imgURL);
        File file = new File(imgURL);
        Uri imageUri = Uri.fromFile(file);

        Log.d(TAG, "getView: imageUri " + imageUri);

        Glide.with(mContext)
                .load(imageUri)
                .into(holder.image);


        return convertView;
    }
}
