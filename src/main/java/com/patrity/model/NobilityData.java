package com.patrity.model;

import com.patrity.model.lbank.LBank;
import lombok.Data;

@Data
public class NobilityData {
    public final String totalSupply;
    public final String totalHolders;
    public final Double priceChange24h;
    public final Double volume24hUSD;
    public final Double priceUSD;
    public final LBank lBank;
}
