package com.senacor.senacorProject;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LBBSpeechlet implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(LBBSpeechlet.class);
    private static final String INTENT_WHATSMYKONTOSTAND="Kontostand";
    private static final String INTENT_WHATSMYLIMIT="Limit";
    private static final String INTENT_YES="Ja";
    private static final String INTENT_NO="Nein";


    public static void main(String[] args) throws Exception {

        LBBSpeechlet mySpeechlet = new LBBSpeechlet();
        mySpeechlet.handleKontostand();
        mySpeechlet.handleLimit();
    }


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        // Initial wird mit einem 6-seitigen Würfel gestartet
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                "mit dem Wort Limit.");
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        System.out.println("Session:"+session+ " Intent:"+request.getIntent().getName());
        String intentName = request.getIntent().getName();
        if(INTENT_WHATSMYKONTOSTAND.equals(intentName))
        {
            return handleKontostand();
        }
        else if (INTENT_WHATSMYLIMIT.equals(intentName))
        {
            return handleLimit();
        }
        else
        {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }



    //  private SpeechletResponse handleKontostand(Session session) {
    private SpeechletResponse handleKontostand() {
        Konto konto = new Konto();
        //System.out.println("wir testen die Methode handleKontostand");
        try {
            //JsonElement myKontostand = myClient.sendGet(myClient.sendPost());
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro. Vielen Dank, bis zum nächsten Mal.");
            System.out.println(speech.getText());
            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse handle_weiterer_Kontostand() {
        Konto konto = new Konto();
        //System.out.println("wir testen die Methode handle_weiterer_Kontostand");
        try {
            //JsonElement myKontostand = myClient.sendGet(myClient.sendPost());
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro. Vielen Dank, bis zum nächsten Mal.");
            System.out.println(speech.getText());
            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private SpeechletResponse handleLimit() {
        Konto konto = new Konto();

        //System.out.println("wir testen die Methode handleLimit");
        try {
            //JsonElement myKontostand = myClient.sendGet(myClient.sendPost());
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Limit beträgt " + konto.getLimit() + " Euro. Möchten Sie noch Ihren Kontostand erfahren?");
            System.out.println(speech.getText());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private SpeechletResponse handle_weiteres_Limit() {
        Konto konto = new Konto();
        //System.out.println("wir testen die Methode handleLimit");
        try {
            //JsonElement myKontostand = myClient.sendGet(myClient.sendPost());
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Limit beträgt " + konto.getLimit() + " Euro. Vielen Dank, bis zum nächsten Mal.");
            System.out.println(speech.getText());
            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

/*
    private SpeechletResponse handleStopIntent() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("auf wiedersehen.");
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse handleHelpIntent() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("bestimme wieviel seiten dein würfel hat oder würfle");
        return SpeechletResponse.newTellResponse(speech);
    }
    */

    private Reprompt createRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Ich habe Sie nicht verstanden. Bitte fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }
}
