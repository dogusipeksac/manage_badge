package com.example.managebadges.Model;

public class BadgeData {

    private int badgeId;
    private String badgeTitle;
    private int howmany;
    private float avarage;

    public BadgeData(int id, String title) {
        this.badgeId = id;
        this.badgeTitle = title;
    }

    public BadgeData() {
    }

    public int getHowmany() {
        return howmany;
    }

    public void setHowmany(int howmany) {
        this.howmany = howmany;
    }

    public float getAvarage() {
        return avarage;
    }

    public void setAvarage(float avarage) {
        this.avarage = avarage;
    }

    public int getId() {
        return badgeId;
    }

    public void setId(int id) {
        this.badgeId = id;
    }

    public String getTitle() {
        return badgeTitle;
    }

    public void setTitle(String title) {
        this.badgeTitle = title;
    }
}
