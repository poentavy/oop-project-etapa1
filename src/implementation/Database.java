package implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.actions.*;
import implementation.movies.Movie;
import implementation.pages.*;
import implementation.pages.Login;
import implementation.pages.Register;
import implementation.user.Credentials;
import implementation.user.User;
import java.util.ArrayList;
import java.util.HashMap;

public final class Database {
    private static Database instance = null;
    private ObjectNode input;
    private ArrayNode output;
    private ArrayList<User> users;
    private HashMap<String, Page> pages;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    private ArrayList<Movie> filteredMovies;
    private User currentUser;
    private Page currentPage;
    private Movie seeingInfo;
    private Database() { }

    /**
     * Get instance
     * @return database instance
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Get users
     * @return users array
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Get movies
     * @return movies array
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public HashMap<String, Page> getPages() {
        return pages;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = pages.get(currentPage);
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Add user
     * @param newUser new user
     */
    public void addUser(final User newUser) {
        users.add(newUser);
    }

    /**
     * Get filtered movies
     * @return filtered movies
     */
    public ArrayList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    /**
     * Set filtered movies
     * @param filteredMovies filtered movies
     */
    public void setFilteredMovies(final ArrayList<Movie> filteredMovies) {
        this.filteredMovies = filteredMovies;
    }

    public Movie getSeeingInfo() {
        return seeingInfo;
    }

    public void setSeeingInfo(final Movie seeingInfo) {
        this.seeingInfo = seeingInfo;
    }

    /**
     * Init instance
     * @param in input node
     * @param out output node
     */
    public void init(final ObjectNode in, final ArrayNode out) {
        this.input = in;
        this.output = out;
        users = new ArrayList<>();
        pages = new HashMap<>();
        movies = new ArrayList<>();
        actions = new ArrayList<>();
        currentUser = null;

        Page homeAuth = new HomeAuth();
        Page homeNotAuth = new HomeNotAuth();
        Page login = new Login();
        Page logout = new Logout();
        Page movs = new Movies();
        Page register = new Register();
        Page seeDetails = new SeeDetails();
        Page upgrades = new Upgrades();

        pages.put(homeAuth.getName(), homeAuth);
        pages.put(homeNotAuth.getName(), homeNotAuth);
        pages.put(login.getName(), login);
        pages.put(logout.getName(), logout);
        pages.put(movs.getName(), movs);
        pages.put(register.getName(), register);
        pages.put(seeDetails.getName(), seeDetails);
        pages.put(upgrades.getName(), upgrades);

        currentPage = pages.get("HomeNotAuth");

        readUsers();
        readMovies();
        readActions();
    }

    /**
     * Run
     */
    public void run() {
        for (var a : actions) {
            ObjectNode o = output.addObject();
            int idx = 0;

            for (int i = 0; i < output.size(); i++) {
                if (output.get(i) == o) {
                    idx = i;
                    break;
                }
            }

            a.run(o);
            if (o.isEmpty()) {
                output.remove(idx);
            }
        }
    }

    private void readUsers() {
        ArrayNode usrs = (ArrayNode) input.get("users");

        for (JsonNode userNode : usrs) {
            ObjectNode userObject = (ObjectNode) userNode;
            ObjectNode userCreds = (ObjectNode) userObject.get("credentials");

            final String name = userCreds.get("name").asText();
            final String password = userCreds.get("password").asText();
            final String accountTypeString = userCreds.get("accountType").asText();
            final String country = userCreds.get("country").asText();
            final int balance = userCreds.get("balance").asInt();
            Credentials.AccountType accountType;

            if (accountTypeString.equals("standard")) {
                accountType = Credentials.AccountType.STANDARD;
            } else {
                accountType = Credentials.AccountType.PREMIUM;
            }

            Credentials credentials = new Credentials(name, password,
                    accountType, country, balance);
            User newUser = new User(credentials);
            users.add(newUser);
        }
    }

