package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.movies.Movie;
import implementation.pages.Movies;
import implementation.pages.Page;
import implementation.user.User;

import java.util.ArrayList;

public final class Filter implements Action {
    private final String rating;
    private final String duration;
    private final ArrayList<String> actors;
    private final ArrayList<String> genre;

    public Filter(final String rating, final String duration,
                  final ArrayList<String> actors, final ArrayList<String> genre) {
        this.rating = rating;
        this.duration = duration;
        this.actors = actors;
        this.genre = genre;
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
        ArrayList<Movie> movies = moviesPage.filter(rating, duration, actors, genre);

        OutputHelper.writeActionMessage(output, user, movies);
        Database.getInstance().setFilteredMovies(movies);
    }
}
