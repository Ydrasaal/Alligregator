package ydrasaal.alligregator.data;

/**
 * Created by Léo on 12/03/2016.
 */
public class MenuItem {

    private String  title;
    private boolean isSeparator;

    public MenuItem(String title, boolean isSeparator) {
        this.title = title;
        this.isSeparator = isSeparator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public void setIsSeparator(boolean isSeparator) {
        this.isSeparator = isSeparator;
    }
}
