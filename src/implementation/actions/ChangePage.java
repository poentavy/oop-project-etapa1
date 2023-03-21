package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.Helper;
import implementation.OutputHelper;
import implementation.movies.Movie;
import implementation.pages.Page;
import implementation.user.User;

import java.util.ArrayList;

public final class ChangePage implements Action {
    private final String newPage;

    public ChangePage(final String newPage) {
        this.newPage = newPage;
    }

    @Override
    public void run(final ObjectNode output) {
        User user = Database.getInstance().getCurrentUser();
        Page currentPage = Database.getInstance().getCurrentPage();
        Page targetPage = null;

        if (newPage.equals("logout")) {
            if (user == null) {
                OutputHelper.generateError(output);
            }
            Database.getInstance().setCurrentUser(null);
            Database.getInstance().setCurrentPage("HomeNotAuth");
            return;
        }

        for (String name : currentPage.getSubPages()) {
            if (name.equals(newPage)) {
                targetPage = Database.getInstance().getPages().get(name);
                break;
            }
        }

        if (targetPage == null) {
            if (user != null) {
                Database.getInstance().setCurrentPage("HomeAuth");
            } else {
                Database.getInstance().setCurrentPage("HomeNotAuth");
            }
            OutputHelper.generateError(output);
            return;
        }

        if (targetPage.needsAuth() && user == null) {
            Database.getInstance().setCurrentPage("HomeNotAuth");
            OutputHelper.generateError(output);
            return;
        }

        Database.getInstance().setCurrentPage(targetPage.getName());

        if (targetPage.getName().equals("movies")) {
            ArrayList<Movie> movies = new ArrayList<>(Database.getInstance().getMovies());
            movies.removeIf(m ->
                !Helper.movieAvailableToUser(user, m)
            );

            OutputHelper.writeActionMessage(output, user, movies);
            Database.getInstance().setFilteredMovies(movies);
        }
    }
}
