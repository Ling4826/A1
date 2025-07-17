package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.character.BattleMageCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

import static se233.chapter1.Launcher.getAllEquipments;

public class AllCustomHandler {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (Launcher.getEquippedWeapon() != null) {
                Launcher.getAllEquipments().add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon(null);
            }
            if (Launcher.getEquippedArmor() != null) {
                Launcher.getAllEquipments().add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor(null);
            }

            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }
    public static void onDragDetected(MouseEvent event, BasedEquipment equipment,
                                      ImageView imgView) {
         Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
         db.setDragView(imgView.getImage());
         ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
         event.consume();
        }

    public static void onDragOver(DragEvent event, String type) {
         Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = getAllEquipments();

        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(
                BasedEquipment.DATA_FORMAT);
         if (dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.
                getClass().getSimpleName().equals(type))
             event.acceptTransferModes(TransferMode.MOVE);
         }
    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();

            boolean canEquip = false;

            if (retrievedEquipment instanceof Weapon) {
                Weapon weapon = (Weapon) retrievedEquipment;

                if ((character instanceof BattleMageCharacter) ||
                        weapon.getDamageType() == character.getType()) {

                    if (Launcher.getEquippedWeapon() != null) {
                        getAllEquipments().add(Launcher.getEquippedWeapon());
                    }

                    Launcher.setEquippedWeapon(weapon);
                    character.equipWeapon(weapon);
                    canEquip = true;

                } else {
                    System.out.println("Cannot equip this weapon due to mismatched DamageType.");
                }

            } else if (retrievedEquipment instanceof Armor) {
                if (!(character instanceof BattleMageCharacter))  {

                    if (Launcher.getEquippedArmor() != null) {
                        getAllEquipments().add(Launcher.getEquippedArmor());
                    }

                    Launcher.setEquippedArmor((Armor) retrievedEquipment);
                    character.equipArmor((Armor) retrievedEquipment);
                    canEquip = true;

                } else {
                    System.out.println("Battlemage cannot equip armor.");
                }
            }

            if (canEquip) {
                ImageView imgView = new ImageView();
                if (imgGroup.getChildren().size() != 1) {
                    imgGroup.getChildren().remove(1);
                }
                lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" +
                        retrievedEquipment.getName());
                imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
                imgGroup.getChildren().add(imgView);
                dragCompleted = true;
                Launcher.setMainCharacter(character);
                Launcher.setAllEquipments(getAllEquipments());
            }

            Launcher.refreshPane();
        }

        System.out.println(dragCompleted);
        event.setDropCompleted(dragCompleted);
        event.consume();
    }


    public static void onEquipDone(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(
                BasedEquipment.DATA_FORMAT);
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                pos = i;
            }
        }
        if (pos != -1) {
            allEquipments.removeIf(equipment -> event.isAccepted() && equipment.getName().equals(retrievedEquipment.getName()));
        }
         Launcher.setAllEquipments(allEquipments);
         Launcher.refreshPane();
         }

}
