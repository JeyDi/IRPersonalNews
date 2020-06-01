package webapp;


import app.Result;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.CreateResultsService;
import service.NewsSearcherService;
import service.ResultReranking;
import service.UserProfileSearchService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class QueryController {

    private final NewsSearcherService searcher;
    private final CreateResultsService resultsService;
    private final ResultReranking resultReranking;
    private final UserProfileSearchService userProfileSearchService;

    @Autowired
    public QueryController(NewsSearcherService searcher, CreateResultsService resultsService, ResultReranking resultReranking, UserProfileSearchService userProfileSearchService) {
        this.searcher = searcher;
        this.resultsService = resultsService;
        this.resultReranking = resultReranking;
        this.userProfileSearchService = userProfileSearchService;
    }

    // Return Template Search HTML
    @RequestMapping("/search")
    public String query(@RequestParam Map<String,String> requestParams, Model model) {
        return "search";
    }

    @RequestMapping("/compute")
    public String search(@RequestParam Map<String,String> requestParams, Model model) throws IOException, ParseException {
        String query = requestParams.get("query");
        String user = requestParams.get("ic-current-url").split("=")[1];
        String topic = requestParams.get("topic");
        String personalize = requestParams.getOrDefault("personalization", "off");



        if (query == null || query.isEmpty()) {
            return "results";
        }

        //Model = output
        model.addAttribute("topic", topic);
        model.addAttribute("user", user);
        model.addAttribute("query", query);
        List<Document> retrivedDocs = searcher.search(query);
        List<Result> results;
        Document userProfile = userProfileSearchService.getUserTopicProfile(user, topic);
        if (personalize.equals("on") && userProfile != null) {

            List<Document> rankedDocs = resultReranking.rank(retrivedDocs, userProfile);
            results = resultsService.createResults(rankedDocs);
        } else {
            results = resultsService.createResults(retrivedDocs);
        }

        //Add the results to the output (model)
        model.addAttribute("results", results);

        //Return the template results
        return "results";
    }
}
