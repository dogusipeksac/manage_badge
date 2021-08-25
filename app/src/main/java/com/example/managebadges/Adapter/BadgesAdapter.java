package com.example.managebadges.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.managebadges.Model.BadgeData;
import com.example.managebadges.Model.Data;
import com.example.managebadges.R;
import com.example.managebadges.Service.JsonService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.ViewHolder>{
    private List<BadgeData> listItems;
    private Context context;
    private JsonService service;

    public BadgesAdapter(List<BadgeData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        service=JsonService.get(context);
    }

    @Override
    public BadgesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.badge_item,parent,false);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new BadgesAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(BadgesAdapter.ViewHolder holder, int position) {
        BadgeData listItem=listItems.get(position);
        holder.title.setText(listItem.getTitle());
        InputStream ims = null;
        try {
            ims = context.getAssets().open("image"+listItem.getId()+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        holder.imageView.setImageDrawable(d);
        holder.howManydata.setText(service.calculateSize(listItem.getId())+" adet");
        holder.ratingBar.setRating(service.calculateAvarage(listItem.getId()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        RatingBar ratingBar;
        TextView howManydata;
        public ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.badgesImage);
            title=itemView.findViewById(R.id.badgeTitle);
            ratingBar=itemView.findViewById(R.id.ratingBarInBadge);
            howManydata=itemView.findViewById(R.id.howmanydata);
        }

    }




}
