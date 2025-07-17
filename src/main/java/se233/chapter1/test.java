package se233.chapter1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se233.chapter1.controller.AllCustomHandler;
import se233.chapter1.controller.GenCharacter;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

import static se233.chapter1.Launcher.getAllEquipments;

public class test extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane1 = new BorderPane();
        Pane mainPane = mainPane1;
        mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Hello World!");
        Button genCharacter = new Button("Button");
        genCharacter.setText("Hello");

        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
