package com.example.newsfeed;

public class News {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mPillarName;
    private String mUrl;
    private String mAuthor;

    public News(String Section, String Title, String Date, String Url, String PillarName,String Author) {
        mTitle = Title;
        mSection = Section;
        mDate = Date;
        mPillarName = PillarName;
        mUrl = Url;
        mAuthor=Author;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmPillarName() {
        return mPillarName;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmAuthor(){return mAuthor;}
}


