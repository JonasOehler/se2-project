module LMSModule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires org.apache.logging.log4j;


    opens LMSPackage to javafx.fxml, com.fasterxml.jackson.databind;
    opens LMSPackage.GuiPackage to javafx.fxml, javafx.controls;
    opens LMSPackage.BookPackage to javafx.fxml, com.fasterxml.jackson.databind;
    opens LMSPackage.UserPackage to com.fasterxml.jackson.databind, javafx.fxml;
    opens LMSPackage.HelperPackage to com.fasterxml.jackson.databind, javafx.fxml;

    exports LMSPackage;
    exports LMSPackage.GuiPackage;
    exports LMSPackage.BookPackage;
    exports LMSPackage.UserPackage;
    exports LMSPackage.HelperPackage;




}