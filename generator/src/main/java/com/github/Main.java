package com.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.model.ClickEvent;
import com.github.model.ImpressionEvent;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class Main {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static boolean end = false;

    public static void main(String[] args) throws InterruptedException, IOException {

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Finish");
                end = true;
            }
        });

        OkHttpClient httpClient = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        int counter = 1;

        while (!end) {

            String id = String.valueOf(counter);

            ImpressionEvent impression = new ImpressionEvent(
                    id,
                    "ad" + id,
                    "Title" + id,
                    (double)counter,
                    "app" + id,
                    "appTitle" + id,
                    System.currentTimeMillis() / 1000
            );
            httpClient.newCall(new Request.Builder()
                                            .url("http://localhost:8585/event/impression")
                                            .put(RequestBody.create(JSON, mapper.writeValueAsString(impression)))
                                            .build()
            ).execute();

            ClickEvent click = new ClickEvent(id, System.currentTimeMillis() / 1000);
            httpClient.newCall(new Request.Builder()
                    .url("http://localhost:8585/event/click")
                    .put(RequestBody.create(JSON, mapper.writeValueAsString(click)))
                    .build()
            ).execute();

            System.out.print("*");
            Thread.sleep(1000);
            ++counter;
        }

    }

}
