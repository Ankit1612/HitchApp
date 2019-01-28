package com.hitch.hitch.Activity;

import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hitch.hitch.Fragment.GalleryFragment;
import com.hitch.hitch.Fragment.PhotoFragment;
import com.hitch.hitch.R;
import com.hitch.hitch.adapter.SectionsPagerAdapter;
import com.hitch.hitch.utils.Permission;

public class ShareActivity extends AppCompatActivity {

    private static final String TAG = "ShareActivity";

    private ViewPager mViewPager;

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        if(checkPermissionsArray(Permission.PERMISSIONS)){
            setupViewPager();
        } else {
            verifyPermissions(Permission.PERMISSIONS);
        }
    }

    /**
     * Get current fragment number
     * 0: GalleryFragment
     * 1:PhotosFragment
     * @return
     */

    public int getCurrentTabNumber(){
        return mViewPager.getCurrentItem();
    }
    private void setupViewPager(){
        SectionsPagerAdapter adapter  = new SectionsPagerAdapter(getSupportFragmentManager());
           adapter.addFragment(new GalleryFragment());
           adapter.addFragment(new PhotoFragment());

        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photos));
    }

    public int getTask(){
        Log.d(TAG, "getTask: task " + getIntent().getFlags());

        return getIntent().getFlags();
    }

    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG, "checkPermissionsArray: checking permission arrays");

        for(int i=0; i<permissions.length; i++){
            String check = permissions[i];
            if (!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: Verifying permisions");

        ActivityCompat.requestPermissions(
                ShareActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permissions: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(ShareActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions:  \n Permission is not granted for: " + permission);
            return false;
        } else {
            Log.d(TAG, "checkPermissions:  \n Permission is was granted for: " + permission);

            return true;
        }
    }
}
