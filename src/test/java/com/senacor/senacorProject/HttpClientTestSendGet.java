package com.senacor.senacorProject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpClientTestSendGet {

    HttpClient sut;

    @Before  //erzeugt jedes mal neue Instanz
    public void setup(){
        sut = new HttpClient();    //sut= System under test
    }


    @Test

    public void testSendGet() throws Exception{
        String token = "c592082e-b283-47b5-929d-f9a12cba2462";
        String url = "http://ec2-18-194-12-73.eu-central-1.compute.amazonaws.com/api/program/ada";

        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        Gson gson = new Gson();

        get.addHeader("User-Agent", "Google Chrome/61.0 Mozilla/5.0 Firefox/26.0");
        get.addHeader("Authorization", "Bearer" + token);

        HttpResponse response = client.execute(get);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        // Datei als JSON-Objekt einlesen
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        System.out.println("das ist der Token .:" +token);
        Assert.assertEquals("prüfen, ob das richtige JSON_Objekt gefunden wird",json, sut.sendGet());


    }


    @After  //räumt auf. Beim Testen mit der Datenbank nützlich,da Daten eingelesen und gespeichert werden
    public void tearDown(){

    }

}
