package implementation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.movies.Movie;
import implementation.user.Credentials;
import implementation.user.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public final class OutputHelper {
    private OutputHelper() { }

    /**
     * Generate error
     * @param output output node
     */
    public static void generateError(final ObjectNode output) {
        output.put("error", "Error");
        output.putArray("currentMoviesList");
        output.putNull("currentUser");
    }

    /**
     * Write movie info
     * @param output output node
     * @param movie movie
     */
    public static void writeMovieInfo(final ObjectNode output, final Movie movie) {
        output.put("name", movie.getName());
        output.put("year", movie.getYear());
        output.put("duration", movie.getDuration());
        ArrayNode genres = output.putArray("genres");
        for (var g : movie.getGenres()) {
            genres.add(g);
        }
        ArrayNode actors = output.putArray("actors");
        for (var a : movie.getActors()) {
            actors.add(a);
        }
        ArrayNode countriesBanned = output.putArray("countriesBanned");
        for (var cb : movie.getCountriesBanned()) {
            countriesBanned.add(cb);
        }
        output.put("numLikes", movie.getLikes());
        BigDecimal truncatedDouble = BigDecimal.valueOf(movie.getRating())
                .setScale(2, RoundingMode.DOWN);
        output.put("rating", truncatedDouble.doubleValue());
        output.put("numRatings", movie.getNumRatings());
    }

    /**
     * Write credentials
     * @param output output node
     * @param creds credentials
     */
    public static void writeCredentials(final ObjectNode output, final Credentials creds) {
        output.put("name", creds.getName());
        output.put("password", creds.getPassword());
        if (creds.getAccountType() == Credentials.AccountType.STANDARD) {
            output.put("accountType", "standard");
        } else {
            output.put("accountType", "premium");
        }
        output.put("country", creds.getCountry());
        output.put("balance", Integer.toString(creds.getBalance()));
    }

    /**
     * Write user info
     * @param output output node
     * @param user user
     */
    public static void writeUserInfo(final ObjectNode output, final User user) {
        ObjectNode creds = output.putObject("credentials");
        writeCredentials(creds, user.getCredentials());
        output.put("tokensCount", user.getTokens());
        output.put("numFreePremiumMovies", user.getFreeMovies());
        ArrayNode purchased = output.putArray("purchasedMovies");
        for (var m : user.getPurchasedMovies()) {
            if (!Helper.movieAvailableToUser(user, m)) {
                continue;
            }

            ObjectNode o = purchased.addObject();
            writeMovieInfo(o, m);
        }
        ArrayNode watched = output.putArray("watchedMovies");
        for (var m : user.getWatchedMovies()) {
            ObjectNode o = watched.addObject();
            writeMovieInfo(o, m);
        }
        ArrayNode liked = output.putArray("likedMovies");
        for (var m : user.getLikedMovies()) {
            ObjectNode o = liked.addObject();
            writeMovieInfo(o, m);
        }
        ArrayNode rated = output.putArray("ratedMovies");
        for (var m : user.getRatedMovies()) {
            ObjectNode o = rated.addObject();
            writeMovieInfo(o, m);
        }
    }

    /**
     * Write action message
     * @param output output node
     * @param user user
     * @param movies movies
     */
    public static void writeActionMessage(final ObjectNode output, final User user,
                                          final ArrayList<Movie> movies) {
        output.putNull("error");
        ArrayNode currentMovies = output.putArray("currentMoviesList");
        for (var m : movies) {
            writeMovieInfo(currentMovies.addObject(), m);
        }
        ObjectNode userInfo = output.putObject("currentUser");
        writeUserInfo(userInfo, user);
    }
}
