package com.fetchrewards.points.api;

import com.fetchrewards.points.database.Database;
import com.fetchrewards.points.models.AccountingRecord;
import com.fetchrewards.points.models.Spend;
import com.fetchrewards.points.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
public final class PointsController {

    private static final String SERVER_ERROR_MESSAGE = "An unexpected error has occurred";
    private static final Logger LOGGER = Logger.getLogger(PointsController.class.getSimpleName());

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, consumes = "application/json")
    public ResponseEntity<?> addPoints(@Valid @RequestBody Transaction transaction) {
        try {
            Database.saveTransaction(transaction);

            return ResponseEntity.status(201).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());

            return ResponseEntity.internalServerError().body(SERVER_ERROR_MESSAGE);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/spend", method = {RequestMethod.POST}, consumes = "application/json")
    public ResponseEntity<?> spendPoints(@Valid @RequestBody Spend points) {
        List<AccountingRecord> results = new LinkedList<>();
        try {
            Database.spendPoints(points.getPoints())
                    .entrySet()
                    .stream()
                    .sorted((o1, o2) -> -o1.getValue().compareTo(o2.getValue()))
                    .forEach(e -> results.add(new AccountingRecord(e.getKey(), e.getValue())));

            return ResponseEntity.ok(results);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());

            return ResponseEntity.internalServerError().body(SERVER_ERROR_MESSAGE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/balance", method = {RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> points() {
        try {
            Map<String, Integer> balances = Database.getBalances();

            return ResponseEntity.ok(Database.getBalances());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());

            return ResponseEntity.internalServerError().body(SERVER_ERROR_MESSAGE);
        }
    }
}
