package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.pages.Page;
import implementation.user.Credentials;
import implementation.user.User;
import java.util.ArrayList;

public final class Register implements Action {
    private final Credentials creds;

    public Register(final Credentials creds) {
        this.creds = creds;
    }

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();
        User currentUser = Database.getInstance().getCurrentUser();

        if (!currentPage.getName().equals("register")) {
            if (currentUser != null) {
                Database.getInstance().setCurrentPage("HomeAuth");
            } else {
                Database.getInstance().setCurrentPage("HomeNotAuth");
            }
            return;
        }

        Database.getInstance().setCurrentPage("HomeNotAuth");

        for (var u : Database.getInstance().getUsers()) {
            if (u.getCredentials().getName().equals(creds.getName())) {
                OutputHelper.generateError(output);
                return;
            }
        }

        implementation.pages.Register regPage = (implementation.pages.Register) currentPage;
        User user = regPage.register(creds);

        Database.getInstance().setCurrentPage("HomeAuth");
        Database.getInstance().setCurrentUser(user);
        OutputHelper.writeActionMessage(output, user, new ArrayList<>());
    }
}
