package com.printway.telegrambot;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;


public class Test {
    public void Http() throws Exception {
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet(
//                "https://api.telegram.org/bot1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI/sendMessage?chat_id="
//                        + chatId
//                        + "&text="
//                        + text
//                        + "&parse_mode=html");
//        HttpResponse response = httpclient.execute(httpget);
//        HttpEntity entity = response.getEntity();
//        String result = EntityUtils.toString(entity);
//        System.out.println(result);
//
//        JSONObject json = new JSONObject(result);
//        JSONObject oj = json.getJSONObject("result");
//        int message_id = oj.getInt("message_id");
//        System.out.println(message_id);
//        return message_id;

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(
                "https://api.telegram.org/bot1718882712:AAFa8WMH6T176FG5omdNwmXW8on-ejXMenI/getUpdates");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        System.out.println(result);
    }
}
