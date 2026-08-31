package LMSPackage.GuiPackage;

import LMSPackage.BookPackage.Book;
import LMSPackage.BookPackage.BookManager;
import LMSPackage.BookPackage.EnumIssued;
import LMSPackage.HelperPackage.EnumWrite;
import LMSPackage.HelperPackage.WriteThread;
import LMSPackage.Main;
import LMSPackage.UserPackage.User;
import LMSPackage.UserPackage.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.util.ArrayList;


public class LMSController {
    private static final Logger log = LogManager.getLogger(LMSController.class);

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private BorderPane borderpane;
    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ListView<User> listViewUserList;
    @FXML
    private ListView<Book> listViewBookList;


    ObservableList<Book> oListBook = FXCollections.observableArrayList();
    ObservableList<User> oListUser = FXCollections.observableArrayList();
    BookManager bookmanager = BookManager.getInstance();
    UserManager usermanager = UserManager.getInstance();

    @FXML
    private void loadScene(ActionEvent event) throws IOException {
        String buttonID = ((Control) event.getSource()).getId();
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/LMSPackage/" + buttonID + ".fxml"));
            borderpane.setCenter(view);
        }catch (NullPointerException exception){
            log.error("failed to load: " + buttonID);
        }
    }

    public static Long getLongFromTextField(TextField textField) {
        String text = textField.getText();
        return Long.parseLong(text);
    }

    @FXML
    private void createBook(ActionEvent event) throws IOException {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        try {
            getLongFromTextField(isbnField);
            Book createdBook = new Book(isbn, title, author);
            BookManager.getInstance().addBook(createdBook);
            log.debug("book" + createdBook + "was created");

            Thread thread1 = new Thread(new WriteThread(EnumWrite.WRITEBOOK));
            thread1.start();
            log.info("created book was saved to json");

            showBookList();
        } catch (NumberFormatException exception) {
            log.error("isbn is not a number");
        }
    }

    @FXML
    private void createUser(ActionEvent event) throws IOException {
        String id = idField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        try {
            getLongFromTextField(idField);
            User createdUser = new User(id, firstName, lastName, new ArrayList<>());
            UserManager.getInstance().addUser(createdUser);
            log.debug("user" + createdUser + "was created");

            Thread thread4 = new Thread(new WriteThread(EnumWrite.WRITEUSER));
            thread4.start();
            log.info("created user was saved to json");

            showUserList();
        } catch (NumberFormatException exception) {
            log.error("id is not a number");
        }
    }

    @FXML
    private void deleteBook(ActionEvent event) throws IOException {
        try {
            int selectedIndex = listViewBookList.getSelectionModel().getSelectedIndex();
            Book selectedBook = bookmanager.getBooklist().get(selectedIndex);
            bookmanager.getBooklist().remove(selectedIndex);
            listViewBookList.getItems().remove(selectedIndex);

            Thread thread2 = new Thread(new WriteThread(EnumWrite.WRITEBOOK));
            thread2.start();
            usermanager.deleteBookOfUser(selectedBook.getISBN());
            showBookList();

        } catch (IndexOutOfBoundsException exception){
            log.error("nothing was selected");
        }
    }

    @FXML
    private void editBook(ActionEvent event) throws IOException {

        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        try {
            int selectedIndex = listViewBookList.getSelectionModel().getSelectedIndex();
            Book selectedBook = bookmanager.getBooklist().get(selectedIndex);
            log.debug("selected: " + selectedBook);

            usermanager.editBookOfUser(selectedBook.getISBN(), selectedBook.getAuthor(), selectedBook.getTitle());
            selectedBook.setIsbn(isbn);
            selectedBook.setAuthor(author);
            selectedBook.setTitle(title);


            Thread thread3 = new Thread(new WriteThread(EnumWrite.WRITEBOOK));
            thread3.start();

            showBookList();

        }catch (IndexOutOfBoundsException exception){
            log.error("nothing was selected");
        }
    }

    public void handle(ActionEvent event) {
        String buttonID = ((Control) event.getSource()).getId();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/LMSPackage/" + buttonID + ".fxml"));
            log.debug("load: " + buttonID);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            log.info("finished loading");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectUser(ActionEvent event) throws IOException {
        boolean check = true;
        try {
            int selectedIndex = listViewUserList.getSelectionModel().getSelectedIndex();
            Main.setSelectedUser(UserManager.getInstance().getUserlist().get(selectedIndex));
        } catch (IndexOutOfBoundsException exception) {
            check = false;
            log.error("nothing was selected");
        }
        if (check == true) {
            handle(event);
        }
    }

    @FXML
    private void issueBook(ActionEvent event) throws IOException {
        try{
            int selectedIndexBook = listViewBookList.getSelectionModel().getSelectedIndex();
            Book selectedBook = bookmanager.getBooklist().get(selectedIndexBook);
            ArrayList<Book> arrayListOfSelectedUser = Main.getSelectedUser().getIssuedBooks();

            if (selectedBook.getIssued().equals("No")) {
                arrayListOfSelectedUser.add(selectedBook);
                Main.getSelectedUser().setIssuedBooks(arrayListOfSelectedUser);
                selectedBook.setIssued(EnumIssued.Yes.toString());
            } else {
                log.debug("book is already issued");
            }

            Thread thread5 = new Thread(new WriteThread(EnumWrite.WRITEUSER));
            thread5.start();
            log.info("book was assigned to user");
            Thread thread6 = new Thread(new WriteThread(EnumWrite.WRITEBOOK));
            thread6.start();
            log.info("issued state was updated to Yes");

            showBookList();
        }catch (IndexOutOfBoundsException exception){
            log.error("nothing was selected");
        }
    }

    @FXML
    private void returnBook(ActionEvent event) throws IOException {
        try{
            int selectedIndex = listViewBookList.getSelectionModel().getSelectedIndex();
            Book selectedBook = Main.getSelectedUser().getIssuedBooks().get(selectedIndex);
            log.debug("select: " + selectedBook);

            Book bookToChange = bookmanager.selectedBook(selectedBook.getISBN());
            bookToChange.setIssued(EnumIssued.No.toString());

            ArrayList<Book> arrayListOfSelectedUser = Main.getSelectedUser().getIssuedBooks();
            arrayListOfSelectedUser.remove(selectedIndex);
            Main.getSelectedUser().setIssuedBooks(arrayListOfSelectedUser);

            Thread thread7 = new Thread(new WriteThread(EnumWrite.WRITEUSER));
            thread7.start();
            log.info("book was returned");
            Thread thread8 = new Thread(new WriteThread(EnumWrite.WRITEBOOK));
            thread8.start();
            log.info("issue state was updated to No");
            showBooksOfUser();
        }catch (IndexOutOfBoundsException exception){
            log.error("nothing was selected");
        }

    }


    @FXML
    private void showBookList() {
        bookmanager.showList(listViewBookList, bookmanager.getBooklist(), oListBook);
    }

    @FXML
    private void showUserList() {
        usermanager.showList(listViewUserList, usermanager.getUserlist(), oListUser);
    }

    @FXML
    private void showBooksOfUser() {
        bookmanager.showList(listViewBookList, Main.getSelectedUser().getIssuedBooks(), oListBook);
    }
}

