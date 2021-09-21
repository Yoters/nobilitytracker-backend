package com.patrity.model.cmc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Quote{
    @SerializedName("USD")
    public USD uSD;
}
