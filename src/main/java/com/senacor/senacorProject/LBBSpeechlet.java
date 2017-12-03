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


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        // Initial wird mit einem 6-seitigen Würfel gestartet
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Willkommen im LBB-Konto.Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand.");
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        System.out.println("Session:"+session+ " Intent:"+request.getIntent().getName());
        String intentName = request.getIntent().getName();
        if(INTENT_WHATSMYKONTOSTAND.equals(intentName))
        {
            return handleKontostand(session);
        }
        else if (INTENT_WHATSMYLIMIT.equals(intentName))
        {
            return handleLimit(session);
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


    private SpeechletResponse handleKontostand(Session session) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("ich habe eine gewürfelt.");
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
    }

    private SpeechletResponse handleLimit(Session session){
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("ich habe eine gewürfelt.");
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
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

    private SpeechletResponse handleChooseDice(Intent intent, Session session) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        if (intent.getSlot(SLOT_NUMBEROFSIDES).getValue() == null) {
            speech.setText("ich habe nicht verstanden wieviele seiten der würfel haben soll.");
        } else {
            Integer numberOfSides = Integer.valueOf(intent.getSlot(SLOT_NUMBEROFSIDES).getValue().toString());
            session.setAttribute(SESSION_NUMBEROFSIDES, numberOfSides);
            speech.setText("ich benutze jetzt einen " + numberOfSides.toString() + " seitigen würfel");
        }
        return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
    }
    */

    private Reprompt createRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("ich habe dich nicht verstanden");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }
}
