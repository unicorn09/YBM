package com.example.ybm;

public class ScreenItem {

    String Title,Description, Short;
    int ScreenImg;

    public ScreenItem(String title, String description, String shortDesc, int screenImg) {
        Title = title;
        Description = description;
        Short = shortDesc;
        ScreenImg = screenImg;
    }

    public String getShort() {
        return Short;
    }

    public void setShort(String aShort) {
        Short = aShort;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
