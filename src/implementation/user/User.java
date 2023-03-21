package implementation.user;

import implementation.movies.Movie;

import java.util.ArrayList;

public final class User {
    private final Credentials credentials;
    private int tokens;
    private int freeMovies;
    private final ArrayList<Movie> watchedMovies;
    private final ArrayList<Movie> likedMovies;
    private final ArrayList<Movie> purchasedMovies;
    private final ArrayList<Movie> ratedMovies;

    public User(final Credentials credentials) {
        final int premiumFreeMovies = 15;
        this.credentials = credentials;
        tokens = 0;
        freeMovies = premiumFreeMovies;
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        purchasedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(final int tokens) {
        this.tokens = tokens;
    }

    public int getFreeMovies() {
        return freeMovies;
    }

    public void setFreeMovies(final int freeMovies) {
        this.freeMovies = freeMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * Watch movie
     * @param m movie
     */
    public void watchMovie(final Movie m) {
        watchedMovies.add(m);
    }

    /**
     * Rate movie
     * @param m movie
     */
    public void rateMovie(final Movie m) {
        ratedMovies.add(m);
    }

    /**
     * Like movie
     * @param m movie
     */
    public void likeMovie(final Movie m) {
        likedMovies.add(m);
    }

    /**
     * Purchase movie
     * @param m movie
     */
    public void purchaseMovie(final Movie m) {
        purchasedMovies.add(m);
    }
}
