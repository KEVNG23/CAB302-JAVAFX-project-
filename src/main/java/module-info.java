module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires jbcrypt;
    requires java.sql;


    exports com.example.demo1;
    exports com.example.demo1.Controller;

    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Controller to javafx.fxml;
    exports com.example.demo1.Models;
    opens com.example.demo1.Models to javafx.fxml;

    exports com.example.demo1.Calendar;
    opens com.example.demo1.Calendar to javafx.fxml;

    exports com.example.demo1.AccountController;
    opens com.example.demo1.AccountController to javafx.fxml;
    exports com.example.demo1.AccountModel;
    opens com.example.demo1.AccountModel to javafx.fxml;

}