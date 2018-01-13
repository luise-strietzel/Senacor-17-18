package com.senacor.senacorProject;

import com.amazon.speech.slu.ConfirmationStatus;
import com.amazon.speech.slu.Intent;
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

    @Test
    public void testOnLaunch() throws SpeechletException {
        String expected = "Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limitmit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.";
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
    public void testLimitIntent() throws Exception {
        Intent myIntent = Intent.builder()
                .withName("Limit")
                .withConfirmationStatus(ConfirmationStatus.CONFIRMED)
                .build();
        IntentRequest myIntentRequest = IntentRequest.builder()
                .withRequestId("123")
                .withIntent(myIntent)
                .withLocale(Locale.GERMANY)
                .build();
        Session session = Session.builder()
                .withSessionId("23423")
                .build();


     //   GetCreditBalance creditBalance = new GetCreditBalance();
        //SpeechletResponse myResponse;
        Konto myKonto = new Konto();

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ihr Kontostand beträgt "+ myKonto.getKontostand() +" Euro. Vielen Dank, bis zum nächsten Mal.");
        SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
        Assert.assertEquals("Soll die handleKontoStand Methode auswählen", myResponse.getReprompt(), sut.onIntent(myIntentRequest, session).getReprompt());

    }

    @Test
    public void testKontostandIntent() throws Exception
    {
        Intent myIntent = Intent.builder()
                .withName("Kontostand")
                .withConfirmationStatus(ConfirmationStatus.CONFIRMED)
                .build();
        IntentRequest myIntentRequest = IntentRequest.builder()
                .withRequestId("123")
                .withIntent(myIntent)
                .withLocale(Locale.GERMANY)
                .build();
        Session session = Session.builder()
                .withSessionId("23423")
                .build();

        Konto konto = new Konto();

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro. Vielen Dank, bis zum nächsten Mal.");
        SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
        Assert.assertEquals("Soll die handleKontoStand Methode auswählen", myResponse.getReprompt(), sut.onIntent(myIntentRequest, session).getReprompt());
    }

    @Test
    public void testKontostandUebersichtIntent() throws SpeechletException, Exception {
        Intent myIntent = Intent.builder()
                .withName("Kontoubersicht")
                .withConfirmationStatus(ConfirmationStatus.CONFIRMED)
                .build();
        IntentRequest myIntentRequest = IntentRequest.builder()
                .withRequestId("123")
                .withIntent(myIntent)
                .withLocale(Locale.GERMANY)
                .build();
        Session session = Session.builder()
                .withSessionId("23423")
                .build();

        Konto konto = new Konto();

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        // speech.setText("Ihr Kontostand beträgt "+ myKonto.getKontostand() +" Euro. Vielen Dank, bis zum nächsten Mal.");
        speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro und ihr Limit "+ konto.getLimit()+" Euro. Vielen Dank und bis zum nächsten Mal.");
        SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
        Assert.assertEquals("Soll die handleKontoStand Methode auswählen", myResponse.getReprompt(), sut.onIntent(myIntentRequest, session).getReprompt());

    }



}
