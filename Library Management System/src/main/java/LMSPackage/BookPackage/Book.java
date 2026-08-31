package LMSPackage.BookPackage;


import LMSPackage.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.*;

import java.util.Objects;

public class Book {
    private static final Logger log = LogManager.getLogger(Book.class);

    private String isbn;
    private String title;
    private String author;
    private String issued;


    @JsonCreator
    public Book(@JsonProperty("isbn") String isbn, @JsonProperty("title") String title, @JsonProperty ("author") String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.issued = EnumIssued.No.toString();
    }

    public String getISBN() { return isbn; }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIssued() { return issued; }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", issued='" + issued + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(issued, book.issued);
    }

}
