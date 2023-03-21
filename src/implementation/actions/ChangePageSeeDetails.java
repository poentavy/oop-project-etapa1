package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.Helper;
import implementation.OutputHelper;
import implementation.movies.Movie;
import implementation.pages.Page;
import implementation.user.User;

import java.util.ArrayList;
import java.util.List;

public final class ChangePageSeeDetails implements Action {
    private final String movie;

    public ChangePageSeeDetails(final String movie) {
        this.movie = movie;
    }

    @Override
    public void run(final ObjectNode output) {
        User user = Database.getInstance().getCurrentUser();
        Page currentPage = Database.getInstance().getCurrentPage();
        Page targetPage = Database.getInstance().getPages().get("see details");
        Movie mov = null;

        for (var m : Database.getInstance().getMovies()) {
            if (m.getName().equals(this.movie)) {
                mov = m;
                break;
            }
        }

        if (!currentPage.getSubPages().contains(targetPage.getName())) {
            if (user != null) {
                Database.getInstance().setCurrentPage("HomeAuth");
            } else {
                Database.getInstance().setCurrentPage("HomeNotAuth");
            }
            OutputHelper.generateError(output);
            return;
        }

        if (user == null) {
            Database.getInstance().setCurrentPage("HomeNotAuth");
            OutputHelper.generateError(output);
            return;
        }

        if (mov == null) {
            Database.getInstance().setCurrentPage("movies");
            OutputHelper.generateError(output);
            return;
        }

        if (!Database.getInstance().getFilteredMovies().contains(mov)) {
            Database.getInstance().setCurrentPage("movies");
            OutputHelper.generateError(output);
            return;
        }

        if (!Helper.movieAvailableToUser(user, mov)) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }

        OutputHelper.writeActionMessage(output, user, new ArrayList<>(List.of(mov)));
        Database.getInstance().setCurrentPage(targetPage.getName());
        Database.getInstance().setSeeingInfo(mov);
    }
}
