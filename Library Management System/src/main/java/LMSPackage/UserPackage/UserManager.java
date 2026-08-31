package LMSPackage.UserPackage;

import LMSPackage.*;
import LMSPackage.BookPackage.*;
import LMSPackage.HelperPackage.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserManager implements FileSaverInterface {
    private static final Logger log = LogManager.getLogger(UserManager.class);

    private static UserManager instance = null;
    private ArrayList<User> userlist;

    public ArrayList<User> getUserlist() {
        return userlist;
    }

    @JsonCreator
    private UserManager(@JsonProperty("userlist") ArrayList<User> userlist) {
        this.userlist = userlist;

    }

    public void editBookOfUser(String isbn, String author, String title) {
        Book searchedBook;
        for (int i = 0; i < userlist.size(); i++) {
            searchedBook = (userlist.get(i).getIssuedBooks().parallelStream()
                    .filter(book -> book.getISBN().equals(isbn))
                    .findFirst()
                    .orElse(null));
            if (searchedBook != null) {
                searchedBook.setIsbn(isbn);
                searchedBook.setAuthor(author);
                searchedBook.setTitle(title);
                Thread thread9 = new Thread(new WriteThread(EnumWrite.WRITEUSER));
                thread9.start();
                break;
            } else {
                log.debug("no book was found");
            }
        }
    }
    public void deleteBookOfUser(String isbn) {
        Book searchedBook;
        for (int i = 0; i < userlist.size(); i++) {
            searchedBook = (userlist.get(i).getIssuedBooks().parallelStream()
                    .filter(book -> book.getISBN().equals(isbn))
                    .findFirst()
                    .orElse(null));
            if (searchedBook != null) {
                userlist.get(i).getIssuedBooks().remove(searchedBook);
                break;
            } else {
                log.debug("no book was found");
            }

        }
    }

    public static synchronized UserManager getInstance() {
        if (instance == null)
            instance = new UserManager(new ArrayList<>());
        return instance;
    }

    public User checkIfUserExists(String id) {
        Optional<User> searchedUser = Optional.ofNullable(userlist.parallelStream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null));

        if (searchedUser.isEmpty()) {
            return null;

        } else {
            return searchedUser.get();
        }
    }

    public boolean addUser(User user) {

        if (getInstance().checkIfUserExists(user.getId()) != null) {
            return false;
        } else {
            userlist.add(user);
            return true;
        }
    }

    @Override
    public String toString() {
        return "UserManager{" +
                "userlist=" + userlist +
                '}';
    }

    @Override
    public synchronized Object loadJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            instance = mapper.readValue(new File("./src/main/resources/jsonFiles/saveDataUser.json"), UserManager.class);
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
            mapper.writeValue(new File("./src/main/resources/jsonFiles/saveDataUser.json"), getInstance());

            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getInstance());
            return jsonInString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void showList(ListView<User> listView, ArrayList<User> arrayList, ObservableList<User> observableList) {
        listView.getItems().clear();
        for (int i = 0; i < arrayList.size(); i++) {
            observableList.add(arrayList.get(i));
        }
        listView.setItems(observableList);
        listView.refresh();
    }

}
