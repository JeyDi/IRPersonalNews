package app;

import javax.validation.constraints.Null;

public class NewsTweet {
    public NewsTweet(String username, String date, int retweets, String text, String hashtags, String id, String permalink, String news_url, String news_text, String image_url) {
        this.username = username;
        this.date = date;
        this.retweets = retweets;
        this.text = text;
        this.hashtags = hashtags;
        this.id = id;
        this.permalink = permalink;
        this.news_url = news_url;
        this.news_text = news_text;
        this.image_url = image_url;
    }
    public NewsTweet(String username, String date, int retweets, String text, String hashtags, String id, String permalink, String news_text) {
        this.username = username;
        this.date = date;
        this.retweets = retweets;
        this.text = text;
        this.hashtags = hashtags;
        this.id = id;
        this.permalink = permalink;
        this.news_url = "";
        this.news_text = news_text;
        this.image_url = "";
    }

    public String username;
    public String date;
    public int retweets;
    public String text;
    public String hashtags;
    public String id;
    public String permalink;
    public String news_url;
    public String news_text;
    public String image_url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_text() {
        return news_text;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }
}
