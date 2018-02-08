package com.senacor.senacorProject;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LBBSpeechlet implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(LBBSpeechlet.class);
    private static final String INTENT_WHATSMYKONTOSTAND="Kontostand";
    private static final String INTENT_WHATSMYLIMIT="Limit";
    private static final String INTENT_WHATSMYKONTOSTANDLIMIT="Kontoubersicht";
    private static final String INTENT_HELP="AMAZON.HelpIntent";
    private static final String INTENT_STOP="AMAZON.StopIntent";
    private static final String INTENT_JA="AMAZON.YesIntent";
    private static final String INTENT_NEIN="AMAZON.NoIntent";
    Konto konto = new Konto();
    boolean kontostand = false;
    boolean limit = false;

    public static void main(String[] args) throws Exception {

        LBBSpeechlet mySpeechlet = new LBBSpeechlet();
        mySpeechlet.handle_weiterer_Kontostand();
    }


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                       "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        System.out.println("Session:" + session + " Intent:" + request.getIntent().getName());
        String intentName = request.getIntent().getName();
        if(INTENT_WHATSMYKONTOSTAND.equals(intentName))
        {
            return handleKontostand();
        }
        else if (INTENT_WHATSMYLIMIT.equals(intentName))
        {
            return handleLimit();
        }
        else if (INTENT_WHATSMYKONTOSTANDLIMIT.equals(intentName))
        {
            return handleKontoubersicht();
        }
        else if (INTENT_HELP.equals(intentName))
        {
            return handleHelpIntent();
        }
        else if (INTENT_STOP.equals(intentName))
        {
            return handleStopIntent();
        }
        else if (INTENT_JA.equals(intentName))
        {
            return handleJaIntent(kontostand, limit);
        }
        else if (INTENT_NEIN.equals(intentName))
        {
            return handleStopIntent();
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

    private SpeechletResponse handleKontostand() {

        try {
            limit = true;
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Kontostand beträgt " + konto.getKontostand() + " Euro. Möchten Sie noch Ihr monatliches Limit erfahren");
            System.out.println(speech.getText());

            return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse handle_weiterer_Kontostand() {

        try {
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Kontostand beträgt " + konto.getKontostand() + " Euro. Vielen Dank und bis zum nächsten Mal.");
            System.out.println(speech.getText());

            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse handleLimit() {

        try {
            kontostand = true;
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Limit beträgt " + konto.getLimit() + " Euro. Möchten Sie noch Ihren Kontostand erfahren?");
            System.out.println(speech.getText());

            return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   private SpeechletResponse handle_weiteres_Limit() {

        try {
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Limit beträgt " + konto.getLimit() + " Euro. Vielen Dank und bis zum nächsten Mal.");
            System.out.println(speech.getText());

            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse handleStopIntent() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Vielen Dank und bis zum nächsten Mal.");
        System.out.println(speech.getText());
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse handleHelpIntent() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Ich habe Sie nicht verstanden. Bitte fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder nach Ihrem Limit mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie Ihr Limit und den Kontostand.");
        return SpeechletResponse.newTellResponse(speech);
    }



    private SpeechletResponse handleJaIntent(boolean willkontostandwissen, boolean willlimitwissen){
        if(willkontostandwissen == true)
        {
            return handle_weiterer_Kontostand();
        }
        else if(willlimitwissen == true)
        {
            return handle_weiteres_Limit();
        }

        return null;
    }

    private SpeechletResponse handleKontoubersicht(){

        try {
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Kontostand beträgt "+ konto.getKontostand() +" Euro und ihr Limit "+ konto.getLimit()+" Euro. Vielen Dank und bis zum nächsten Mal.");
            System.out.println(speech.getText());
            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Reprompt createRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                               "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }
}

