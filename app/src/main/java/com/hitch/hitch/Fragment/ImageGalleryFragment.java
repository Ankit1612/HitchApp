package com.hitch.hitch.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hitch.hitch.Activity.FullScreenImageActivity;
import com.hitch.hitch.Activity.UserProfileActivity;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.GalleryImageAdapter;
import com.hitch.hitch.model.GalleryItems;
import com.hitch.hitch.model.ItemObject;
import com.hitch.hitch.model.Photo;
import com.hitch.hitch.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ImageGalleryFragment extends Fragment {

    private static final String TAG = "ImageGalleryFragment";

    GalleryImageAdapter mAdapter;

    @Bind(R.id.galleryRV)
    RecyclerView mRecyclerView;

    List<Photo> imageList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

    //    List<GalleryItems> rowlList = createImageList();

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_image_gallery, container, false);
        setUpView(root);
        return root;
    }

    void setUpView(ViewGroup root){
        ButterKnife.bind(this, root);
        setUPList();
    }

    void setUPList(){
        Log.d(TAG, "setUPList: entered");
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setHasFixedSize(true);

        DatabaseReference refrecence = FirebaseDatabase.getInstance().getReference();
        Query query = refrecence
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: adding photos: "+ singleSnapshot.getValue(Photo.class));
                    Photo photo = singleSnapshot.getValue(Photo.class);
                    Log.d(TAG, "onDataChange: photo "+photo);
                    imageList.add(photo);
                }

                mAdapter = new GalleryImageAdapter(getActivity(), imageList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });

    }

  /*  private List<GalleryItems> setUpImageGallery(){
        Log.d(TAG, "setUpImageGallery: setting up imagegallery");

        final ArrayList<GalleryItems> photos = new ArrayList<>();

        DatabaseReference refrecence = FirebaseDatabase.getInstance().getReference();
        Query query = refrecence
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: adding photos: "+ singleSnapshot.getValue(GalleryItems.class));
                    photos.add(singleSnapshot.getValue(GalleryItems.class));
                }

                ArrayList<String> imgUrls = new ArrayList<>();
                for(int i=0;i<photos.size();i++){
                    imgUrls.add(photos.get(i).getImage_path());
                    Log.d(TAG, "onDataChange: image urls: " +photos.get(i).getImage_path());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });

        return photos;
    }
    private List<GalleryItems> createImageList(){
        ArrayList<GalleryItems> imageList = new ArrayList<>();

        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img3));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img13));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img23));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img33));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img31));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img32));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img1));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img2));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img3));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img4));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img5));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img6));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img7));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img8));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img9));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img10));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img11));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img12));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img14));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img15));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img16));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img17));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img18));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img19));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img20));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img21));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img22));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img24));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img25));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img26));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img27));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img28));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img29));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img30));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img32));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img33));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img34));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img35));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img36));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img37));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img8));
        imageList.add(new GalleryItems("Nishi Arya",R.drawable.img31));

        return imageList;
    }*/
}
