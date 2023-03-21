package implementation.pages;

import implementation.Database;
import implementation.Helper;
import implementation.movies.Movie;

import java.util.ArrayList;
import java.util.Arrays;

public final class Movies extends Page {
    public Movies() {
        super(new ArrayList<>(Arrays.asList("HomeAuth", "see details", "logout")),
                true, "movies");
    }

    /**
     * Search movies
     * @param startsWith name prefix
     * @return resulting movies
     */
    public ArrayList<Movie> search(final String startsWith) {
        ArrayList<Movie> res = new ArrayList<>();

        for (Movie m : Database.getInstance().getMovies()) {
            if (!Helper.movieAvailableToUser(Database.getInstance().getCurrentUser(), m)) {
                continue;
            }

            if (m.getName().startsWith(startsWith)) {
                res.add(m);
            }
        }

        return res;
    }

    /**
     * Filter movies
     * @param rating rating order
     * @param duration duration order
     * @param actors required actors
     * @param genre required genres
     * @return resulting movies
     */
    public ArrayList<Movie> filter(final String rating, final String duration,
                                   final ArrayList<String> actors,
                                   final ArrayList<String> genre) {
        ArrayList<Movie> res = new ArrayList<>();

        for (Movie m : Database.getInstance().getMovies()) {
            boolean containsActors = true;
            boolean containsGenre = true;

            if (!Helper.movieAvailableToUser(Database.getInstance().getCurrentUser(), m)) {
                continue;
            }

            for (var a : actors) {
                if (!m.getActors().contains(a)) {
                    containsActors = false;
                }
            }

            if (!containsActors) {
                continue;
            }

            for (var g : genre) {
                if (!m.getGenres().contains(g)) {
                    containsGenre = false;
                }
            }

            if (containsGenre) {
                res.add(m);
            }
        }

        res.sort((m1, m2) -> {
            int dur1 = m1.getDuration();
            int dur2 = m2.getDuration();
            Double rat1 = m1.getRating();
            Double rat2 = m2.getRating();

            int r = 0;
            if (duration.equals("decreasing")) {
                r = -Integer.compare(dur1, dur2);
            } else if (duration.equals("increasing")) {
                r = Integer.compare(dur1, dur2);
            }

            if (r != 0) {
                return r;
            }

            if (rating.equals("decreasing")) {
                r = -Double.compare(rat1, rat2);
            } else if (rating.equals("increasing")) {
                r = Double.compare(rat1, rat2);
            }

            return r;
        });

        return res;
    }
}
