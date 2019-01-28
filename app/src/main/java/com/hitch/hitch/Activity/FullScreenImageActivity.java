package com.hitch.hitch.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hitch.hitch.R;
import com.hitch.hitch.model.GalleryItems;
import com.hitch.hitch.model.Photo;

public class FullScreenImageActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_PHOTO = "FullScreenImageActivity.IMAGE_PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        mImageView = (ImageView)findViewById(R.id.fullSizeImage);
        Photo galleryItems = getIntent().getParcelableExtra(EXTRA_IMAGE_PHOTO);

        Glide.with(this)
                .load(galleryItems.getImage_path())
                .asBitmap()
                .error(R.drawable.ic_red_heart)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
    }
}
