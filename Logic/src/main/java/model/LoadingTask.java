package model;

import javafx.concurrent.Task;
import model.data.Movie;
import org.jdom2.Document;
import model.resources.XMLJDomFunctions;

import java.util.List;

public class LoadingTask extends Task {
    String found;

    public LoadingTask () {
    }

    @Override
    protected Object call() throws Exception {
        List<String> movies = Fetch.getAllTitles();
        int max = movies.size();
        updateProgress(0, max);
        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        for (int i = 0; i < max; i++) {
            Movie temp = Fetch.findMovie(movies.get(i));
            doc = XMLManipulation.addMovie(temp, doc);
            System.out.println("\n\n");
//            WHETHER SAVE XML FILE AFTER EVERY RECORD OR AT THE END
            XMLJDomFunctions.writeDocumentToFile(doc, "movies.xml");
            updateProgress(i + 1, max);
//            break;
            if (isCancelled()) {
                return found;
            }
        }
//        System.out.println(getProgress());
        updateProgress(0, max - 1);
        found = XMLJDomFunctions.readDocumentToString(doc);
        return found;
    }
}