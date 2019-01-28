package com.hitch.hitch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.hitch.hitch.R;
import com.hitch.hitch.adapter.NotificationRecyclerAdapter;
import com.hitch.hitch.model.NotificationItems;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private LinearLayoutManager llm;

    private static final String TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        List<NotificationItems> notifyList = getAllItemList();
        llm = new LinearLayoutManager(NotificationActivity.this);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.notificationRV);
        recyclerView.setLayoutManager(llm);

        NotificationRecyclerAdapter adapter = new NotificationRecyclerAdapter(NotificationActivity.this, notifyList);
        recyclerView.setAdapter(adapter);

        ImageButton home = (ImageButton)findViewById(R.id.home);
        ImageButton search = (ImageButton)findViewById(R.id.search);
        ImageButton notification = (ImageButton)findViewById(R.id.notification);
        ImageButton profile = (ImageButton)findViewById(R.id.profile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotificationActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotificationActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotificationActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotificationActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });
    }


    private List<NotificationItems> getAllItemList(){

        List<NotificationItems> allItems = new ArrayList<NotificationItems>();
        allItems.add(new NotificationItems("Nishi Arya","2 min","Commented on your photo.",R.drawable.img30,R.drawable.img10));
        allItems.add(new NotificationItems("Ruby Mishra","1 day","Liked your photo.", R.drawable.img31,R.drawable.img11));
        allItems.add(new NotificationItems("Anjali Rawat","4 sec","Messaged you.", R.drawable.img32,R.drawable.ic_message));
        allItems.add(new NotificationItems("Vipra Bhatt","45 min","Liked your photo.", R.drawable.img33,R.drawable.img12));
        allItems.add(new NotificationItems("Garima Singh","1 week","Commented on your photo.", R.drawable.img34,R.drawable.img13));
        allItems.add(new NotificationItems("Apurva Bohra","1 month","Commented on your photo", R.drawable.img35,R.drawable.img14));
        allItems.add(new NotificationItems("Joyti Gambhir","25 sec","Commented on your photo", R.drawable.img36,R.drawable.img15));
        allItems.add(new NotificationItems("Anchal Bonal","10 min","Messaged you", R.drawable.img37,R.drawable.ic_message));
        allItems.add(new NotificationItems("Reena Kandari","2 hrs","Messaged you", R.drawable.img20,R.drawable.ic_message));
        allItems.add(new NotificationItems("Nisha Bagadwal","1 day","Messaged you", R.drawable.img21,R.drawable.ic_message));
        allItems.add(new NotificationItems("Priyanka Mehta","2 days","Liked your photo.", R.drawable.img22,R.drawable.img16));
        allItems.add(new NotificationItems("Kirti Thakur","1 sec","Commented on your photo.",R.drawable.img23,R.drawable.img17));
        allItems.add(new NotificationItems("Sanju Bhat","5 min","Message you", R.drawable.img24,R.drawable.ic_message));
        allItems.add(new NotificationItems("Sweta Aggarwal","5 hrs","Liked your photo.", R.drawable.img25,R.drawable.img18));
        allItems.add(new NotificationItems("Prachi Sharma","1 min","Liked your photo.", R.drawable.img26,R.drawable.img19));
        allItems.add(new NotificationItems("Kanika Aggarwal","2 sec","Commented on your photo.", R.drawable.img27,R.drawable.img20));

        return allItems;
    }
}
