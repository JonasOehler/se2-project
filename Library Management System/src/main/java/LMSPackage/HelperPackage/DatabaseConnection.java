package LMSPackage.HelperPackage;

import LMSPackage.*;
import org.apache.logging.log4j.*;

import java.sql.*;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String url = "jdbc:sqlite:src/main/resources/databases/lms.db";


        try {
            databaseLink = DriverManager.getConnection(url);
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return databaseLink;
    }
}
