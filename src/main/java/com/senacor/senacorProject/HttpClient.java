package com.senacor.senacorProject;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public static void main(String[] args) throws Exception {

        HttpClient http = new HttpClient();
        //ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("Testing 01 - Send Http GET request");
        http.sendGet(http.sendPost());

        //System.out.println("\nTesting 02 - Send Http POST request");
        //http.sendPost();

    }

    // HTTP GET request
    private void sendGet(String token) throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/program/ada";

        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        Gson gson = new Gson();


        // add request header
        get.addHeader("User-Agent", USER_AGENT);
        get.addHeader("Authorization", "Bearer" + token);

        HttpResponse response = client.execute(get);

        //FileInputStream input = new FileInputStream("response.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        // Datei als JSON-Objekt einlesen
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        // Element "Kontodaten" auslesengit
        JsonElement availableFromDepositAmount = json.getAsJsonObject("creditCardProgram").getAsJsonArray("accounts").get(0).getAsJsonObject().getAsJsonObject("financeInfo").get("availableFromDepositAmount");

        System.out.println(availableFromDepositAmount);

    }

    // HTTP POST request
    private String sendPost() throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/oauth/token";

        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.addHeader("USER-AGENT", USER_AGENT);
        post.addHeader("Authorization", "Basic a2tiOnNlY3JldA==");
        //post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        //add body
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
