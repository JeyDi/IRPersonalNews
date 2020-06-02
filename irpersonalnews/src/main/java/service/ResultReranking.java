package service;

import app.CosineSimilarity;
import app.TextAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ResultReranking {

    public List<Document> rank(List<Document> documents, Document userProfile) throws IOException {
        if (documents.size() == 0) {
            return documents;
        }

        Map<String, Float> userProfileFrequencies = countFrequencies(getTerms(userProfile, "keywords"));
        int limit = documents.size() > 10 ? 10 : documents.size();
        List<Document> topDocuments = documents.subList(0, limit);
        Map<Document, Double> rankedTopDocs = new HashMap<>();

        for (Document doc : topDocuments) {
            List<String> docTerms = getTerms(doc, "news_text");
            if (docTerms.size() == 0) {
                Double similarity = 0.0d;
                rankedTopDocs.put(doc, similarity);
            } else {
                Map<String, Float> documentFrequencies = countFrequencies(docTerms);
                Double similarity = new CosineSimilarity().cosineSimilarity(userProfileFrequencies, documentFrequencies);
                rankedTopDocs.put(doc, similarity);
            }
        }

        List<Document> sortedTopDocs = rankedTopDocs.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        List<Document> unsortedDocuments = documents.subList(limit, documents.size());


        return Stream.concat(sortedTopDocs.stream(), unsortedDocuments.stream())
                .collect(Collectors.toList());
    }

    private List<String> getTerms(Document document, String field) throws IOException {
        TokenStream stream = TextAnalyzer.textAnalyzer().tokenStream(field, new StringReader(document.getField(field).stringValue()));

        List<String> result = new ArrayList<>();
        try {
            stream.reset();
            while (stream.incrementToken()) {
                result.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
            // not thrown b/c we're using a string reader...
        }

        return result;

    }

    private Map<String, Float> countFrequencies(List<String> terms) {
        Map<String, Float> wordCount = new HashMap<>();

        for (String word : terms) {
            Float count = wordCount.getOrDefault(word, 0f);
            wordCount.put(word, count + 1);
        }

        return wordCount;
    }
}
