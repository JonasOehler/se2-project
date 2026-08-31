package LMSPackage;


import LMSPackage.HelperPackage.ReadThread;
import LMSPackage.UserPackage.User;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.*;
import java.io.IOException;
import java.util.logging.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static User user;

    public static User getSelectedUser() {
        return user;
    }

    public static void setSelectedUser(User selectedUser) {
        Main.user = selectedUser;
    }


    @Override
    public void start(Stage stage) throws IOException {
        log.debug("started program");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
        Thread thread0 = new Thread(new ReadThread());
        thread0.start();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }




    public static void main(String[] args){
        launch();
    }
}