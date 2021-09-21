package com.patrity.model.cmc;

import lombok.Data;

@Data
public class Platform{
    public int id;
    public String name;
    public String symbol;
    public String slug;
    public String token_address;
}