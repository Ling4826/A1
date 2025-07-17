package se233.lambda.chapter2.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import se233.lambda.chapter2.model.CurrencyEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // [cite: 3334]

    public static List<CurrencyEntity> fetchRange(String symbol, int N) { // [cite: 3335]
        String dateEnd = LocalDate.now().format(formatter); // [cite: 3335]
        String dateStart = LocalDate.now().minusDays(N).format(formatter); // [cite: 3336]
        String urlStr = String.format("https://cmu.to/SE233currencyapi?base=THB&symbol=%s&start_date=%s&end_date=%s", symbol, dateStart, dateEnd); // [cite: 3336]
        List<CurrencyEntity> histList = new ArrayList<>(); // [cite: 3337]
        try { // [cite: 3377]
            String retrievedJson = IOUtils.toString(new URL(urlStr), Charset.defaultCharset()); // [cite: 3362]
            JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject("rates"); // [cite: 3362]
            Iterator keysToCopyIterator = jsonOBJ.keys(); // [cite: 3363]
            while (keysToCopyIterator.hasNext()) { // [cite: 3363]
                String key = (String) keysToCopyIterator.next(); // [cite: 3364]
                Double rate = Double.parseDouble(jsonOBJ.get(key).toString()); // [cite: 3364]
                histList.add(new CurrencyEntity(rate, key)); // [cite: 3364]
            } // [cite: 3365]
            histList.sort(new Comparator<CurrencyEntity>() { // [cite: 3365]
                @Override
                public int compare(CurrencyEntity o1, CurrencyEntity o2) { // [cite: 3365]
                    return o1.getTimestamp().compareTo(o2.getTimestamp()); // [cite: 3365]
                }
            }); // [cite: 3366]
        } catch (MalformedURLException e) { // [cite: 3380]
            System.err.println("Encounter a Malformed Url exception"); // [cite: 3380]
        } catch (IOException e) { // [cite: 3381]
            System.err.println("Encounter an IO exception"); // [cite: 3381]
        }
        return histList; // [cite: 3382]
    }
}
