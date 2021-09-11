package com.patrity;

import com.google.gson.GsonBuilder;
import com.patrity.http.Data;
import com.patrity.model.NobilityData;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;

public class Main {

    private static Main SINGLETON;
    private static NobilityData nobilityData;

    public static void main (String[] args) throws IOException, InterruptedException {
        Main.SINGLETON = new Main();
        System.out.println(Data.getHolders());
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.enableCorsForAllOrigins();
        }).start(6969);
        app.get("/", Main::serveSupply);
        Main.SINGLETON.updateSupply();
    }

    private void updateSupply() throws IOException, InterruptedException {
        while (true) {
            String supply = Data.getSupply();
            String holders = Data.getHolders();
            nobilityData = new NobilityData(supply, holders);
            System.out.println("Total Supply Updated: " + nobilityData.getTotalSupply());
            System.out.println("Total Holders Updated: " + nobilityData.getTotalHolders());
            Thread.sleep(30000L);
        }
    }

    private static void serveSupply(Context ctx) {
        GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
        ctx.result(gb.create().toJson(nobilityData));

    }

}
