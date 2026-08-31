package LMSPackage.UserPackage;

import LMSPackage.*;
import LMSPackage.BookPackage.Book;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.*;

import java.util.ArrayList;

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private ArrayList <Book> issuedBooks;


    @JsonCreator
    public User(@JsonProperty("id") String id,@JsonProperty("firstname") String firstname,@JsonProperty("lastname") String lastname,@JsonProperty("issuedBooks") ArrayList<Book> issuedBooks) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.issuedBooks = issuedBooks;

    }
    public String getId() { return id; }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(ArrayList<Book> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", issuedBooks=" + issuedBooks +
                '}';
    }


}
