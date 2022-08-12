package com.fetchrewards.points.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountingRecord {

    @NotBlank
    @NotNull
    private String payer;
    @NotNull
    private Integer points;

}
