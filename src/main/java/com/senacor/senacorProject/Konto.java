package com.senacor.senacorProject;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static com.senacor.senacorProject.HttpClient.getKontostand;

public class Konto {
    HttpClient httpclient = new HttpClient();

    public Float getKontostand() throws Exception {


        JsonObject json = httpclient.sendGet();
        // Element "Kontodaten" auslesengit
        Float availableFromDepositAmount = json
                .getAsJsonObject("creditCardProgram")
                .getAsJsonArray("accounts")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("financeInfo")
                .get("availableFromDepositAmount")
                .getAsFloat();

        //System.out.println(availableFromDepositAmount);
        return availableFromDepositAmount;
    }
}
