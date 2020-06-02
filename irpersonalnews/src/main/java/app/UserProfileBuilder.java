package app;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserProfileBuilder {
    public static void main(String[] args) throws IOException {
        String filePath = new File("resources/documents/users/").getAbsolutePath();
        Path path = Paths.get(new File("resources/profiles_index/").getAbsolutePath());
        Directory dir = FSDirectory.open(path);

        IndexWriterConfig config = new IndexWriterConfig(TextAnalyzer.textAnalyzer());
        config.setSimilarity(new BM25Similarity(1.2f, 0.75f));
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(dir, config);

        Integer min_users = 1;
        Integer max_users = 10;

        for (Integer i = min_users; i <= max_users; i++) {
            String userPath = filePath + "/" + i + "/";
            List<File> userInterests = getFiles(userPath);

            for (File interest : userInterests) {
                System.out.println(interest.getAbsolutePath());
                String content = new String(Files.readAllBytes(Paths.get(interest.getAbsolutePath())));
                Document document = new Document();
                Field userID = new StringField("userID", i.toString(), Field.Store.YES);
                document.add(userID);
                Field topic = new StringField("topic", interest.getName().replace(".txt", ""), Field.Store.YES);
                document.add(topic);
                FieldType type = new FieldType();
                type.setStored(true);
                type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
                type.setStoreTermVectors(true);
                Field keywords = new Field("keywords", content, type);
                document.add(keywords);
                iwriter.addDocument(document);
            }
        }

        iwriter.commit();
        iwriter.close();

    }


    private static List<File> getFiles(String path) {
        File[] files = new File(path).listFiles();
        System.out.println(path);

        assert files != null;
        List<File> results = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file);
            }
        }

        return results;
    }
}
