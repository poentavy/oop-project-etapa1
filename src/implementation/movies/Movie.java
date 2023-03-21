package implementation.movies;

import java.util.ArrayList;

public final class Movie {
    private final String name;
    private final int year;
    private final int duration;
    private final ArrayList<String> genres;
    private final ArrayList<String> actors;
    private final ArrayList<String> countriesBanned;
    private final ArrayList<Double> ratings;
    private int likes;

    public Movie(final String name, final int year, final int duration,
                 final ArrayList<String> genres, final ArrayList<String> actors,
                 final ArrayList<String> countriesBanned) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
        this.countriesBanned = countriesBanned;
        ratings = new ArrayList<>();
        likes = 0;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * Add new rating
     * @param value rating
     */
    public void addRating(final int value) {
        Double rating = (double) value;

        ratings.add(rating);
    }

    /**
     * Get rating
     * @return rating
     */
    public Double getRating() {
        if (ratings.size() == 0) {
            return 0.0;
        }

        Double sum = 0.0;
        Double n = (double) ratings.size();

        for (var r : ratings) {
            sum += r;
        }

        return sum / n;
    }

    public int getLikes() {
        return likes;
    }

    /**
     * Add new like
     */
    public void like() {
        likes++;
    }

    public int getNumRatings() {
        return ratings.size();
    }
}
