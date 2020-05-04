package com.example.crispfeed;

public class NewsItem {
    private String headline;
    private String description;
    private int upvote;
    private String urlToImage;
    private String url;
    private String idd;

    public NewsItem(){}

    public String getUrl() {
        return url;
    }

    public NewsItem(String headline, String description, int upvote, String urlToImage, String url, String id) {
        this.headline = headline;
        this.description = description;
        this.upvote = upvote;
        this.urlToImage = urlToImage;
        this.url=url;
        this.idd = id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getId(){return idd;}



    public String getDescription() {
        return description;
    }



    public int getUpvote() {
        return upvote;
    }



    public String getUrlToImage() {
        return urlToImage;
    }


}