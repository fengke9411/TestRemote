package com.frank.entity;

import java.io.Serializable;

/**
 *
 * 文章实体类
 * Created by frank on 2016/2/16.
 */
public class Essay implements Serializable{
//    private int id;
    private String author;
    private String essayTitle;
    private String essayBody;
    private String publishDate;
    private int essayType;

    public Essay() {

    }

    public Essay(String author, String essayTitle, String essayBody, String publishDate, int essayType) {

        this.author = author;
        this.essayTitle = essayTitle;
        this.essayBody = essayBody;
        this.publishDate = publishDate;
        this.essayType = essayType;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEssayBody() {
        return essayBody;
    }

    public void setEssayBody(String essayBody) {
        this.essayBody = essayBody;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public void setEssayTitle(String essayTitle) {
        this.essayTitle = essayTitle;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getEssayType() {
        return essayType;
    }

    public void setEssayType(int essayType) {
        this.essayType = essayType;
    }

    @Override
    public String toString() {
        return "Essay{" +
                "author='" + author + '\'' +
                ", essayTitle='" + essayTitle + '\'' +
                ", essayBody='" + essayBody + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", essayType=" + essayType +
                '}';
    }
}
