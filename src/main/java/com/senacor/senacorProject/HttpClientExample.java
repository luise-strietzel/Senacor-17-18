package com.senacor.senacorProject;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClientExample {

    private final String USER_AGENT = "Google Chrome/61.0.3163.100";

    public static void main(String[] args) throws Exception {

        HttpClientExample http = new HttpClientExample();

        System.out.println("\nTesting - Send Http POST request");
        http.sendPost();

    }

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/oauth/token";

        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("USER-AGENT", USER_AGENT );
        //post.setHeader("Authorization", "BASIC a2tiOnNlY3JldA==" );
        //post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", "edieser"));
        nameValuePairs.add(new BasicNameValuePair("password", "password"));
        nameValuePairs.add(new BasicNameValuePair("device_id", "20ee812cd3c821e1eaa2db40881a41527faaa4457d"));
        nameValuePairs.add(new BasicNameValuePair("device_type", "PC"));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
        nameValuePairs.add(new BasicNameValuePair("client_id", "kkb"));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);

        //JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
        //String token = json_auth.getString("access_token");

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

}
