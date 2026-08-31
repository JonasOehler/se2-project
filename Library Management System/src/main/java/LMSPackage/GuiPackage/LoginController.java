package LMSPackage.GuiPackage;

import LMSPackage.*;
import LMSPackage.HelperPackage.DatabaseConnection;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    private static final Logger log = LogManager.getLogger(LoginController.class);
    @FXML
    private Button loginButton;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button signup;


    public void loginButtonOnAction(ActionEvent e) {
        if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
            validateLogin();
        } else {
            wrongLogin.setText("Please enter username and password.");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = ? AND password = ?";
        String usernameField = username.getText();
        String passwordField = password.getText();

        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
            statement.setString(1, usernameField);
            statement.setString(2, passwordField);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    switchToLMS();
                } else {
                    wrongLogin.setText("invalid login. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToLMS() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LMSPackage/lms.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        log.info("successfully switched to LMS");
    }

    public void switchToSignup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LMSPackage/registration.fxml"));
        Stage stage = (Stage) signup.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        log.info("successfully switched to signup");
    }
}
