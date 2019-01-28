package com.hitch.hitch.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hitch.hitch.Activity.EditProfileActivity;
import com.hitch.hitch.Activity.NextShareActivity;
import com.hitch.hitch.Activity.ShareActivity;
import com.hitch.hitch.R;
import com.hitch.hitch.utils.Permission;

/**
 * Created by Ankit Shah on 28-Sep-17.
 */

public class PhotoFragment extends Fragment{
    private static final String TAG = "PhotoFragment";

    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int GALLERY_FRAGMENT_NUM = 2;
    private static final int CAMERA_REQUEST_CODE = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos,container, false);

        Button btnLaunchCamera = (Button)view.findViewById(R.id.cameraBtn);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening camera");

                if(((ShareActivity)getActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUM){
                    if(((ShareActivity)getActivity()).checkPermissions(Permission.CAMERA_PERMISSION[0])){
                        Log.d(TAG, "onClick: Starting Camera");

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(getActivity(), ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == CAMERA_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: done taking a photo");
            Log.d(TAG, "onActivityResult: navigating to final screen");

            //navigate to the final share screen
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            if(isRootTask()) {
                try{
                    Log.d(TAG, "onActivityResult: recevied new bitmap from camera: "+bitmap);
                    Intent intent = new Intent(getActivity(), NextShareActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                    startActivity(intent);
                } catch (NullPointerException e){
                    Log.d(TAG, "onActivityResult: NullPointerException "+e.getMessage());
                }
            } else {
                try{
                    Log.d(TAG, "onActivityResult: recevied new bitmap from camera: "+bitmap);
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                    intent.putExtra(getString(R.string.return_to_fragment), getString(R.string.edit_profile_fragment));
                    startActivity(intent);
                    getActivity().finish();
                } catch (NullPointerException e){
                    Log.d(TAG, "onActivityResult: NullPointerException "+e.getMessage());
                }

            }
            
        }
    }

    private boolean isRootTask(){
        if (((ShareActivity)getActivity()).getTask() == 0)
            return true;
        else
            return false;
    }
}
