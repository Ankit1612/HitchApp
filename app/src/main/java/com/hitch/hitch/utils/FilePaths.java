package com.hitch.hitch.utils;

import android.os.Environment;

/**
 * Created by Ankit Shah on 29-Sep-17.
 */

public class FilePaths {

    //"/storage/emulated/0/"
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR+"/Pictures";
    public String CAMERA = ROOT_DIR+"/DCIM/Camera";
    public String DOWNLOADS = ROOT_DIR+"/Download";




    public String FIREBASE_IMAGE_STORAGE="photos/users/";

}
