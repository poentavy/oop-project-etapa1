package implementation.pages;

import java.util.ArrayList;

public abstract class Page {
    private final ArrayList<String> subPages;
    private final boolean needsAuth;
    private final String name;

    public Page(final ArrayList<String> subPages, final boolean needsAuth, final String name) {
        this.subPages = subPages;
        this.needsAuth = needsAuth;
        this.name = name;
    }

    /**
     * Get pages to go to from here
     * @return page name list
     */
    public ArrayList<String> getSubPages() {
        return subPages;
    }

    /**
     * Needs authentication
     * @return needs auth
     */
    public boolean needsAuth() {
        return needsAuth;
    }

    /**
     * Get page name
     * @return name
     */
    public String getName() {
        return name;
    }
}
