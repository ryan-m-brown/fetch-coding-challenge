package com.fetchrewards.points.database;

import com.fetchrewards.points.models.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Database {

    private Database() {
        //do nothing
    }

    public static void saveTransaction(Transaction transaction)  {
        LocalDateTime timestamp = LocalDateTime.parse(transaction.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
        if (transaction.getPoints() > 0) {
            int credits = DataModel.CREDITS.getOrDefault(transaction.getPayer(), 0);
            int adjPoints = transaction.getPoints() + credits;

            DataModel.PAYER_POINT_RECORDS.add(new DataModel.PayerPointsRecord(transaction.getPayer(), adjPoints, timestamp));

            Integer sum = DataModel.PAYER_POINT_RECORDS.stream()
                    .filter(p -> p.getPayer().compareToIgnoreCase(transaction.getPayer()) == 0)
                    .mapToInt(DataModel.PayerPointsRecord::getPoints).sum();
            DataModel.BALANCES.put(transaction.getPayer(), sum);
        } else {
            Integer credit = DataModel.CREDITS.getOrDefault(transaction.getPayer(), 0);
            if (DataModel.CREDITS.containsKey(transaction.getPayer())) {
                DataModel.CREDITS.replace(transaction.getPayer(), credit + transaction.getPoints());
            } else {
                DataModel.CREDITS.put(transaction.getPayer(), transaction.getPoints());
            }
        }
    }

    public static Map<String, Integer> spendPoints(int points) {
        Map<String, Integer> decrements = new HashMap<>();
        for (DataModel.PayerPointsRecord p : DataModel.PAYER_POINT_RECORDS) {
            int x = p.getPoints() - points < 0 ? p.getPoints() : points;
            if (points <= 0) {
                break;
            }

            points -= x;
            if (DataModel.BALANCES.containsKey(p.getPayer())) {
                DataModel.BALANCES.replace(p.getPayer(), DataModel.BALANCES.get(p.getPayer()) - x);
            } else {
                DataModel.BALANCES.put(p.getPayer(), 0);
            }

            decrements.put(p.getPayer(), -x);
        }

        return decrements;
    }

    public static Map<String, Integer> getBalances() {
        //use a LinkedHashMap to preserve the insertion order
        Map<String, Integer> sortedBalances = new LinkedHashMap<>();
        DataModel.PAYER_POINT_RECORDS.forEach(p -> {
            if (!sortedBalances.containsKey(p.getPayer())) {
                sortedBalances.put(p.getPayer(), DataModel.BALANCES.get(p.getPayer()));
            }
        });

        return sortedBalances;
    }

}
