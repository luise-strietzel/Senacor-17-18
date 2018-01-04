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
    public void testOnIntent(String intent) throws SpeechletException {
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

        if(intent.equals(INTENT_WHATSMYLIMIT))
        {
            // springe in Methode handleLImit
            Assert.assertEquals("Soll die handleLimit Methode auswählen", speech.getText(), mySpeechlet.handleKontostand(session));

        }
        else if(intent.equals(INTENT_WHATSMYKONTOSTAND))
        {
            //springe in Methode handleKontostand
        }

        //SpeechletResponse speechletResponse = sut.onLaunch(launchRequest, session);
        //PlainTextOutputSpeech result = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();


    }

    @Test
    public void testOnIntentWhatsMyLimit() {
        // Hier Intent aufbauen für Whats my Limit
    }

    @Test
    public void testOnIntentWhatsMyKontostand() {
        // Hier Intent aufbauen für Whats my Kontostand

    }
}
