package com.senacor.senacorProject;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Konto {
    HttpClient httpclient = new HttpClient();

    public Float getKontostand() throws Exception {

        JsonObject json = httpclient.sendGet();

        // "Kontodaten" auslesen
        Float availableFromDepositAmount = json
                .getAsJsonObject("creditCardProgram")
                .getAsJsonArray("accounts")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("financeInfo")
                .get("availableFromDepositAmount")
                .getAsFloat();

        return availableFromDepositAmount;
    }

    public Float getLimit() throws Exception{

        JsonObject json = httpclient.sendGet();

        // "Limit" auslesen
        Float limit = json
                .getAsJsonObject("creditCardProgram")
                //.getAsJsonPrimitive("partialPaymentInfo")
                .getAsJsonObject()
                .get("limit")
                .getAsFloat();

        return limit;
    }
}
