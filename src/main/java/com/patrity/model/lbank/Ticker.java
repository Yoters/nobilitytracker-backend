package com.patrity.model.lbank;

import lombok.Data;

@Data
public class Ticker{
    private final String high;
    private final String vol;
    private final String low;
    private final String change;
    private final String turnover;
    private final String latest;
}

