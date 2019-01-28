package com.hitch.hitch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hitch.hitch.R;
import com.hitch.hitch.adapter.ProfileSearchRecyclerAdapter;
import com.hitch.hitch.model.ItemObject;

import java.util.ArrayList;
import java.util.List;

import static com.hitch.hitch.R.id.recyclerView;

public class SearchActivity extends AppCompatActivity {

    private GridLayoutManager lLayout;
    private EditText name;
    private LinearLayout linearLayout1, linearLayout2;
    private Button search;

    private static final String TAG = "SearchActivity";

    private final int ACTIVITY_NUM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        List<ItemObject> rowListIem = getAllItemList();
        lLayout = new GridLayoutManager(SearchActivity.this,2);

        name = (EditText)findViewById(R.id.namefilter);
        linearLayout1 = (LinearLayout)findViewById(R.id.yeargender);
        linearLayout2 = (LinearLayout)findViewById(R.id.branchcollege);
        search = (Button)findViewById(R.id.filterSearch);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        search.setVisibility(View.GONE);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.searchRV);
        recyclerView.setLayoutManager(lLayout);
        recyclerView.setNestedScrollingEnabled(false);

        ProfileSearchRecyclerAdapter rcAdapter = new ProfileSearchRecyclerAdapter(SearchActivity.this,rowListIem);
        recyclerView.setAdapter(rcAdapter);

     /*   ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton search = (ImageButton)findViewById(R.id.search);
        ImageButton notification = (ImageButton)findViewById(R.id.notification);
        ImageButton profile = (ImageButton)findViewById(R.id.profile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });*/
     }


    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Nishi Arya","Second Year","Computer Science",R.drawable.img30));
        allItems.add(new ItemObject("Ruby Mishra","Second Year","Mechinal", R.drawable.img31));
        allItems.add(new ItemObject("Anjali Rawat","Third Year","Electrical", R.drawable.img32));
        allItems.add(new ItemObject("Vipra Bhatt","Fourth Year","Industrial Production", R.drawable.img33));
        allItems.add(new ItemObject("Garima Singh","First Year","Computer Science", R.drawable.img34));
        allItems.add(new ItemObject("Apurva Bohra","Fourth Year","Information Technology", R.drawable.img35));
        allItems.add(new ItemObject("Joyti Gambhir","Third Year","Electronics & Communication", R.drawable.img36));
        allItems.add(new ItemObject("Anchal Bonal","Fourth Year","Mechinal", R.drawable.img37));
        allItems.add(new ItemObject("Reena Kandari","Fourth Year","Electrical", R.drawable.img20));
        allItems.add(new ItemObject("Nisha Bagadwal","Third Year","Information Technology", R.drawable.img21));
        allItems.add(new ItemObject("Priyanka Mehta","First Year","Computer Science", R.drawable.img22));
        allItems.add(new ItemObject("Kirti Thakur","Fourth Year","Mechinal",R.drawable.img23));
        allItems.add(new ItemObject("Sanju Bhat","Third Year","Industrial Production", R.drawable.img24));
        allItems.add(new ItemObject("Sweta Aggarwal","First Year","Computer Science", R.drawable.img25));
        allItems.add(new ItemObject("Prachi Sharma","Fourth Year","Information Technology", R.drawable.img26));
        allItems.add(new ItemObject("Kanika Aggarwal","Second Year","Civil", R.drawable.img27));

        return allItems;
    }


}
