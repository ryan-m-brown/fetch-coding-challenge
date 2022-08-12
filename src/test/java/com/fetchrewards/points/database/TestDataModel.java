package com.fetchrewards.points.database;


import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;

public class TestDataModel {

    @Test
    public void testSortOrder() {
        SortedSet<DataModel.PayerPointsRecord> payerPointRecords = DataModel.PAYER_POINT_RECORDS;

        DataModel.PayerPointsRecord top = new DataModel.PayerPointsRecord("DANNON", 300, LocalDateTime.parse("2020-10-31T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME));
        payerPointRecords.add(new DataModel.PayerPointsRecord("UNILEVER", 200, LocalDateTime.parse("2020-10-31T11:00:00Z", DateTimeFormatter.ISO_DATE_TIME)));
        payerPointRecords.add(new DataModel.PayerPointsRecord("MILLER COORS", 10000, LocalDateTime.parse("2020-11-01T14:00:00Z", DateTimeFormatter.ISO_DATE_TIME)));
        payerPointRecords.add(top);

        DataModel.PayerPointsRecord first = payerPointRecords.stream().findFirst().get();

        Assert.assertEquals(0, first.compareTo(top));
    }

}
