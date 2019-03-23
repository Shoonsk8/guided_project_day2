package com.joshuahalvorson.journal;

import android.net.Uri;

import java.io.Serializable;

public class JournalEntry implements Serializable {
    public static final String TAG = "entry";

    private String date, entryText, imageUrl;
    private int dayRating, id;

    public JournalEntry(String date, String entryText, String imageUrl, int dayRating, int id) {
        this.date = date;
        this.entryText = entryText;
        this.imageUrl = imageUrl;
        this.dayRating = dayRating;
        this.id = id;
    }

    public JournalEntry(int id) {
        this.id = id;
        this.date = "";
        this.entryText = "";
        this.imageUrl = "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public Uri getImage() {
        if(!imageUrl.equals("")){
            return Uri.parse(imageUrl);
        }else{
            return null;
        }
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl.toString();
    }

    public int getDayRating() {
        return dayRating;
    }

    public void setDayRating(int dayRating) {
        this.dayRating = dayRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
