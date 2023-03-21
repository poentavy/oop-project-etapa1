package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.movies.Movie;
import implementation.pages.Movies;
import implementation.pages.Page;
import implementation.user.User;

import java.util.ArrayList;

public final class Search implements Action {
    private final String startsWith;

    public Search(final String startsWith) {
        this.startsWith = startsWith;
    }

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();
        User user = Database.getInstance().getCurrentUser();

        if (!currentPage.getName().equals("movies")) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }

        Movies moviesPage = (Movies) currentPage;
        ArrayList<Movie> movies = moviesPage.search(startsWith);

        OutputHelper.writeActionMessage(output, user, movies);
        Database.getInstance().setFilteredMovies(movies);
    }
}
