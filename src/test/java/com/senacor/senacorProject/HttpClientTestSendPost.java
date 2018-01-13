package com.senacor.senacorProject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTestSendPost {

    HttpClient sut;

    @Before  //erzeugt jedes mal neue Instanz
            public void setup(){
        sut = new HttpClient();    //sut= System under test
    }

//c592082e-b283-47b5-929d-f9a12cba2462

    @Test
    public void alternSendPostTest()throws Exception
    {
        String exp = "c592082e-b283-47b5-929d-f9a12cba2462";
        Assert.assertEquals("SendPostMethode richtig ausgeführt? ", exp, sut.sendPost());
    }
    @Test

    public void testSendPost()throws Exception{
        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/oauth/token";
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        post.addHeader("USER-AGENT", "Google Chrome/61.0 Mozilla/5.0 Firefox/26.0");
        post.addHeader("Authorization", "Basic a2tiOnNlY3JldA==");

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("username", "edieser"));
        nameValuePairs.add(new BasicNameValuePair("password", "password"));
        nameValuePairs.add(new BasicNameValuePair("device_id", "2a32cd35b93e01307a10cba4fe01202c1394a7fe29"));
      //  nameValuePairs.add(new BasicNameValuePair("device_type", "PC"));
       // nameValuePairs.add(new BasicNameValuePair("sms_tan", "0108"));
       // nameValuePairs.add(new BasicNameValuePair("remember_device", "true"));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
      //  nameValuePairs.add(new BasicNameValuePair("client_id", "kkb"));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
        String expectedResult = json_auth.getString("access_token");

        Assert.assertEquals("soll überprüfen, ob sendPost funktioniert", expectedResult, sut.sendPost());





    }


    @After  //räumt auf. Beim Testen mit der Datenbank nützlich,da Daten eingelesen und gespeichert werden
    public void tearDown(){


    }

}

