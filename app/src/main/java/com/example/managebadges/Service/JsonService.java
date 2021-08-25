package com.example.managebadges.Service;

import android.content.Context;


import com.example.managebadges.Model.Author;
import com.example.managebadges.Model.BadgeData;
import com.example.managebadges.Model.Data;
import com.example.managebadges.Model.RelatedPerson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonService {

    public  static Context context;
    List<Data> list;
    List<BadgeData> badgeList;
    private static JsonService jsonService;
    public static float ratingAvarageGeneral;
    public static int sizeGeneral;


    public static JsonService get(Context context){
        if (jsonService==null){
            jsonService=new JsonService(context);
        }
        return jsonService;
    }

    private JsonService(Context context) {
        this.context = context;
        list=new ArrayList<>();
        badgeList=new ArrayList<>();
    }

    public static String loadJSONFromAsset(String path) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public List<Data> getJsonFileFromLocally() {

        try {
            JSONObject obj =(JSONObject) new JSONObject(loadJSONFromAsset("list-data.json"));
            JSONArray jsonArrayList= obj.getJSONArray("Row");
            int ratingSum=0;
            ratingAvarageGeneral=0;
            for(int i=0;i<jsonArrayList.length();i++){
                Data item=new Data(new BadgeData(),new Author(),new RelatedPerson());

                ///////////////////////////////////////////get data string from json
                JSONObject object=jsonArrayList.getJSONObject(i);


                String relatedPersonTitle="";

                JSONArray objectArrayRelated=object.getJSONArray("RelatedPerson");
                for(int y=0;y<objectArrayRelated.length();y++){
                    JSONObject objectRelated= objectArrayRelated.getJSONObject(y);
                    /////////////////////////////getTitle
                    relatedPersonTitle=objectRelated.getString("title");
                    ///////////////////////////////////////
                }
                String badgelookupValue="";
                int idForBadge=0;
                JSONArray objectArrayBadge=object.getJSONArray("Badge");
                for(int y=0;y<objectArrayBadge.length();y++){
                    JSONObject objectBadge= objectArrayBadge.getJSONObject(y);
                    ///////////////////////////////////////
                    badgelookupValue=objectBadge.getString("lookupValue");
                    idForBadge=objectBadge.getInt("lookupId");
                }
                //şu an kullanılmıyor
                JSONArray objectArrayAuthor=object.getJSONArray("Author");


                /////////////////////////////getdate
                String cratedDate="";
                cratedDate=object.getString("Created");
                ///////////////////////////////////////

                /////////////////////////////getmessage
                String message="";
                message=object.getString("Message");
                ///////////////////////////////////////



                int ratingScrore=0;
                ratingScrore=object.getInt("PraiseRating");


                ratingSum=ratingSum+ratingScrore;


                /////////////////////////////////////Nesneleri listeye ekledik
                item.getRelated_person().setTitle(relatedPersonTitle);
                item.setCreted_date(cratedDate);
                item.setMessage(message);
                item.getBadgeData().setTitle(badgelookupValue);
                item.setPraiseRating(ratingScrore);
                item.getBadgeData().setId(idForBadge);


                list.add(item);


            }
            ratingAvarage(ratingSum,jsonArrayList.length());
            sizeGeneral=list.size();


            // for
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BadgeData> getJsonFromLocalyBadge(){


        try {
            JSONObject obj =(JSONObject) new JSONObject(loadJSONFromAsset("badge-data.json"));
            JSONArray jsonArrayList= obj.getJSONArray("value");
            for (int i=0;i<jsonArrayList.length();i++){
                JSONObject object=jsonArrayList.getJSONObject(i);
                String title="";
                title=object.getString("Title");
                int id=0;
                id=object.getInt("Id");
                BadgeData item=new BadgeData();
                item.setId(id);
                item.setTitle(title);
                badgeList.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
     return badgeList;
    }

    public int calculateSize(int id){
        int size=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBadgeData().getId()==id){
                size++;
            }
        }
        return size;
    }
    public float calculateAvarage(int id){
        float avarage=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBadgeData().getId()==id){
                avarage=avarage+list.get(i).getPraiseRating();
            }
        }
        return avarage/calculateSize(id);
    }

    public float ratingAvarage(int ratingSum,int size){

        ratingAvarageGeneral=(float)ratingSum/(float) size;

         return ratingAvarageGeneral;
    }

}
