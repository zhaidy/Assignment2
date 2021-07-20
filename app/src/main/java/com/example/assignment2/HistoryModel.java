package com.example.assignment2;

import android.graphics.Bitmap;

public class HistoryModel {
    private String id;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    private String type;
    private String code;
    private Bitmap img;
    private String date;

    public HistoryModel(String id, String type, String code, Bitmap img, String date) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.img = img;
        this.date = date;
    }
}
