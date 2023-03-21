package implementation.pages;

import implementation.Database;
import implementation.user.Credentials;
import implementation.user.User;

import java.util.ArrayList;

public final class Register extends Page {
    public Register() {
        super(new ArrayList<>(), false, "register");
    }

    /**
     * Register user
     * @param newCredentials new user credentials
     * @return success
     */
    public User register(final Credentials newCredentials) {
        User u = new User(newCredentials);
        Database.getInstance().addUser(u);

        return u;
    }
}
