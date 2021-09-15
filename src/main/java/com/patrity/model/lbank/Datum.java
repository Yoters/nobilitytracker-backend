package com.patrity.model.lbank;


import lombok.Data;

@Data
public class Datum{
    public final String symbol;
    public final Ticker ticker;
    public final long timestamp;
}