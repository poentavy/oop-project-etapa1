package implementation.pages;

import implementation.Database;
import implementation.user.Credentials;
import implementation.user.User;

import java.util.ArrayList;

public final class Login extends Page {
    public Login() {
        super(new ArrayList<>(), false, "login");
    }

    /**
     * Login user
     * @param name username
     * @param password user password
     * @return success
     */
    public User login(final String name, final String password) {
        User user = null;
        for (var u : Database.getInstance().getUsers()) {
            Credentials creds = u.getCredentials();

            if (creds.getName().equals(name) && creds.getPassword().equals(password)) {
                user = u;
                break;
            }
        }

        return user;
    }
}
