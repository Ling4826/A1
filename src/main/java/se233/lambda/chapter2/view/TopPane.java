package se233.lambda.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se233.lambda.chapter2.controller.AllEventHandlers;

import java.time.LocalDateTime;

public class TopPane extends FlowPane {
    private Button refresh; // [cite: 3399]
    private Button add; // [cite: 3519]
    private Label update; // [cite: 3399]

    public TopPane() {
        this.setPadding(new Insets(10)); // [cite: 3400]
        this.setHgap(10); // [cite: 3400]
        this.setPrefSize(640, 20); // [cite: 3400]
        add = new Button("Add"); // [cite: 3521]
        refresh = new Button("Refresh"); // [cite: 3401]
        refresh.setOnAction(new EventHandler<ActionEvent>() { // [cite: 3475]
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onRefresh(); // [cite: 3475]
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() { // [cite: 3523]
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onAdd(); // [cite: 3523]
            }
        });
        update = new Label(); // [cite: 3401]
        refreshPane(); // [cite: 3401]
        this.getChildren().addAll(refresh, add, update); // [cite: 3524]
    }

    public void refreshPane() { // [cite: 3401]
        update.setText(String.format("Last update: %s", LocalDateTime.now().toString())); // [cite: 3401]
    }
}
