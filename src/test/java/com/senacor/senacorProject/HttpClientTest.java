package com.senacor.senacorProject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HttpClientTest {

    HttpClient sut = new HttpClient();
    HttpClient httpclient = Mockito.mock(HttpClient.class);

    @Before  //erzeugt jedes mal neue Instanz
    public void setup(){
        sut = new HttpClient();    //sut= System under test
    }

    @Test
    public void sendPostTest()throws Exception
    {
        Mockito.when(httpclient.sendPost("edieser", "password")).thenReturn("c592082e-b283-47b5-929d-f9a12cba2462");
        String exp = "c592082e-b283-47b5-929d-f9a12cba2462";

        Assert.assertEquals("SendPostMethode richtig ausgeführt? ", exp , httpclient.sendPost("edieser", "password"));
    }

    @Test
    public void sendGetTest() throws Exception{

        String expected = ("{\"creditCardProgram\":{\"partialPaymentInfo\":{\"partialPayment\":false,\"fixedRate\":false,\"percentage\":0,\"rate\":0},\"id\":\"ada\",\"name\":\"ADAC\",\"limit\":245000,\"accounts\":[{\"creditCardNumber\":\"123456xxxxxx7777\",\"shortCreditCardNumber\":\"*7777\",\"creditCardHolder\":\"Ewald Dieser - Rücklastschrift\",\"issuer\":\"VISA\",\"isPartnerAccount\":false,\"isActive\":true,\"isBlocked\":false,\"isCanceled\":false,\"financeInfo\":{\"id\":0,\"creditLineAmount\":0,\"carryoverTransactionsAmount\":0,\"bookedTransactionsAmount\":78182,\"authorizedTransactionsAmount\":21818,\"availableFromDepositAmount\":0,\"availableFromCreditLineAmount\":0,\"specialCase\":\"returndebit\",\"returnDebitNote\":358200},\"accountId\":\"edieser001\",\"paymentSafety\":{\"internetPayment\":true,\"onSitePayment\":\"EUROPE\"}},{\"creditCardNumber\":\"123456xxxxxx3333\",\"shortCreditCardNumber\":\"*3333\",\"creditCardHolder\":\"Ewald's Freundin - Rücklastschrift\",\"issuer\":\"VISA\",\"isPartnerAccount\":true,\"isActive\":true,\"isBlocked\":false,\"isCanceled\":false,\"financeInfo\":{\"id\":0,\"creditLineAmount\":0,\"carryoverTransactionsAmount\":0,\"bookedTransactionsAmount\":90688,\"authorizedTransactionsAmount\":9312,\"availableFromDepositAmount\":0,\"availableFromCreditLineAmount\":0,\"specialCase\":\"returndebit\",\"returnDebitNote\":358200},\"accountId\":\"edieser003\",\"paymentSafety\":{\"internetPayment\":true,\"onSitePayment\":\"EUROPE\"}}],\"currency\":\"Euro\",\"financeInfo\":{\"id\":0,\"creditLineAmount\":245000,\"carryoverTransactionsAmount\":45000,\"bookedTransactionsAmount\":168300,\"authorizedTransactionsAmount\":53900,\"availableFromDepositAmount\":0,\"availableFromCreditLineAmount\":0,\"specialCase\":\"returndebit\",\"returnDebitNote\":358200},\"isPartnerProgram\":false,\"numberOfUnreadDocuments\":0,\"design\":\"adac\",\"hasBonusPoints\":true,\"limitMaxIncrement\":100000,\"limitIncrement\":10000,\"mccDefaultName\":\"Sonstiges\",\"mccDefaultColor\":\"#777777\",\"customerServicePhoneNumber\":\"012345678\",\"paymentProtection\":false}}");
        String result = sut.sendGet("c592082e-b283-47b5-929d-f9a12cba2462").toString();

        Assert.assertEquals("SendPostMethode richtig ausgeführt? ", expected , result);

    }

}

