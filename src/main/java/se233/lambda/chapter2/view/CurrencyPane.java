package se233.lambda.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.lambda.chapter2.controller.AllEventHandlers;
import se233.lambda.chapter2.controller.draw.DrawGraphTask;
import se233.lambda.chapter2.model.Currency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CurrencyPane extends BorderPane {
    private Currency currency; //
    private Button watch; // [cite: 3404]
    private Button delete; // [cite: 3535]

    public CurrencyPane(Currency currency) {
        this.watch = new Button("Watch"); // [cite: 3405]
        this.delete = new Button("Delete"); // [cite: 3537]
        this.delete.setOnAction(new EventHandler<ActionEvent>() { // [cite: 3537]
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode()); // [cite: 3537]
            }
        });
        this.watch.setOnAction(new EventHandler<ActionEvent>() { // [cite: 3553]
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode()); // [cite: 3553]
            }
        });
        this.setPadding(new Insets(0)); // [cite: 3405]
        this.setPrefSize(640, 300); // [cite: 3405]
        this.setStyle("-fx-border-color: black"); // [cite: 3405]
        try { // [cite: 3461]
            this.refreshPane(currency); // [cite: 3461]
        } catch (ExecutionException e) { // [cite: 3462]
            System.out.println("Encountered an execution exception."); // [cite: 3462]
        } catch (InterruptedException e) { // [cite: 3463]
            System.out.println("Encountered an interupted exception."); // [cite: 3463]
        }
    }

    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException { // [cite: 3464]
        this.currency = currency; // [cite: 3464]
        Pane currencyInfo = genInfoPane(); // [cite: 3465]
        FutureTask futureTask = new FutureTask<VBox>(new DrawGraphTask(currency)); // [cite: 3465]
        ExecutorService executor = Executors.newSingleThreadExecutor(); // [cite: 3465]
        executor.execute(futureTask); // [cite: 3466]
        VBox currencyGraph = (VBox) futureTask.get(); // [cite: 3466]
        Pane topArea = genTopArea(); // [cite: 3466]
        this.setTop(topArea); // [cite: 3466]
        this.setLeft(currencyInfo); // [cite: 3466]
        this.setCenter(currencyGraph); // [cite: 3467]
    }

    private Pane genInfoPane() { // [cite: 3408]
        VBox currencyInfoPane = new VBox(10); // [cite: 3409]
        currencyInfoPane.setPadding(new Insets(5, 25, 5, 25)); // [cite: 3409]
        currencyInfoPane.setAlignment(Pos.CENTER); // [cite: 3409]
        Label exchangeString = new Label(""); // [cite: 3410]
        Label watchString = new Label(""); // [cite: 3410]
        exchangeString.setStyle("-fx-font-size: 20;"); // [cite: 3410]
        watchString.setStyle("-fx-font-size: 14;"); // [cite: 3411]
        if (this.currency != null) { // [cite: 3411]
            exchangeString.setText(String.format("%s: %.4f", this.currency.getShortCode(), this.currency.getCurrent().getRate())); // [cite: 3411]
            if (this.currency.getWatch() == true) { // [cite: 3412]
                watchString.setText(String.format("(Watch @%.4f)", this.currency.getWatchRate())); // [cite: 3412]
            }
        }
        currencyInfoPane.getChildren().addAll(exchangeString, watchString); // [cite: 3412]
        return currencyInfoPane; // [cite: 3413]
    }

    private HBox genTopArea() { // [cite: 3413]
        HBox topArea = new HBox(10); // [cite: 3414]
        topArea.setPadding(new Insets(5)); // [cite: 3414]
        topArea.getChildren().addAll(watch, delete); // [cite: 3539]
        ((HBox) topArea).setAlignment(Pos.CENTER_RIGHT); // [cite: 3414]
        return topArea; // [cite: 3414]
    }
}
