module com.example.battleroyal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.battleroyal to javafx.fxml;
    exports com.example.battleroyal;
}