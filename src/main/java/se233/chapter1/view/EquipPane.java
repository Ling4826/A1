package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import static se233.chapter1.Launcher.getAllEquipments;
import static se233.chapter1.controller.AllCustomHandler.onDragDropped;
import static se233.chapter1.controller.AllCustomHandler.onDragOver;

// Assuming Weapon and Armor classes are defined elsewhere with getName() and getImagePath() methods
// Assuming Launcher class is defined elsewhere with getResource() method

public class EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public EquipPane() {

    }



    private Pane getDetailsPane() {
        Pane equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        ((VBox) equipmentInfoPane).setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));

        Label weaponLbl = new Label();
        Label armorLbl = new Label();
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();
        ImageView armorImg = new ImageView();
        ImageView weaponImg = new ImageView();
        bg1.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        bg2.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        weaponImgGroup.getChildren().add(bg1);
        armorImgGroup.getChildren().add(bg2);

        if (equippedWeapon != null) {
            weaponLbl.setText("Weapon: \n" + equippedWeapon.getName());
            weaponImg.setImage(new Image(Launcher.class.getResource(equippedWeapon.getImagepath()).toString()));

            weaponImgGroup.getChildren().add(weaponImg);
        } else {
            weaponLbl.setText("Weapon: ");
            weaponImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));

            weaponImgGroup.getChildren().add(weaponImg);
        }

        if (equippedArmor != null) {
            armorLbl.setText("Armor: \n" + equippedArmor.getName());
            armorImg.setImage(new Image(Launcher.class.getResource(equippedArmor.getImagepath()).toString()));

            armorImgGroup.getChildren().add(armorImg);
        } else {
            armorLbl.setText("Armor: ");
            armorImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));

            armorImgGroup.getChildren().add(armorImg);
        }

        weaponImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                onDragOver(e, "Weapon");
            }
        });
        weaponImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                System.out.println("🎯 Dropped on weaponImgGroup!");
                onDragDropped(e, weaponLbl, weaponImgGroup);

            }
        });
        armorImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                onDragOver(e, "Armor");
            }
        });
        armorImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) {
                onDragDropped(e, armorLbl, armorImgGroup);

            }
        });
        Button unequipAllBtn = new Button("Unequip All");
        unequipAllBtn.setOnAction(e -> {
            BasedCharacter character = Launcher.getMainCharacter();

            if (Launcher.getEquippedWeapon() != null) {
                character.unequipWeapon();
                Launcher.getAllEquipments().add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon(null);
            }

            if (Launcher.getEquippedArmor() != null) {
                character.unequipArmor();
                Launcher.getAllEquipments().add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor(null);
            }

            Launcher.refreshPane();
        });


        equipmentInfoPane.getChildren().add(unequipAllBtn);


        equipmentInfoPane.getChildren().addAll(weaponLbl,weaponImgGroup,armorLbl,
                armorImgGroup);

        return equipmentInfoPane;

    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color: Red;"); // Note: original code has a typo "Red;" instead of "red;" or "#FF0000;"
        this.setContent(equipmentInfo);
    }
}