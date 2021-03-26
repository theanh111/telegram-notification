package com.printway.telegrambot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.printway.entity.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private final String TELEGRAM_BOT = "https://api.telegram.org/bot1613155455:AAG1WJ3hxXehq9DtX1-hk8MpAc0mYweR5eg/getUpdates";


    public void configHttp() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(TELEGRAM_BOT);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<JSONObject> resultJsonList = new ArrayList(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            resultJsonList.add(jsonArray.getJSONObject(i));
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        Result result1 = gson.fromJson(resultJsonList.get(0).toString(), Result.class);
        System.out.println(result1);
    }
}
