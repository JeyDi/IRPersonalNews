package app;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class IndexBuilder {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = new File("resources/documents/documents/import.csv").getAbsolutePath();
        Path path = Paths.get(new File("resources/index/").getAbsolutePath());
        Directory dir = FSDirectory.open(path);

        IndexWriterConfig config = new IndexWriterConfig(TextAnalyzer.textAnalyzer());
        config.setSimilarity(new BM25Similarity(1.2f, 0.75f));
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(dir, config);

        List<NewsTweet> tweets = TweetReader.read(filePath);


        for (NewsTweet tweet : tweets) {
            Document document = new Document();
            Field username = new StringField("username", tweet.username, Field.Store.YES);
            document.add(username);
            Field date = new StringField("date", tweet.date, Field.Store.YES);
            document.add(date);
            Field text = new TextField("text", removeURLS(tweet.text), Field.Store.YES);
            document.add(text);
            Field hashtags = new TextField("hashtags", tweet.hashtags, Field.Store.YES);
            document.add(hashtags);
            Field id = new StringField("id", tweet.id, Field.Store.YES);
            document.add(id);
            Field permalink = new StoredField("permalink", tweet.permalink);
            document.add(permalink);
            Field news_url = new StoredField("news_url", tweet.news_url);
            document.add(news_url);
            Field newsText = new TextField("news_text", tweet.news_text, Field.Store.YES);
            document.add(newsText);
            Field imageURL = new StoredField("image_url", tweet.image_url);
            document.add(imageURL);
            Field retweets = new StoredField("retweets", tweet.retweets);
            document.add(retweets);
            Field formattedText = new StoredField("formattedText", textToHtmlConvertingURLsToLinks(tweet.text));
            document.add(formattedText);

            Field eta = new NumericDocValuesField("eta", getTimestamp(tweet.date));
            document.add(eta);
            Field sort_retweets = new NumericDocValuesField("sort_retweets", (long) tweet.retweets);
            document.add(sort_retweets);

            iwriter.addDocument(document);
        }
        iwriter.commit();
        iwriter.close();

    }

    private static String removeURLS(String text) {
        String regex = "/(((http|ftp|https):\\/{2})+(([0-9a-z_-]+\\.)+(aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mn|mn|mo|mp|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|nom|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ra|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw|arpa)(:[0-9]+)?((\\/([~0-9a-zA-Z\\#\\+\\%@\\.\\/_-]+))?(\\?[0-9a-zA-Z\\+\\%@\\/&\\[\\];=_-]+)?)?))\\b/imuS";
        text = text.replaceAll(regex, "");
        text = text.replaceAll("((pic.twitter.com/)[a-zA-Z0-9]*|http|https)", "");
        return text;
    }

    private static String textToHtmlConvertingURLsToLinks(String text) {
        if (text == null) {
            return null;
        }

        String escapedText = HtmlUtils.htmlEscape(text);

        text = escapedText.replaceAll("(\\A|\\s)([-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))(\\s|\\z)",
                "$1<a href=\"http://$2\">$2</a>$4");

        text = text.replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)", "$1<a href=\"$2\">$2</a>$4");

        return text;
    }


    private static long getTimestamp(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(date));
        long time = c.getTimeInMillis();
        long curr = System.currentTimeMillis();
        long diff = curr - time;    //Time difference in milliseconds
        return diff / 1000;
    }
}
