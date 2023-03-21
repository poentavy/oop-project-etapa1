package implementation.pages;

import implementation.Database;
import implementation.movies.Movie;
import implementation.user.Credentials;
import implementation.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public final class SeeDetails extends Page {
    public SeeDetails() {
        super(new ArrayList<>(Arrays.asList("HomeAuth", "movies", "upgrades", "logout")),
                true, "see details");
    }

    /**
     * Purchase movie
     * @return success
     */
    public boolean purchase() {
        Movie currentMovie = Database.getInstance().getSeeingInfo();
        User currentUser = Database.getInstance().getCurrentUser();

        if (currentUser.getPurchasedMovies().contains(currentMovie)) {
            return false;
        }

        if (currentUser.getCredentials().getAccountType() == Credentials.AccountType.PREMIUM
                && currentUser.getFreeMovies() > 0) {
            currentUser.purchaseMovie(currentMovie);
            currentUser.setFreeMovies(currentUser.getFreeMovies() - 1);
        } else {
            if (currentUser.getTokens() < 2) {
                return false;
            }

            currentUser.purchaseMovie(currentMovie);
            currentUser.setTokens(currentUser.getTokens() - 2);
        }

        return true;
    }

    /**
     * Watch movie
     * @return success
     */
    public boolean watch() {
        Movie currentMovie = Database.getInstance().getSeeingInfo();
        User currentUser = Database.getInstance().getCurrentUser();

        if (!currentUser.getPurchasedMovies().contains(currentMovie)
                || currentUser.getWatchedMovies().contains(currentMovie)) {
            return false;
        }

        currentUser.watchMovie(currentMovie);
        return true;
    }

    /**
     * Like movie
     * @return success
     */
    public boolean likeMovie() {
        Movie currentMovie = Database.getInstance().getSeeingInfo();
        User currentUser = Database.getInstance().getCurrentUser();

        if (!currentUser.getPurchasedMovies().contains(currentMovie)
                || !currentUser.getWatchedMovies().contains(currentMovie)
                || currentUser.getLikedMovies().contains(currentMovie)) {
            return false;
        }

        currentUser.likeMovie(currentMovie);
        currentMovie.like();
        return true;
    }

    /**
     * Rate movie
     * @param rating new rating
     * @return success
     */
    public boolean rate(final int rating) {
        Movie currentMovie = Database.getInstance().getSeeingInfo();
        User currentUser = Database.getInstance().getCurrentUser();
        final int maxRating = 5;

        if (rating > maxRating || rating < 1) {
            return false;
        }

        if (!currentUser.getPurchasedMovies().contains(currentMovie)
                || !currentUser.getWatchedMovies().contains(currentMovie)
                || currentUser.getRatedMovies().contains(currentMovie)) {
            return false;
        }

        currentUser.rateMovie(currentMovie);
        currentMovie.addRating(rating);
        return true;
    }
}
