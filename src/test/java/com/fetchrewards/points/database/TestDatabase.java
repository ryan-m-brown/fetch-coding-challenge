package com.fetchrewards.points.database;


import com.fetchrewards.points.exceptions.NoDataException;
import com.fetchrewards.points.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestDatabase {

    @Test
    public void testAdd() throws NoDataException {
        Transaction trans = new Transaction("DANNON", 1000, "2020-11-02T14:00:00Z");
        Database.saveTransaction(trans);

        Assert.assertEquals(1, DataModel.PAYER_POINT_RECORDS.size());
        Assert.assertEquals(1000, (int) Database.getBalances().get("DANNON"));
    }

    @Test
    public void testSpend() throws NoDataException {
        Transaction first = new Transaction("DANNON", 1000, "2020-11-02T14:00:00Z");
        Transaction second = new Transaction("UNILEVER", 200, "2020-10-31T11:00:00Z");
        Transaction third = new Transaction("DANNON", -200, "2020-10-31T15:00:00Z");
        Transaction fourth = new Transaction("MILLER COORS", 10000, "2020-11-01T14:00:00Z");
        Transaction fifth = new Transaction("DANNON", 300, "2020-10-31T10:00:00Z");

        Database.saveTransaction(first);
        Database.saveTransaction(second);
        Database.saveTransaction(third);
        Database.saveTransaction(fourth);
        Database.saveTransaction(fifth);

        Map<String, Integer> decrements = Database.spendPoints(5000);


        Assert.assertEquals(3, decrements.size());
        Assert.assertEquals(-100, (int) decrements.get("DANNON"));
        Assert.assertEquals(-4700, (int) decrements.get("MILLER COORS"));
        Assert.assertEquals(-200, (int) decrements.get("UNILEVER"));
    }

    @Test
    public void testBalance() throws NoDataException {
        Transaction first = new Transaction("DANNON", 1000, "2020-11-02T14:00:00Z");
        Transaction second = new Transaction("UNILEVER", 200, "2020-10-31T11:00:00Z");
        Transaction third = new Transaction("DANNON", -200, "2020-10-31T15:00:00Z");
        Transaction fourth = new Transaction("MILLER COORS", 10000, "2020-11-01T14:00:00Z");
        Transaction fifth = new Transaction("DANNON", 300, "2020-10-31T10:00:00Z");

        Database.saveTransaction(first);
        Database.saveTransaction(second);
        Database.saveTransaction(third);
        Database.saveTransaction(fourth);
        Database.saveTransaction(fifth);

        Database.spendPoints(5000);

        Assert.assertEquals(1000, (int) Database.getBalances().get("DANNON"));
        Assert.assertEquals(0, (int) Database.getBalances().get("UNILEVER"));
        Assert.assertEquals(5300, (int) Database.getBalances().get("MILLER COORS"));
    }

}
