module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires jbcrypt;
    requires java.sql;
    requires java.prefs;


    exports com.example.demo1;
    exports com.example.demo1.HomePageController;

    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.HomePageController to javafx.fxml;

    exports com.example.demo1.Models;
    opens com.example.demo1.Models to javafx.fxml;

    exports com.example.demo1.Calendar;
    opens com.example.demo1.Calendar to javafx.fxml;

    exports com.example.demo1.AccountController;
    opens com.example.demo1.AccountController to javafx.fxml;
    exports com.example.demo1.AccountModel;
    opens com.example.demo1.AccountModel to javafx.fxml;

    exports com.example.demo1.Timer;
    opens com.example.demo1.Timer to javafx.fxml;
    exports com.example.demo1.Profile;
    opens com.example.demo1.Profile to javafx.fxml;

}