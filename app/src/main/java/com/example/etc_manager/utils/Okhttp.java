package com.example.etc_manager.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Okhttp {

    public String getResult(String url, String json) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        try {
            Request request = new Request.Builder()
                    .url(new URL(url))
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
