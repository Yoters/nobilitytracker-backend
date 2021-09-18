package com.patrity.model.lbank;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class LBank{
    public final String result;
    public final List<Datum> data;
    public final int error_code;
    public final long ts;
}

