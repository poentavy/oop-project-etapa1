package implementation.user;

public final class Credentials {
    public enum AccountType {
        STANDARD,
        PREMIUM
    }

    private final String name;
    private final String password;
    private AccountType accountType;
    private final String country;
    private int balance;

    public Credentials(final String name, final String password, final AccountType accountType,
                       final String country, final int balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getCountry() {
        return country;
    }

    public int getBalance() {
        return balance;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }
}
