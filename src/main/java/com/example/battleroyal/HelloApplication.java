package com.example.battleroyal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        ComboBox<String> fighter1= new ComboBox<>();
        fighter1.getItems().addAll("Archer","Mage","Warrior");
        ComboBox<String> fighter2= new ComboBox<>();
        fighter2.getItems().addAll("Archer","Mage","Warrior");
        fighter1.setValue("Mage");
        fighter2.setValue("Warrior");
        Button btn1=new Button();
        btn1.setText("Start Game");
        VBox root=new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(fighter1);
        root.getChildren().add(fighter2);
        root.getChildren().add(btn1);
        root.setStyle("-fx-background-color: #1e1e2f;");
        btn1.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        btn1.setOnAction(e-> {
    Arena a=new Arena(stage,fighter1.getValue(),fighter2.getValue());});
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Battle Royal");
        stage.setScene(scene);
        stage.show();
    }
}
