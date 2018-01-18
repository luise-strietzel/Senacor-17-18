package com.senacor.senacorProject;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {

    private final String USER_AGENT = "Google Chrome/61.0 Mozilla/5.0 Firefox/26.0";
    private JsonObject kontostand;

    private static JsonObject setKontostand() throws Exception
    {
        HttpClient http = new HttpClient();
        http.kontostand = http.sendGet();
        return http.kontostand;
    }

    public static JsonObject getKontostand() throws Exception{
        setKontostand();
        return setKontostand();
    }

    public JsonObject sendGet() throws Exception {
        String token = sendPost();
        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/program/ada";

        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        Gson gson = new Gson();

        get.addHeader("User-Agent", USER_AGENT);
        get.addHeader("Authorization", "Bearer" + token);

        HttpResponse response = client.execute(get);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        // Datei als JSON-Objekt einlesen
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        System.out.println("das ist der Token .:" +token);
        return json;

    }

    public String sendPost() throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/oauth/token";

        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        post.addHeader("USER-AGENT", USER_AGENT);
        post.addHeader("Authorization", "Basic a2tiOnNlY3JldA==");

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("username", "edieser"));
        nameValuePairs.add(new BasicNameValuePair("password", "password"));
        nameValuePairs.add(new BasicNameValuePair("device_id", "2a32cd35b93e01307a10cba4fe01202c1394a7fe29"));
        nameValuePairs.add(new BasicNameValuePair("device_type", "PC"));
        nameValuePairs.add(new BasicNameValuePair("sms_tan", "0108"));
        nameValuePairs.add(new BasicNameValuePair("remember_device", "true"));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
        nameValuePairs.add(new BasicNameValuePair("client_id", "kkb"));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);

        JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
        String token = json_auth.getString("access_token");

        return token;

    }

}
