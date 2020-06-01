package service;

import app.Result;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateResultsService {
    public List<Result> createResults(List<Document> documents) {
        List<Result> results = new ArrayList<>();

        int i = 0;
        for (Document doc : documents) {
            i++;
            String tweet_url = doc.get("permalink");
            String tweet_text = doc.get("formattedText");
            String tweet_image = doc.get("image_url");
            String news_url = doc.get("news_url");
            String user = doc.get("username");
            String date = doc.get("date");
            String retweets = doc.get("retweets");
            Result result = new Result(i, user, tweet_url, tweet_image, tweet_text, news_url, retweets, date);
            results.add(result);
        }

        return results;
    }
}
