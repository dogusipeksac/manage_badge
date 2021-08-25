package com.example.managebadges.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managebadges.Model.Data;
import com.example.managebadges.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Data> listItems;
    private Context context;

    public ListAdapter(List<Data> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data listItem=listItems.get(position);
        //listItems.get(position).setImgUrl("https://picsum.photos/1000?random="+position);
        holder.message.setText(listItem.getMessage());
        holder.related_name.setText(listItem.getRelated_person().getTitle());
        holder.created_date.setText(listItem.getCreted_date());
        holder.badget_title.setText(listItem.getBadgeData().getTitle());
        InputStream ims = null;
        try {
            ims = context.getAssets().open("image"+listItem.getBadgeData().getId()+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        holder.badges_image.setImageDrawable(d);
        holder.ratingBar.setRating(listItem.getPraiseRating());
        /*Glide.with(context)
                .load(listItems.get(position).getImgUrl())
                //.override(500,500)
                .into(holder.profileImage);*/
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView related_name;
        TextView created_date;
        TextView badget_title;
        TextView message;
        ImageView badges_image;
        RatingBar ratingBar;
        ImageView profileImage;
        public ViewHolder(View itemView) {
            super(itemView);
            related_name=itemView.findViewById(R.id.related_name);
            created_date=itemView.findViewById(R.id.crated_date);
            badget_title=itemView.findViewById(R.id.badget_title);
            message=itemView.findViewById(R.id.message);
            badges_image=itemView.findViewById(R.id.badges_image);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            profileImage=itemView.findViewById(R.id.profile_image);
        }
    }
}
