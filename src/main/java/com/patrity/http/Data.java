package com.patrity.http;

import com.patrity.model.NobilityData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Data {

    public static String getSupply() throws IOException {
        Document doc = Jsoup.connect("https://bscscan.com/token/0xb6d136b9ae09bbdde47721b7127b622228ca9aa9").get();
        return doc.body().getElementById("ContentPlaceHolder1_hdnTotalSupply").val().replace(",", "");
    }

    public static String getHolders() throws IOException {
        Document doc = Jsoup.connect("https://bscscan.com/token/0xA67a13c9283Da5AABB199Da54a9Cb4cD8B9b16bA").get();
        String data = doc.body().getElementsByClass("mr-3").toString();
        data = data.substring(data.indexOf(">") + 1, data.indexOf("addresses")).replace(" ", "").replace("\n", "");
        return data;
    }
}
