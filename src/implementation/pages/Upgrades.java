package implementation.pages;

import implementation.Database;
import implementation.user.Credentials;
import implementation.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public final class Upgrades extends Page {
    public Upgrades() {
        super(new ArrayList<>(Arrays.asList("HomeAuth", "movies", "logout")),
                true, "upgrades");
    }

    /**
     * Buy tokens
     * @param amount token amount
     * @return success
     */
    public boolean buyTokens(final int amount) {
        User user = Database.getInstance().getCurrentUser();
        int balance = user.getCredentials().getBalance();

        if (balance < amount) {
            return false;
        }

        balance -= amount;
        user.getCredentials().setBalance(balance);
        user.setTokens(user.getTokens() + amount);

        return true;
    }

    /**
     * Buy premium
     * @return success
     */
    public boolean buyPremium() {
        User user = Database.getInstance().getCurrentUser();
        int tokens = user.getTokens();
        final int premiumPrice = 10;

        if (user.getCredentials().getAccountType() == Credentials.AccountType.PREMIUM) {
            return false;
        }

        if (tokens < premiumPrice) {
            return false;
        }

        user.setTokens(tokens - premiumPrice);
        user.getCredentials().setAccountType(Credentials.AccountType.PREMIUM);

        return true;
    }
}
