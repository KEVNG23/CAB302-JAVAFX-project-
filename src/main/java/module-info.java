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

}