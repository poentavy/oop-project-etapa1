package implementation.pages;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeAuth extends Page {
    public HomeAuth() {
        super(new ArrayList<>(Arrays.asList("movies", "upgrades", "logout")),
                true, "HomeAuth");
    }
}
