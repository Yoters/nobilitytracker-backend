package com.patrity.model;

import lombok.Data;

import java.util.List;

@Data
public class Transactions {
    public final int total;
    public final int page;
    public final int page_size;
    public final List<Transaction> result;
}

