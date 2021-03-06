
package ydrasaal.alligregator.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadEntry {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("publishedDate")
    @Expose
    private Date publishedDate;
    @SerializedName("contentSnippet")
    @Expose
    private String contentSnippet;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

//    /**
//     *
//     * @return
//     *     The publishedDate
//     */
//    public String getPublishedDate() {
//        return publishedDate;
//    }
//
//    /**
//     *
//     * @param publishedDate
//     *     The publishedDate
//     */
//    public void setPublishedDate(String publishedDate) {
//        this.publishedDate = publishedDate;
//    }


    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * 
     * @return
     *     The contentSnippet
     */
    public String getContentSnippet() {
        return contentSnippet;
    }

    /**
     * 
     * @param contentSnippet
     *     The contentSnippet
     */
    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The categories
     */
    public List<Object> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
