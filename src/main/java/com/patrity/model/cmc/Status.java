package com.patrity.model.cmc;

import lombok.Data;


@Data
public class Status{
    public String timestamp;
    public int error_code;
    public Object error_message;
    public int elapsed;
    public int credit_count;
    public Object notice;
}
