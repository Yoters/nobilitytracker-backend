package com.patrity;

import com.google.gson.GsonBuilder;
import com.patrity.http.Data;
import com.patrity.http.ReflectionHistory;
import com.patrity.model.DexToolsV1;
import com.patrity.model.DexToolsV2;
import com.patrity.model.NobilityData;
import com.patrity.model.lbank.LBank;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static Main SINGLETON;
    public static NobilityData nobilityData = new NobilityData(
            "0",
            "0",
            0.0,
            0.0,
            0.0,
            new LBank("0",new ArrayList<>(), 0, 0));
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main (String[] args) {
        Main.SINGLETON = new Main();

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
    }

    private void init() throws InterruptedException, IOException {
        while (true) {
            nobilityData = updateSupply();
            Thread.sleep(30000L);
        }
    }

    private NobilityData updateSupply() {
        String supply = Data.getSupply();
        String holders = Data.getHolders();
        DexToolsV1 dexToolsV1 = Data.getV1Data();
        DexToolsV2 dexToolsV2 = Data.getV2Data();
        LBank lBank = Data.getLBankData();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Supply updated: " + timeStamp);
        return new NobilityData(supply,
                holders,
                dexToolsV1 == null ? 0.0 : dexToolsV1.getPriceChange24h(),
                dexToolsV1 == null ? 0.0 : dexToolsV1.getVolume24hUSD(),
                dexToolsV2 == null ? 0.0 : dexToolsV2.getToken_price_usd(),
                lBank);
    }

    private static void serveSupply(Context ctx) {
        GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
        ctx.result(gb.create().toJson(nobilityData));
    }

}
