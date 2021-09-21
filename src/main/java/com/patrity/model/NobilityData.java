package com.patrity.model;

import com.patrity.model.lbank.LBank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NobilityData {
    public final String totalSupply;
    public final String totalHolders;
    public final BigDecimal priceChange24h;
    public final BigDecimal priceChange7d;
    public final BigDecimal priceChange30d;
    public final BigDecimal priceChange60d;
    public final BigDecimal priceChange90d;
    public final BigDecimal volume24hUSD;
    public final BigDecimal priceUSD;
    public final LBank lBank;
}
