package service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class UserProfileSearchService {
    public Document getUserTopicProfile(String user_id, String topic) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        final String INDEX_DIRECTORY = new File("resources/profiles_index/").getAbsolutePath();
        Directory index = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
        IndexReader reader = DirectoryReader.open(index);
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"userID", "topic"}, analyzer);
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BooleanSimilarity());


        Query query = parser.parse("topic:" + topic + " AND userID:" + user_id);
        TopDocs topDoc = searcher.search(query, 1);
        if (topDoc.scoreDocs.length == 0) {
            return null;
        }
        ScoreDoc hit = topDoc.scoreDocs[0];

        return searcher.doc(hit.doc);
    }
}