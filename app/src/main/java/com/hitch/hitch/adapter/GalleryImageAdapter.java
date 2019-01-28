package com.hitch.hitch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hitch.hitch.Activity.FullScreenImageActivity;
import com.hitch.hitch.Fragment.ImageGalleryFragment;
import com.hitch.hitch.R;
import com.hitch.hitch.model.GalleryItems;
import com.hitch.hitch.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit Shah on 18-Sep-17.
 */
public class GalleryImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "GalleryImageAdapter";
    Context context;
    List<Photo> imageList = new ArrayList<>();

    public GalleryImageAdapter(Context context,List<Photo> imageList){
        this.context = context;
        this.imageList = imageList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_gallery,parent,false);
        RecyclerView.ViewHolder viewHolder = new MyItemHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: imgurl: "+imageList.get(position).getImage_path());
        Glide.with(context)
                .load(imageList.get(position).getImage_path())
                .thumbnail(0.5f)
                .override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((MyItemHolder) holder).mImg);


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImg;

        public MyItemHolder(View itemView){
            super(itemView);

            mImg = (ImageView)itemView.findViewById(R.id.item_img);

            itemView.setOnClickListener(this);
        }


    @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Photo galleryItems = imageList.get(position);
                Intent intent = new Intent(context, FullScreenImageActivity.class);
                intent.putExtra(FullScreenImageActivity.EXTRA_IMAGE_PHOTO, galleryItems);
                context.startActivity(intent);
            }
        }
    }
}
