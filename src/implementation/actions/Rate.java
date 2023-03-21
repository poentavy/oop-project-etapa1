package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.movies.Movie;
import implementation.pages.Page;
import implementation.pages.SeeDetails;
import implementation.user.User;

import java.util.ArrayList;
import java.util.List;

public final class Rate implements Action {
    private final int rating;

    public Rate(final int rating) {
        this.rating = rating;
    }

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();
        User user = Database.getInstance().getCurrentUser();
        Movie currentMovie = Database.getInstance().getSeeingInfo();

        if (!currentPage.getName().equals("see details")) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }

        SeeDetails detailsPage = (SeeDetails) currentPage;
        if (!detailsPage.rate(rating)) {
            Database.getInstance().setCurrentPage("see details");
            OutputHelper.generateError(output);
            return;
        }

        OutputHelper.writeActionMessage(output, user, new ArrayList<>(List.of(currentMovie)));
    }
}
