package implementation;

import implementation.movies.Movie;
import implementation.user.User;

public final class Helper {
    private Helper() { }

    /**
     * Movie available to user
     * @param user user
     * @param movie movie
     * @return is available
     */
    public static boolean movieAvailableToUser(final User user, final Movie movie) {
        return !movie.getCountriesBanned().contains(user.getCredentials().getCountry());
    }
}
