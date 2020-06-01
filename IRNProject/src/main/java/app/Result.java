package app;

public class Result {

    private String tweet_link;
    private String image_url;
    private String tweet_text;
    private String news_url;
    private String rank;
    private String user;
    private String retweets;
    private String date;

    public Result(int rank,String user, String tweet_link, String image_url, String tweet_text, String news_url, String retweets, String date) {
        this.user = user;
        this.rank = String.valueOf(rank);
        this.tweet_link = tweet_link;
        this.image_url = image_url;
        this.tweet_text = tweet_text;
        this.news_url = news_url;
        this.retweets = retweets;
        this.date = date;
    }

    public String getTweet_link() {
        return tweet_link;
    }

    public void setTweet_link(String tweet_link) {
        this.tweet_link = tweet_link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getRank() {
        return "Rank: " + rank;
    }

    public void setRank(int rank) {
        this.rank = String.valueOf(rank);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRetweets() {
        return "Retweets: " + retweets;
    }

    public void setRetweets(String retweets) {
        this.retweets = retweets;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
