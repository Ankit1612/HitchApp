package com.hitch.hitch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hitch.hitch.Fragment.ImageGalleryFragment;
import com.hitch.hitch.Fragment.PersonalDetailFragment;
import com.hitch.hitch.Fragment.PostsFragment;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TabPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                PostsFragment tabPost = new PostsFragment();
                return tabPost;
            case 1:
                ImageGalleryFragment tabPhotos = new ImageGalleryFragment();
                return tabPhotos;
            case 2:
                PersonalDetailFragment tabDetail = new PersonalDetailFragment();
                return tabDetail;
            default:
                return null;
        }

      /*  if(position == 0) // if the position is 0 we are returning the First tab
        {
            PostsFragment tab1 = new PostsFragment();
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            PostsFragment tab2 = new PostsFragment();
            return tab2;
        }
*/

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
