package com.patrity;

import com.google.gson.GsonBuilder;
import com.patrity.http.Data;
import com.patrity.http.ReflectionHistory;
import com.patrity.model.NobilityData;
import com.patrity.model.cmc.CoinMarketCap;
import com.patrity.model.lbank.LBank;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static Main SINGLETON;
    public static NobilityData nobilityData = new NobilityData(
            "0",
            "0",
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new BigDecimal(0.0),
            new LBank("0",new ArrayList<>(), 0, 0));

    public final Properties config = new Properties();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main (String[] args) {
        Main.SINGLETON = new Main();
        Main.SINGLETON.loadConfig();

        nobilityData = SINGLETON.updateSupply();

        Main.SINGLETON.executorService.submit(() -> {
            try {
                Main.SINGLETON.init();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });


        Javalin app = Javalin.create().start(6969);
        app.get("/", Main::serveSupply);
        app.get("/reflections", ReflectionHistory::getTotalReflections);
    }

    private void init() throws InterruptedException, IOException {
        while (true) {
            nobilityData = updateSupply();
            Thread.sleep(30000L);
        }
    }
    private void loadConfig() {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private NobilityData updateSupply() {
        String supply = Data.getSupply();
        String holders = Data.getHolders();
        CoinMarketCap cmc = Data.getCmcData();
        LBank lBank = Data.getLBankData();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Supply updated: " + timeStamp);
        return new NobilityData(supply,
                holders,
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPercent_change_24h(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPercent_change_7d(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPercent_change_30d(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPercent_change_60d(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPercent_change_90d(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getVolume_24h(),
                cmc == null ? new BigDecimal(0.0) : cmc.getData().get_11336().getQuote().getUSD().getPrice(),
                lBank);
    }

    private static void serveSupply(Context ctx) {
        GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
        ctx.result(gb.create().toJson(nobilityData));
    }

}
