package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.pages.Page;
import implementation.user.User;

import java.util.ArrayList;

public final class Login implements Action {
    private final String name;
    private final String password;

    public Login(final String name, final String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();
        User user = Database.getInstance().getCurrentUser();

        if (!currentPage.getName().equals("login")) {
            if (user != null) {
                Database.getInstance().setCurrentPage("HomeAuth");
            } else {
                Database.getInstance().setCurrentPage("HomeNotAuth");
            }
            OutputHelper.generateError(output);
            return;
        }

        Database.getInstance().setCurrentPage("HomeNotAuth");

        implementation.pages.Login loginPage = (implementation.pages.Login) currentPage;
        User newUser = loginPage.login(name, password);

        if (newUser == null) {
            OutputHelper.generateError(output);
            return;
        }

        Database.getInstance().setCurrentUser(newUser);
        Database.getInstance().setCurrentPage("HomeAuth");
        OutputHelper.writeActionMessage(output, newUser, new ArrayList<>());
    }
}
