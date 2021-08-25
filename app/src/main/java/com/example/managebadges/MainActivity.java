package com.example.managebadges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.managebadges.Adapter.BadgesAdapter;
import com.example.managebadges.Adapter.ListAdapter;
import com.example.managebadges.Model.BadgeData;
import com.example.managebadges.Model.Data;
import com.example.managebadges.Service.JsonService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private BadgesAdapter badgeAdapter;
    private JsonService service;
    private List<Data> list;
    private List<BadgeData> badgeDataList;
    private Button avarageText;
    private TextView adet;
    private RatingBar ratingBar;
    ViewPager2 imageContainer;
    TextView[] dots;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.list_data_recycler);
        imageContainer=findViewById(R.id.view_pager);
        layout=findViewById(R.id.dots_container);
        avarageText=findViewById(R.id.avarage_text);
        adet=findViewById(R.id.adet);
        ratingBar=findViewById(R.id.ratingBar3);
        service=JsonService.get(this);
        setAdapterBottomList();

        badgeDataList=JsonService.get(this).getJsonFromLocalyBadge();
        int size=badgeDataList.size();
        dots = new TextView[size];
        badgeAdapter=new BadgesAdapter(badgeDataList,this);
        imageContainer.setAdapter(badgeAdapter);
        setIndicators();
        imageContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedDots(position);
                super.onPageSelected(position);
            }
        });

        adet.setText(JsonService.sizeGeneral+" adet");
        float rating=JsonService.ratingAvarageGeneral;
        String strDouble = String.format("%.2f", rating);
        ratingBar.setRating(rating);
        avarageText.setText(""+strDouble);
    }
    public void setAdapterBottomList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=JsonService.get(this).getJsonFileFromLocally();
        adapter=new ListAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }
    private void selectedDots(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setTextColor(getResources().getColor(R.color.orange));
            } else {
                dots[i].setTextColor(getResources().getColor(R.color.grey));
            }
        }
    }

    private void setIndicators() {
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(18);
            layout.addView(dots[i]);
        }

    }

}