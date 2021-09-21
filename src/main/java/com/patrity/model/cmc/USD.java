package com.patrity.model.cmc;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class USD{
    public BigDecimal price;
    public BigDecimal volume_24h;
    public BigDecimal percent_change_1h;
    public BigDecimal percent_change_24h;
    public BigDecimal percent_change_7d;
    public BigDecimal percent_change_30d;
    public BigDecimal percent_change_60d;
    public BigDecimal percent_change_90d;
    public int market_cap;
    public double market_cap_dominance;
    public double fully_diluted_market_cap;
    public String last_updated;
}