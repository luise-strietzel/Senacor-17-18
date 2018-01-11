package com.senacor.senacorProject;

import com.amazon.speech.speechlet.*;
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
    // Intent myLimitIntent = new Intent("Limit", "NONE",null);
    //  Assert.assertEquals("Soll die handleLimit Methode auswählen", kontospeech.getText(), mySpeechlet.callHandleKontostand());

    @Test
    public void testOnIntent() throws SpeechletException, Exception {
        IntentRequest myIntentRequest = IntentRequest.builder()
                .withRequestId("123")
                .withLocale(Locale.GERMANY)
                .build();
        Session session = Session.builder()
                .withSessionId("23423")
                .build();

        String intentName = myIntentRequest.getIntent().getName();
        System.out.println("das ist der intentname "+intentName);

        GetCreditBalance creditBalance = new GetCreditBalance();
        //SpeechletResponse myResponse;

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();


        if(intentName.equals(INTENT_WHATSMYKONTOSTAND))
        {
            speech.setText("Ihr Kontostand beträgt "+ creditBalance.getcreditBalance() +" Euro. Vielen Dank, bis zum nächsten Mal.");
            SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
            // springe in Methode handleKontostand
            Assert.assertEquals("Soll die handleKontoStand Methode auswählen", myResponse, sut.onIntent(myIntentRequest, session));
           // Assert.assertEquals("Soll die handleKontoStand Methode auswählen", speech.getText(), sut.onIntent(myIntentRequest, session));
        }

        else if(intentName.equals(INTENT_WHATSMYLIMIT)){
            speech.setText("ich habe eine gewürfelt.");
            SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
            // springe in Methode handleLimit
            Assert.assertEquals("Soll die handleKontoStand Methode auswählen", myResponse, sut.onIntent(myIntentRequest, session));
          //  Assert.assertEquals("Soll die handleLimit Methode auswählen", speech.getText(), sut.onIntent(myIntentRequest, session));
        }
    }

    @Test
    public void testOnIntentWhatsMyLimit() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("ich habe eine gewürfelt.");
        SpeechletResponse response =  SpeechletResponse.newTellResponse(speech);

        Assert.assertEquals("Soll verifizieren, ob handleLimit die korrekten Daten zurückgibt", response.getReprompt(), sut.callHandleLimit().getReprompt());

    }

    @Test
    public void testOnIntentWhatsMyKontostand() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        int valueKonto = 0;
        speech.setText("Ihr Kontostand beträgt "+valueKonto+" Euro. Vielen Dank, bis zum nächsten Mal.");
        SpeechletResponse response =  SpeechletResponse.newTellResponse(speech);
        Assert.assertEquals("Soll verifizieren, ob handleKOntostand die korrekten Daten zurückgibt", response.getReprompt(), sut.callKonto().getReprompt());

    }
}
