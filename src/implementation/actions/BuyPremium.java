package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.pages.Page;
import implementation.pages.Upgrades;
import implementation.user.User;

public final class BuyPremium implements Action {

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();
        User user = Database.getInstance().getCurrentUser();

        if (!currentPage.getName().equals("upgrades")) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }

        Upgrades upgradesPage = (Upgrades) currentPage;
        if (!upgradesPage.buyPremium()) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }
    }
}
