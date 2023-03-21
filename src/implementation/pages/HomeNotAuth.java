package implementation.pages;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeNotAuth extends Page {
    public HomeNotAuth() {
        super(new ArrayList<>(Arrays.asList("login", "register")), false, "HomeNotAuth");
    }
}
