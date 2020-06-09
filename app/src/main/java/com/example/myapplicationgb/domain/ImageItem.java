package com.example.myapplicationgb.domain;

public class ImageItem {

    private String path;

    private String title;

    private long date;

    private int id;

    private int w;

    private int h;


    public ImageItem(String path, String title, long date, int id, int w, int h) {
        this.path = path;
        this.title = title;
        this.date = date;
        this.id=id;
        this.h=h;
        this.w=w;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", id=" + id +
                ", w=" + w +
                ", h=" + h +
                '}';
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
