package ydrasaal.alligregator.data;

/**
 * Created by Léo on 12/03/2016.
 *
 * Data object for navigation drawer adapter
 */
public class MenuItem {

    private int position = 5;
    private String  title;
    private String  url;
    private int     iconId = 0;
    private boolean isHeader;

    public MenuItem(String title, boolean isHeader, String url) {
        this(title, 0, isHeader, url);
    }

    public MenuItem(String title, int iconId, boolean isHeader, String url) {
        this.title = title;
        this.iconId = iconId;
        this.isHeader = isHeader;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public String getUrl() {
        return url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
