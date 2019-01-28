package com.hitch.hitch.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitch.hitch.Activity.AccountSettingActivity;
import com.hitch.hitch.Activity.EditProfileActivity;
import com.hitch.hitch.Activity.NextShareActivity;
import com.hitch.hitch.Activity.ShareActivity;
import com.hitch.hitch.Activity.UserProfileActivity;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.GridImageAdapter;
import com.hitch.hitch.utils.FilePaths;
import com.hitch.hitch.utils.FileSearch;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ankit Shah on 28-Sep-17.
 */

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryFragment";

    private static final int NUM_GRID_COLUMNS = 3;

    private GridView gridView;
    private ImageView galleryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;

    private ArrayList<String> directories;
    private String mSelectedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery,container, false);

        galleryImage = (ImageView)view.findViewById(R.id.galleryImageView);
        gridView = (GridView)view.findViewById(R.id.gridView);
        directorySpinner = (Spinner)view.findViewById(R.id.snipperDirectory);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
        Log.d(TAG, "onCreateView: Started");

        ImageView shareClose = (ImageView)view.findViewById(R.id.ivCloseShare);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing gallery fragment");
                getActivity().finish();
            }
        });

        TextView nextScreen = (TextView)view.findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigate to final share screen");

                if(isRootTask()) {
                    Intent intent = new Intent(getActivity(), NextShareActivity.class);
                    intent.putExtra(getString(R.string.selected_image), mSelectedImage);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    intent.putExtra(getString(R.string.selected_image), mSelectedImage);
                    intent.putExtra(getString(R.string.return_to_fragment), getString(R.string.edit_profile_fragment));
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        init();
        return view;
    }

    private boolean isRootTask(){
        if (((ShareActivity)getActivity()).getTask() == 0)
            return true;
        else
            return false;
    }
    private void init(){

        FilePaths filePaths = new FilePaths();

        if(FileSearch.getDirectoryPaths(filePaths.PICTURES) != null){
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
            Log.d(TAG, "init: directories: "+directories);
        }
       /* ArrayList<String> directoryNames = new ArrayList<>();
        for(int i=0; i<directories.size();i++){
           int index = directories.get(i).lastIndexOf("/")+1;
            String string = directories.get(i).substring(index);
            directoryNames.add(string);
            Log.d(TAG, "init: directory names: "+string);
        }*/
        directories.add(filePaths.CAMERA);
        directories.add(filePaths.DOWNLOADS);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected: "+directories.get(position));
                setupGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupGridView(String selectedDirectory){
        Log.d(TAG, "setupGridView: directory choosen+ " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);
        Log.d(TAG, "setupGridView: image list " + imgURLs);

        // set grid column width
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        //use the grid adapter to adapter the images to gridview
        GridImageAdapter adapter =  new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, imgURLs);
        gridView.setAdapter(adapter);

        //set the first image to be displayed when activity fragment view is inflated
        try{
            setImage(imgURLs.get(0),galleryImage);
            mSelectedImage = imgURLs.get(0);
        } catch (ArrayIndexOutOfBoundsException e){
            Log.d(TAG, "setupGridView: arrayindex out of bound " + e.getMessage());
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected an image "+imgURLs.get(0));
                setImage(imgURLs.get(position),galleryImage);
                mSelectedImage = imgURLs.get(position);

            }
        });
    }

    private void setImage(String imgURL, ImageView image){
        Log.d(TAG, "setImage: setting the images " + imgURL);

        File file = new File(imgURL);
        Uri imageUri = Uri.fromFile(file);

        Log.d(TAG, "setImage: imageUri is "+ imageUri);
        Glide.with(getActivity())
                .load(imageUri)
                .into(image);
    }
}
