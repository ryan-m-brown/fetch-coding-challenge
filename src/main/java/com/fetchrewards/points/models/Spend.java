package com.fetchrewards.points.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
public class Spend {

    @Min(0)
    private Integer points;

}
