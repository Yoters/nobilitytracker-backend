package com.patrity.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patrity.model.Reflections;
import com.patrity.model.Transaction;
import com.patrity.model.Transactions;
import io.javalin.http.Context;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ReflectionHistory {

    public static void getTotalReflections(Context ctx) {

        if (ctx.queryParamMap().size() == 0) {
            ctx.result("Please include a param with the user address");
            return;
        }
        if (ctx.queryParam("address") == null) {
            ctx.result("Please include a param with the user address");
            return;
        }
        String address = ctx.queryParam("address");
        List<Transaction> transactions = getAllTransactions(address);

        Gson gb = new GsonBuilder().setPrettyPrinting().create();

        if (transactions.size() == 0) {
            ctx.result(gb.toJson(new Reflections(0.0)));
        }
        BigDecimal total = new BigDecimal(0);


        for (Transaction transaction : transactions) {
            if (transaction.getFrom_address().equalsIgnoreCase("0xB6d136B9ae09BBdde47721b7127b622228Ca9AA9")) {
                BigDecimal rawValue = new BigDecimal(transaction.getValue());
                BigDecimal divisor = new BigDecimal("1000000000000000000");
                rawValue = rawValue.divide(divisor);
                total = total.add(rawValue);
            }
        }


        Reflections reflections = new Reflections(Double.parseDouble(total.toString()));
        ctx.result(gb.toJson(reflections));

    }

    private static List<Transaction> getAllTransactions(String address) {
        OkHttpClient client = new OkHttpClient();
        Gson gb = new GsonBuilder().setPrettyPrinting().create();

        Request request = new Request.Builder()
                .url("https://deep-index.moralis.io/api/v2/" + address + "/erc20/transfers?chain=bsc")
                .addHeader("X-API-Key", "GuUPJupRDyn4cxFhN3WTY5Rz8GGGlKiVMYERVJH8Ry4W5E4HLR8rmuXsPL801Ihc")
                .build();

        try (Response response = client.newCall(request).execute()) {
            Transactions transactions = gb.fromJson(Objects.requireNonNull(response.body()).string(), Transactions.class);
            return transactions.getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
