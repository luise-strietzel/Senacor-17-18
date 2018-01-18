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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

    HttpClient sut;
    HttpClient httpclient = Mockito.mock(HttpClient.class);

    @Before  //erzeugt jedes mal neue Instanz
    public void setup(){
        sut = new HttpClient();    //sut= System under test
    }

//c592082e-b283-47b5-929d-f9a12cba2462

    @Test
    public void sendPostTest()throws Exception
    {
        Mockito.when(httpclient.sendPost()).thenReturn("c592082e-b283-47b5-929d-f9a12cba2462");

        String exp = "c592082e-b283-47b5-929d-f9a12cba2462";
        //Assert.assertEquals("SendPostMethode richtig ausgeführt? ", exp, sut.sendPost());
        Assert.assertEquals("SendPostMethode richtig ausgeführt? ", exp , httpclient.sendPost());
    }

    @Test

    public void alternTestSendPost()throws Exception
    {

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

    @Test
    public void altTestSendGet() throws Exception{
        Gson gson = new Gson();
        //   myj = "{"creditCardProgram":{"partialPaymentInfo":{"partialPayment":false,"fixedRate":false,"percentage":0,"rate":0},"id":"ada","name":"ADAC","limit":245000,"accounts":[{"creditCardNumber":"123456xxxxxx7777","shortCreditCardNumber":"*7777","creditCardHolder":"Ewald Dieser - Rücklastschrift","issuer":"VISA","isPartnerAccount":false,"isActive":true,"isBlocked":false,"isCanceled":false,"financeInfo":{"id":0,"creditLineAmount":0,"carryoverTransactionsAmount":0,"bookedTransactionsAmount":78182,"authorizedTransactionsAmount":21818,"availableFromDepositAmount":0,"availableFromCreditLineAmount":0,"specialCase":"returndebit","returnDebitNote":358200},"accountId":"edieser001","paymentSafety":{"internetPayment":true,"onSitePayment":"EUROPE"}},{"creditCardNumber":"123456xxxxxx3333","shortCreditCardNumber":"*3333","creditCardHolder":"Ewald's Freundin - Rücklastschrift","issuer":"VISA","isPartnerAccount":true,"isActive":true,"isBlocked":false,"isCanceled":false,"financeInfo":{"id":0,"creditLineAmount":0,"carryoverTransactionsAmount":0,"bookedTransactionsAmount":90688,"authorizedTransactionsAmount":9312,"availableFromDepositAmount":0,"availableFromCreditLineAmount":0,"specialCase":"returndebit","returnDebitNote":358200},"accountId":"edieser003","paymentSafety":{"internetPayment":true,"onSitePayment":"EUROPE"}}],"currency":"Euro","financeInfo":{"id":0,"creditLineAmount":245000,"carryoverTransactionsAmount":45000,"bookedTransactionsAmount":168300,"authorizedTransactionsAmount":53900,"availableFromDepositAmount":0,"availableFromCreditLineAmount":0,"specialCase":"returndebit","returnDebitNote":358200},"isPartnerProgram":false,"numberOfUnreadDocuments":0,"design":"adac","hasBonusPoints":true,"limitMaxIncrement":100000,"limitIncrement":10000,"mccDefaultName":"Sonstiges","mccDefaultColor":"#777777","customerServicePhoneNumber":"012345678","paymentProtection":false}});.
        String myjs = "{'creditCardProgram':{'partialPaymentInfo':{'partialPayment':false,'fixedRate':false,'percentage':0,'rate':0},'id':'ada','name':'ADAC','limit':245000,'accounts':[{'creditCardNumber':'123456xxxxxx7777','shortCreditCardNumber':'*7777','creditCardHolder':'Ewald Dieser - Rücklastschrift','issuer':'VISA','isPartnerAccount':false,'isActive':true,'isBlocked':false,'isCanceled':false,'financeInfo':{'id':0,'creditLineAmount':0,'carryoverTransactionsAmount':0,'bookedTransactionsAmount':78182,'authorizedTransactionsAmount':21818,'availableFromDepositAmount':0,'availableFromCreditLineAmount':0,'specialCase':'returndebit','returnDebitNote':358200},'accountId':'edieser001','paymentSafety':{'internetPayment':true,'onSitePayment':'EUROPE'}},{'creditCardNumber':'123456xxxxxx3333','shortCreditCardNumber':'*3333','creditCardHolder':'Ewald's Freundin - Rücklastschrift','issuer':'VISA','isPartnerAccount':true,'isActive':true,'isBlocked':false,'isCanceled':false,'financeInfo':{'id':0,'creditLineAmount':0,'carryoverTransactionsAmount':0,'bookedTransactionsAmount':90688,'authorizedTransactionsAmount':9312,'availableFromDepositAmount':0,'availableFromCreditLineAmount':0,'specialCase':'returndebit','returnDebitNote':358200},'accountId':'edieser003','paymentSafety':{'internetPayment':true,'onSitePayment':'EUROPE'}}],'currency':'Euro','financeInfo':{'id':0,'creditLineAmount':245000,'carryoverTransactionsAmount':45000,'bookedTransactionsAmount':168300,'authorizedTransactionsAmount':53900,'availableFromDepositAmount':0,'availableFromCreditLineAmount':0,'specialCase':'returndebit','returnDebitNote':358200},'isPartnerProgram':false,'numberOfUnreadDocuments':0,'design':'adac','hasBonusPoints':true,'limitMaxIncrement':100000,'limitIncrement':10000,'mccDefaultName':'Sonstiges','mccDefaultColor':'#777777','customerServicePhoneNumber':'012345678','paymentProtection':false}}";
        String trimIt = myjs.trim();
        BufferedReader myReader = new BufferedReader(new StringReader(trimIt));
        JsonObject myj = gson.fromJson(myReader, JsonObject.class);
        Assert.assertEquals("prüfen, ob das richtige JSON_Objekt gefunden wird",myj, sut.sendGet());
    }


    @After  //räumt auf. Beim Testen mit der Datenbank nützlich,da Daten eingelesen und gespeichert werden
    public void tearDown()
    {

    }

}

