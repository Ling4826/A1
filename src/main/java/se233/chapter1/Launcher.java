package se233.chapter1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.chapter1.controller.GenCharacter;
import se233.chapter1.controller.GenItemList;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;
import se233.chapter1.view.CharacterPane;
import se233.chapter1.view.EquipPane;
import se233.chapter1.view.InventoryPane;

import java.util.ArrayList;

public class Launcher extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    private static Scene mainScene;
    private static BasedCharacter mainCharacter = null;
    private static ArrayList<BasedEquipment> allEquipments = null;

    public static ArrayList<BasedEquipment> getAllEquipments() {
        return allEquipments;
    }

    public static void setAllEquipments(ArrayList<BasedEquipment> equipments) {
        allEquipments = equipments;
    }

    private static Weapon equippedWeapon = null;
    private static Armor equippedArmor = null;

    public static void setEquippedWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    public static Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public static void setEquippedArmor(Armor armor) {
        equippedArmor = armor;
    }

    public static Armor getEquippedArmor() {
        return equippedArmor;
    }

    private static CharacterPane characterPane = null;
    private static EquipPane equipPane = null;
    private static InventoryPane inventoryPane = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chapter1");
        primaryStage.setResizable(false);
        primaryStage.show();
        mainCharacter = GenCharacter.setUpCharacter();
        allEquipments = GenItemList.setUpItemList();

        Pane mainPane = getMainPane();
        mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
    }
    public Pane getMainPane() {
        BorderPane mainPane = new BorderPane();
        characterPane = new CharacterPane();
        equipPane = new EquipPane();
        inventoryPane = new InventoryPane();
        refreshPane();
        mainPane.setCenter(characterPane);
        mainPane.setLeft(equipPane);
        mainPane.setBottom(inventoryPane);
        return mainPane;
    }

    public static void refreshPane() {
        if (characterPane != null) {
            characterPane.drawPane(mainCharacter);
        }
        if (equipPane != null) {
            equipPane.drawPane(equippedWeapon, equippedArmor);
        }
        if (inventoryPane != null) {
            inventoryPane.drawPane(allEquipments);
        }
    }

    public static BasedCharacter getMainCharacter() {
        return mainCharacter;
    }

    public static void setMainCharacter(BasedCharacter mainCharacter) {
        Launcher.mainCharacter = mainCharacter;
    }


}