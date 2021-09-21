package com.patrity.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patrity.Main;
import com.patrity.model.cmc.CoinMarketCap;
import com.patrity.model.lbank.LBank;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class Data {
    public static String getSupply() {
        try {
            Document doc = Jsoup.connect("https://bscscan.com/token/0xb6d136b9ae09bbdde47721b7127b622228ca9aa9").get();
            return doc.body().getElementById("ContentPlaceHolder1_hdnTotalSupply").val().replace(",", "");
        } catch (IOException ex) {
            ex.printStackTrace();
            return Main.nobilityData.totalSupply;
        }
    }

    public static String getHolders() {
        try {
            Document doc = Jsoup.connect("https://bscscan.com/token/0xA67a13c9283Da5AABB199Da54a9Cb4cD8B9b16bA").get();
            String data = doc.body().getElementsByClass("mr-3").toString();
            data = data.substring(data.indexOf(">") + 1, data.indexOf("addresses")).replace(" ", "").replace("\n", "");
            return data;
        } catch (IOException ex) {
            ex.printStackTrace();
            return Main.nobilityData.getTotalHolders();
        }
    }

    public static CoinMarketCap getCmcData() {
        OkHttpClient client = new OkHttpClient();
        Gson gb = new GsonBuilder().setPrettyPrinting().setLenient().create();

        Request request = new Request.Builder()
                .url("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?id=11336")
                .addHeader("X-CMC_PRO_API_KEY", Main.SINGLETON.config.getProperty("cmc-apikey"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            return gb.fromJson(Objects.requireNonNull(response.body()).string(), CoinMarketCap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LBank getLBankData() {
        OkHttpClient client = new OkHttpClient();
        Gson gb = new GsonBuilder().setPrettyPrinting().create();

        Request request = new Request.Builder()
                .url("https://api.lbkex.com/v2/ticker/24hr.do?symbol=nbl_usdt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return gb.fromJson(Objects.requireNonNull(response.body()).string(), LBank.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
