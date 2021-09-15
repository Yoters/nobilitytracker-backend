package com.patrity;

import com.google.gson.GsonBuilder;
import com.patrity.http.Data;
import com.patrity.model.DexToolsV1;
import com.patrity.model.DexToolsV2;
import com.patrity.model.NobilityData;
import com.patrity.model.lbank.LBank;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;

public class Main {

    private static Main SINGLETON;
    private static NobilityData nobilityData;

    public static void main (String[] args) throws IOException, InterruptedException {
        Main.SINGLETON = new Main();
        Javalin app = Javalin.create().start(6969);
        app.get("/", Main::serveSupply);
        Main.SINGLETON.updateSupply();
    }

    private void updateSupply() throws IOException, InterruptedException {
        while (true) {
            String supply = Data.getSupply();
            String holders = Data.getHolders();
            DexToolsV1 dexToolsV1 = Data.getV1Data();
            DexToolsV2 dexToolsV2 = Data.getV2Data();
            LBank lBank = Data.getLBankData();

            nobilityData = new NobilityData(supply, holders, dexToolsV1.getPriceChange24h(), dexToolsV1.getVolume24hUSD(), dexToolsV2.getToken_price_usd(), lBank);
            Thread.sleep(30000L);
        }
    }

    private static void serveSupply(Context ctx) {
        GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
        ctx.result(gb.create().toJson(nobilityData));
    }

}
