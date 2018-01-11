package com.senacor.senacorProject;

import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class LBBSpeechletTest {
   /* @Test
    public void onSessionStarted() throws Exception {
    }


    @Test
    public void onSessionEnded() throws Exception {
    }*/

    private LBBSpeechlet sut = new LBBSpeechlet();

    private static final String INTENT_WHATSMYKONTOSTAND="Kontostand";
    private static final String INTENT_WHATSMYLIMIT="Limit";

    @Test
    public void testOnLaunch() throws SpeechletException {
        String expected = "Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand.";
        LaunchRequest launchRequest = LaunchRequest.builder()
                .withRequestId("123")
                .withLocale(Locale.GERMANY)
                .build();

        Session session = Session.builder()
                .withSessionId("23423")
                .build();

        SpeechletResponse speechletResponse = sut.onLaunch(launchRequest, session);
        PlainTextOutputSpeech result = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();

        Assert.assertEquals("Soll willkommenstext beinhalten", expected, result.getText());
    }

    @Test
    public void testOnIntent() throws SpeechletException, Exception {
        String expected = "Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand.";
        LaunchRequest launchRequest = LaunchRequest.builder()
                .withRequestId("123")
                .withLocale(Locale.GERMANY)
                .build();

        Session session = Session.builder()
                .withSessionId("23423")
                .build();

        SpeechletResponse speechletResponse = sut.onLaunch(launchRequest, session);
        LBBSpeechlet mySpeechlet = new LBBSpeechlet();
        GetCreditBalance creditBalance = new GetCreditBalance();
        //System.out.println("wir testen die Methode handleKontostand");

            //JsonElement myKontostand = myClient.sendGet(myClient.sendPost());
            PlainTextOutputSpeech kontospeech = new PlainTextOutputSpeech();

                kontospeech.setText("Ihr Kontostand beträgt "+ creditBalance.getcreditBalance() +" Euro. Vielen Dank, bis zum nächsten Mal.");

        String intent= "Kontostand";

          //  kontospeech.setText("Ihr Kontostand beträgt "+ creditBalance.getcreditBalance() +" Euro. Vielen Dank, bis zum nächsten Mal.");
        PlainTextOutputSpeech limitspeech = new PlainTextOutputSpeech();
        limitspeech.setText("ich habe eine gewürfelt.");
        if(intent.equals(INTENT_WHATSMYKONTOSTAND))
        {
            // springe in Methode handleLImit
            Assert.assertEquals("Soll die handleLimit Methode auswählen", kontospeech.getText(), mySpeechlet.callHandleKontostand());
          //  Assert.assertEquals("Soll die handleLimit Methode auswählen", kontospeech.getText(), sut.onIntent(INTENT_WHATSMYLIMIT.toString(), session));

        }
        else if(intent.equals(INTENT_WHATSMYLIMIT))
        {
            Assert.assertEquals("Soll die handleLimit Methode auswählen", limitspeech.getText(), mySpeechlet.callHandleLimit());

        }

    }

    @Test
    public void testOnIntentWhatsMyLimit() {
        LBBSpeechlet mySpeechlet = new LBBSpeechlet();

        String expAnswer = "ich habe eine gewürfelt.";
        Assert.assertEquals("Soll verifizieren, ob handleLimit die korrekten Daten zurückgibt", expAnswer, mySpeechlet.callHandleLimit().toString());

    }

    @Test
    public void testOnIntentWhatsMyKontostand() {
        LBBSpeechlet mySpeechlet = new LBBSpeechlet();
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        int valueKonto = 0;
        String expAnswer = "Ihr Kontostand beträgt "+valueKonto+" Euro. Vielen Dank, bis zum nächsten Mal.";
        speech.setText(expAnswer);
        Assert.assertEquals("Soll verifizieren, ob handleKOntostand die korrekten Daten zurückgibt", expAnswer, mySpeechlet.callHandleKontostand());

    }
}
