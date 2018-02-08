package com.senacor.senacorProject;

import com.amazon.speech.slu.ConfirmationStatus;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class LBBSpeechletTest {

    private LBBSpeechlet sut = new LBBSpeechlet();
    private Konto konto = new Konto();

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
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ihr Limit beträgt " + konto.getLimit() + " Euro. Möchten Sie noch Ihren Kontostand erfahren?");
        SpeechletResponse myResponse = SpeechletResponse.newAskResponse(speech, reprompt);
        Assert.assertEquals("Soll die handleLimit Methode auswählen", ((PlainTextOutputSpeech)myResponse.getReprompt().getOutputSpeech()).getText(), ((PlainTextOutputSpeech)sut.onIntent(myIntentRequest, session).getReprompt().getOutputSpeech()).getText());
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

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ihr Kontostand beträgt " + konto.getKontostand() + " Euro. Möchten Sie noch Ihr monatliches Limit erfahren");
        SpeechletResponse myResponse = SpeechletResponse.newAskResponse(speech, reprompt);
        Assert.assertEquals("Soll die handleKontoStand Methode auswählen", ((PlainTextOutputSpeech)myResponse.getReprompt().getOutputSpeech()).getText(), ((PlainTextOutputSpeech)sut.onIntent(myIntentRequest, session).getReprompt().getOutputSpeech()).getText());
    }

    @Test
    public void testKontostandUebersichtIntent() throws Exception {
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

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro und ihr Limit "+ konto.getLimit()+" Euro. Vielen Dank und bis zum nächsten Mal.");
        SpeechletResponse myResponse = SpeechletResponse.newTellResponse(speech);
        System.out.println(((PlainTextOutputSpeech)myResponse.getOutputSpeech()).getText());
        System.out.println(((PlainTextOutputSpeech)sut.onIntent(myIntentRequest, session).getOutputSpeech()).getText());
        Assert.assertEquals("Soll die handleKontoUbersicht Methode auswählen", ((PlainTextOutputSpeech)myResponse.getOutputSpeech()).getText(), ((PlainTextOutputSpeech)sut.onIntent(myIntentRequest, session).getOutputSpeech()).getText());

    }
}
