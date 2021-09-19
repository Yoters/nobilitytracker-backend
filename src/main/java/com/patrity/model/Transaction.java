package com.patrity.model;

import lombok.Data;

@Data
public class Transaction {
    public final String transaction_hash;
    public final String address;
    public final String block_timestamp;
    public final String block_number;
    public final String block_hash;
    public final String to_address;
    public final String from_address;
    public final String value;
}