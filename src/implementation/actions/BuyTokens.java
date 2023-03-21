package implementation.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.Database;
import implementation.OutputHelper;
import implementation.pages.Page;
import implementation.pages.Upgrades;

public final class BuyTokens implements Action {
    private final int amount;

    public BuyTokens(final int amount) {
        this.amount = amount;
    }

    @Override
    public void run(final ObjectNode output) {
        Page currentPage = Database.getInstance().getCurrentPage();

        if (!currentPage.getName().equals("upgrades")) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }

        Upgrades upgradesPage = (Upgrades) currentPage;
        if (!upgradesPage.buyTokens(amount)) {
            Database.getInstance().setCurrentPage("HomeAuth");
            OutputHelper.generateError(output);
            return;
        }
    }
}
