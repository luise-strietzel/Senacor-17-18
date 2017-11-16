package com.senacor.senacorProject;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClientExample {

    private final String USER_AGENT = "Google Chrome/61.0 Mozilla/5.0 Firefox/26.0";

    public static void main(String[] args) throws Exception {

        HttpClientExample http = new HttpClientExample();

        System.out.println("Testing 01 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 02 - Send Http POST request");
        http.sendPost();

    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/program/ada";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
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

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/oauth/token";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.addHeader("USER-AGENT", USER_AGENT );
        post.addHeader("Authorization", "Basic a2tiOnNlY3JldA==" );
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
