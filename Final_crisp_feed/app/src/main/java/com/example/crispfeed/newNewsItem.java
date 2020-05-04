package com.example.crispfeed;

public class newNewsItem {
    private String headline;
    private String description;
    private int upvote;
    private String urlToImage;
    private String url;
    private String idd;

    public newNewsItem(){}

    public String getUrl() {
        return url;
    }

    public newNewsItem(String headline, String description, int upvote, String urlToImage, String url) {
        this.headline = headline;
        this.description = description;
        this.upvote = upvote;
        this.urlToImage = urlToImage;
        this.url=url;
    }

    public String getHeadline() {
        return headline;
    }



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