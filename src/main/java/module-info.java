module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires jbcrypt;


    exports com.example.demo1;
    exports com.example.demo1.Controller;

    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Controller to javafx.fxml;

}