    private void readMovies() {
        ArrayNode mvs = (ArrayNode) input.get("movies");

        for (JsonNode movieNode : mvs) {
            ObjectNode movieObject = (ObjectNode) movieNode;

            final String name = movieObject.get("name").asText();
            final int year = movieObject.get("year").asInt();
            final int duration = movieObject.get("duration").asInt();
            final ArrayList<String> genres = new ArrayList<>();
            final ArrayList<String> actors = new ArrayList<>();
            final ArrayList<String> countriesBanned = new ArrayList<>();

            ArrayNode genresNode = (ArrayNode) movieObject.get("genres");
            for (var g : genresNode) {
                genres.add(g.asText());
            }

            ArrayNode actorsNode = (ArrayNode) movieObject.get("actors");
            for (var a : actorsNode) {
                actors.add(a.asText());
            }

            ArrayNode countriesBannedNode = (ArrayNode) movieObject.get("countriesBanned");
            for (var cb : countriesBannedNode) {
                countriesBanned.add(cb.asText());
            }

            Movie newMovie = new Movie(name, year, duration, genres, actors, countriesBanned);
            movies.add(newMovie);
        }
    }

    private void readActions() {
        ArrayNode acts = (ArrayNode) input.get("actions");

        for (JsonNode actNode : acts) {
            ObjectNode actionObject = (ObjectNode) actNode;

            final String type = actionObject.get("type").asText();
            Action newAction = null;

            if (type.equals("change page")) {
                final String target = actionObject.get("page").asText();
                if (target.equals("see details")) {
                    final String movie = actionObject.get("movie").asText();
                    newAction = new ChangePageSeeDetails(movie);
                } else {
                    newAction = new ChangePage(target);
                }
            } else if (type.equals("on page")) {
                final String feature = actionObject.get("feature").asText();

                if (feature.equals("login")) {
                    ObjectNode creds = (ObjectNode) actionObject.get("credentials");
                    final String name = creds.get("name").asText();
                    final String password = creds.get("password").asText();

                    newAction = new implementation.actions.Login(name, password);
                } else if (feature.equals("register")) {
                    ObjectNode creds = (ObjectNode) actionObject.get("credentials");
                    final String name = creds.get("name").asText();
                    final String password = creds.get("password").asText();
                    final String accountTypeString = creds.get("accountType").asText();
                    final String country = creds.get("country").asText();
                    final int balance = creds.get("balance").asInt();
                    Credentials.AccountType accountType;

                    if (accountTypeString.equals("standard")) {
                        accountType = Credentials.AccountType.STANDARD;
                    } else {
                        accountType = Credentials.AccountType.PREMIUM;
                    }

                    Credentials credentials = new Credentials(name, password,
                            accountType, country, balance);

                    newAction = new implementation.actions.Register(credentials);
                } else if (feature.equals("search")) {
                    final String startsWith = actionObject.get("startsWith").asText();

                    newAction = new Search(startsWith);
                } else if (feature.equals("filter")) {
                    String rating = "";
                    String duration = "";
                    final ArrayList<String> actors = new ArrayList<>();
                    final ArrayList<String> genres = new ArrayList<>();

                    ObjectNode filters = (ObjectNode) actionObject.get("filters");
                    if (filters.has("sort")) {
                        ObjectNode sort = (ObjectNode) filters.get("sort");

                        if (sort.has("rating")) {
                            rating = sort.get("rating").asText();
                        }

                        if (sort.has("duration")) {
                            duration = sort.get("duration").asText();
                        }
                    }

                    if (filters.has("contains")) {
                        ObjectNode contains = (ObjectNode) filters.get("contains");

                        if (contains.has("actors")) {
                            ArrayNode act = (ArrayNode) contains.get("actors");

                            for (var a : act) {
                                actors.add(a.asText());
                            }
                        }

                        if (contains.has("genre")) {
                            ArrayNode gnr = (ArrayNode) contains.get("genre");

                            for (var g : gnr) {
                                genres.add(g.asText());
                            }
                        }
                    }

                    newAction = new Filter(rating, duration, actors, genres);
                } else if (feature.equals("buy tokens")) {
                    final String count = actionObject.get("count").asText();
                    final int amount = Integer.parseInt(count);

                    newAction = new BuyTokens(amount);
                } else if (feature.equals("buy premium account")) {
                    newAction = new BuyPremium();
                } else if (feature.equals("purchase")) {
                    newAction = new Purchase();
                } else if (feature.equals("watch")) {
                    newAction = new Watch();
                } else if (feature.equals("like")) {
                    newAction = new Like();
                } else if (feature.equals("rate")) {
                    final int rating = actionObject.get("rate").asInt();
                    newAction = new Rate(rating);
                }
            }

            if (newAction != null) {
                actions.add(newAction);
            }
        }
    }
}
