package com.hitch.hitch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hitch.hitch.R;
import com.hitch.hitch.model.ItemObject;

import java.util.List;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class ProfileSearchRecyclerAdapter extends RecyclerView.Adapter<ProfileSearchRecyclerAdapter.RecyclerViewHolder> {

    private List<ItemObject> itemList;
    private Context context;

    public ProfileSearchRecyclerAdapter (Context context, List<ItemObject> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_profile_cardview,null);
        RecyclerViewHolder rvh = new RecyclerViewHolder(layoutView);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.Name.setText(itemList.get(position).getName());
        holder.Batch.setText(itemList.get(position).getBatch());
        holder.Branch.setText(itemList.get(position).getBranch());

        Glide.with(context)
                .load(itemList.get(position).getProfile())
                .override(1280,720)
                .fitCenter()
                .into(holder.Profile);

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Name;
        public TextView Batch;
        public TextView Branch;
        public ImageView Profile;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            Name = (TextView)itemView.findViewById(R.id.tvName);
            Batch = (TextView)itemView.findViewById(R.id.tvBatch);
            Branch = (TextView)itemView.findViewById(R.id.tvBranch);
            Profile = (ImageView)itemView.findViewById(R.id.imPhoto);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),"Clicked on ", Toast.LENGTH_SHORT).show();
        }
    }
}
