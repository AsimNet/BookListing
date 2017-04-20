package asim.net.booklisting;

/**
 * Created by asimaltwijry on 4/19/17.
 */

public class Book {
    private String title;
    private String author;
    private String url;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public Book(String title, String author, String url) {
        this.title = title;
        this.author = author;
        this.url = url;
    }
}
