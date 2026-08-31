package LMSPackage.BookPackage;

import java.io.*;
import java.util.*;

import LMSPackage.*;
import LMSPackage.HelperPackage.FileSaverInterface;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.*;


public class BookManager implements FileSaverInterface {
    private  ArrayList<Book> booklist;

    private static BookManager instance = null;
    @JsonCreator
    private BookManager(@JsonProperty("booklist") ArrayList<Book> booklist) {
        this.booklist = booklist;

    }

    public ArrayList<Book> getBooklist() {
        return booklist;
    }

    public static BookManager getInstance() {
        if (instance == null)
            instance = new BookManager(new ArrayList<>());
        return instance;
    }

    public Book selectedBook(String isbn) {
        Optional<Book> searchedBook = Optional.ofNullable(booklist.parallelStream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null));

        if (searchedBook.isEmpty()) {
            return null;

        } else {
            return searchedBook.get();
        }
    }

    public boolean addBook(Book book) {

        if (getInstance().selectedBook(book.getISBN()) != null) {
            return false;
        } else {
            booklist.add(book);
            return true;
        }
    }


    @Override
    public String toString() {
        return "BookManager{" +
                "booklist=" + booklist +
                '}';
    }

    @Override
    public synchronized BookManager loadJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            instance = mapper.readValue(new File("./src/main/resources/jsonFiles/saveDataBook.json"), BookManager.class);
            return instance;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public synchronized String saveJsonFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("./src/main/resources/jsonFiles/saveDataBook.json"), getInstance());

            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getInstance());
            return jsonInString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void showList(ListView<Book> listView,ArrayList<Book> arrayList, ObservableList<Book> observableList) {
        listView.getItems().clear();
        for (int i = 0; i < arrayList.size(); i++) {
            observableList.add(arrayList.get(i));
        }
        listView.setItems(observableList);
        listView.refresh();
    }

}
