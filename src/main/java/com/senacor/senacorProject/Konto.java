package com.senacor.senacorProject;

import com.google.gson.JsonObject;

public class Konto {
    HttpClient httpclient = new HttpClient();

    public Float getKontostand() throws Exception {


        JsonObject json = httpclient.sendGet();

        // Element "Kontodaten" auslesen
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
}
