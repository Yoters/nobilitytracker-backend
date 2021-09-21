package com.patrity.model.cmc;

import lombok.Data;

import java.util.List;

@Data
public class _11336{
    public int id;
    public String name;
    public String symbol;
    public String slug;
    public int num_market_pairs;
    public String date_added;
    public List<Object> tags;
    public long max_supply;
    public int circulating_supply;
    public long total_supply;
    public Platform platform;
    public int is_active;
    public int cmc_rank;
    public int is_fiat;
    public String last_updated;
    public Quote quote;
}