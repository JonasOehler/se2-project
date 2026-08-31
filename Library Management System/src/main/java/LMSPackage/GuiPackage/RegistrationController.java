package LMSPackage.GuiPackage;

import LMSPackage.*;
import LMSPackage.HelperPackage.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.sql.*;


public class RegistrationController {
    private static final Logger log = LogManager.getLogger(RegistrationController.class);
    @FXML
    private Button signUpButton;
    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Label errorMessage;

    public void registerButtonOnAction(ActionEvent e) throws IOException {
        if (password.getText().equals(confirmPassword.getText())) {
            registerUser();
            switchToLogin();
        } else {
            errorMessage.setText("Password does not match");
        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        log.info("connected to database");

        String insertFields = "INSERT INTO users(username, password, name) VALUES(?, ?, ?)";
        String usernameField = username.getText();
        String passwordField = password.getText();
        String nameField = name.getText();

        try {
            PreparedStatement statement = connectDB.prepareStatement(insertFields, new String[]{"ident"});
            statement.setString(1, usernameField);
            statement.setString(2, passwordField);
            statement.setString(3, nameField);
            statement.executeUpdate();
            log.info("user was created");
        } catch (SQLException exception) {
            exception.printStackTrace();
            exception.getCause();
            log.debug(exception);
        }
    }
    public void switchToLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LMSPackage/login.fxml"));
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        log.info("successfully switched to login");
    }
}
