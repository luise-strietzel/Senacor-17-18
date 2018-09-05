package com.senacor.senacorProject;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Konto {
    private HttpClient httpclient = new HttpClient();
    private JsonObject kontodaten;

    public boolean meldeAn(String username, String password) {

        boolean warErfolgreich;

        try {
            String token = httpclient.sendPost(username, password);

            if (token != null) {
                warErfolgreich = true;
                kontodaten = httpclient.sendGet(token);
            } else {
                warErfolgreich = false;
            }

        } catch (Exception e) {
            warErfolgreich = false;
        }

        System.out.println("war erfolgreich?");
        return warErfolgreich;
    }


    public Float getKontostand() throws Exception {

        if (kontodaten != null) {

            // "Kontodaten" auslesen
            Float availableFromDepositAmount = kontodaten
                    .getAsJsonObject("creditCardProgram")
                    .getAsJsonArray("accounts")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("financeInfo")
                    .get("availableFromDepositAmount")
                    .getAsFloat();

            return availableFromDepositAmount;
        } else {
            return null;
        }
    }

    public Float getLimit() throws Exception {

        if (kontodaten != null) {
            // "Limit" auslesen
            Float limit = kontodaten
                    .getAsJsonObject("creditCardProgram")
                    //.getAsJsonPrimitive("partialPaymentInfo")
                    .getAsJsonObject()
                    .get("limit")
                    .getAsFloat();

            return limit;
        } else {
            return null;
        }

    }
}