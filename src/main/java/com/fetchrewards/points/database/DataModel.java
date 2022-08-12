package com.fetchrewards.points.database;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

final class DataModel {

    @ToString
    @Getter
    @RequiredArgsConstructor
    static class PayerPointsRecord implements Comparable<PayerPointsRecord> {

        @EqualsAndHashCode.Include
        @NotBlank
        @NotNull
        private final String payer;

        @EqualsAndHashCode.Exclude
        @NotNull
        private final Integer points;
        @EqualsAndHashCode.Include
        @NotNull
        private final LocalDateTime timestamp;

        @Override
        public int compareTo(PayerPointsRecord o) {
            return this.timestamp.compareTo(o.timestamp);
        }
    }

    static final SortedSet<PayerPointsRecord> PAYER_POINT_RECORDS = new TreeSet<>();
    static final Map<String, Integer> BALANCES = new HashMap<>();
    static final Map<String, Integer> CREDITS = new HashMap<>();

    private DataModel(){
        //do nothing
    }
}
