package se233.lambda.chapter2.controller;

import javafx.scene.control.TextInputDialog;
import se233.lambda.chapter2.Launcher;
import se233.lambda.chapter2.model.Currency;
import se233.lambda.chapter2.model.CurrencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    public static void onRefresh() { //
        try {
            Launcher.refreshPane(); //
        } catch (Exception e) { // [cite: 3474]
            e.printStackTrace(); // [cite: 3474]
        }
    }

    public static void onAdd() { // [cite: 3513]
        try {
            TextInputDialog dialog = new TextInputDialog(); // [cite: 3514]
            dialog.setTitle("Add Currency"); // [cite: 3514]
            dialog.setContentText("Currency code:"); // [cite: 3514]
            dialog.setHeaderText(null); // [cite: 3514]
            dialog.setGraphic(null); // [cite: 3514]
            Optional<String> code = dialog.showAndWait(); // [cite: 3515]
            if (code.isPresent()) { // [cite: 3515]
                List<Currency> currencyList = Launcher.getCurrencyList(); // [cite: 3515]
                Currency c = new Currency(code.get()); // [cite: 3516]
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 8); // [cite: 3516]
                c.setHistorical(cList); // [cite: 3516]
                c.setCurrent(cList.get(cList.size() - 1)); // [cite: 3516]
                currencyList.add(c); // [cite: 3516]
                Launcher.setCurrencyList(currencyList); // [cite: 3517]
                Launcher.refreshPane(); // [cite: 3517]
            }
        } catch (InterruptedException | ExecutionException e) { // [cite: 3517, 3518]
            e.printStackTrace(); // [cite: 3518]
        }
    }

    public static void onDelete(String code) { // [cite: 3530]
        try {
            List<Currency> currencyList = Launcher.getCurrencyList(); // [cite: 3530]
            int index = -1; // [cite: 3531]
            for (int i = 0; i < currencyList.size(); i++) { // [cite: 3531]
                if (currencyList.get(i).getShortCode().equals(code)) { // [cite: 3531]
                    index = i; // [cite: 3532]
                    break; // [cite: 3532]
                }
            }
            if (index != -1) { // [cite: 3532]
                currencyList.remove(index); // [cite: 3533]
                Launcher.setCurrencyList(currencyList); // [cite: 3533]
                Launcher.refreshPane(); // [cite: 3533]
            }
        } catch (InterruptedException | ExecutionException e) { // [cite: 3533, 3534]
            e.printStackTrace(); // [cite: 3534]
        }
    }

    public static void onWatch(String code) { // [cite: 3544]
        try {
            List<Currency> currencyList = Launcher.getCurrencyList(); // [cite: 3544]
            int index = -1; // [cite: 3545]
            for (int i = 0; i < currencyList.size(); i++) { // [cite: 3545]
                if (currencyList.get(i).getShortCode().equals(code)) { // [cite: 3545]
                    index = i; // [cite: 3546]
                    break; // [cite: 3546]
                }
            }
            if (index != -1) { // [cite: 3546]
                TextInputDialog dialog = new TextInputDialog(); // [cite: 3547]
                dialog.setTitle("Add Watch"); // [cite: 3547]
                dialog.setContentText("Rate:"); // [cite: 3547]
                dialog.setHeaderText(null); // [cite: 3547]
                dialog.setGraphic(null); // [cite: 3547]
                Optional<String> retrievedRate = dialog.showAndWait(); // [cite: 3548]
                if (retrievedRate.isPresent()) { // [cite: 3548]
                    double rate = Double.parseDouble(retrievedRate.get()); // [cite: 3548]
                    currencyList.get(index).setWatch(true); // [cite: 3548]
                    currencyList.get(index).setWatchRate(rate); // [cite: 3548]
                    Launcher.setCurrencyList(currencyList); // [cite: 3549]
                    Launcher.refreshPane(); // [cite: 3549]
                }
            }
        } catch (InterruptedException | ExecutionException e) { // [cite: 3550, 3551]
            e.printStackTrace(); // [cite: 3551]
        }
    }
}
