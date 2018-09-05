package com.senacor.senacorProject;

import com.amazon.speech.slu.Intent;
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
    private static final String INTENT_USERNAME="Benutzername";
    private static final String INTENT_PASSWORT="Passwort";
    private static final String INTENT_HELP="AMAZON.HelpIntent";
    private static final String INTENT_STOP="AMAZON.StopIntent";
    private static final String INTENT_JA="AMAZON.YesIntent";
    private static final String INTENT_NEIN="AMAZON.NoIntent";
    Konto konto = new Konto();
    boolean kontostand = false;
    boolean limit = false;

    String username;
    String password;

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
        speech.setText("Willkommen im LBB-Konto. Bitte nennen Sie mir ihren Benutzernamen mit folgender Anweisung. Mein Benutzername ist.");
        System.out.println(speech.getText());
        return SpeechletResponse.newAskResponse(speech, benutzernameRepromptSpeech());
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
        else if (INTENT_USERNAME.equals(intentName))
        {
            return benutzername(request, session);
        }
        else if (INTENT_PASSWORT.equals(intentName))
        {
            return password(request, session);
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

    private SpeechletResponse benutzername(IntentRequest intent, Session session) {
        try {
            username = intent.getIntent().getSlots().get("username").getValue();

            //System.out.println( username);

            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Ihr Benutzername ist " + username + ". Bitte nennen Sie ihr Passwort mit folgender Anweisung. Mein Passwort ist.");

            session.setAttribute("username", username);
            System.out.println(speech.getText());

            return SpeechletResponse.newAskResponse(speech, passwordRepromptSpeech());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse password(IntentRequest intent, Session session) {
        try {
            password = intent.getIntent().getSlots().get("password").getValue();

            //System.out.println( password);

            //PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            //speech.setText("Anmeldeversuch");

            session.setAttribute("password", password);


            boolean anmeldungErfolgreich = konto.meldeAn(username, password);
            if (anmeldungErfolgreich) {
                return handleWillkommen();
            } else {
                return handleFalscheAnmeldung();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SpeechletResponse handleWillkommen() {
       try {
                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                speech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                        "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
                System.out.println(speech.getText());

                return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
             }
       catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    private static SpeechletResponse handleFalscheAnmeldung() {
        try {
                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                speech.setText("Ihr Benutzername oder Passwort war nicht richtig. Die Anmeldung ist fehlgeschlagen.");
                System.out.println(speech.getText());

                return SpeechletResponse.newTellResponse(speech);
            }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SpeechletResponse handleKontostand() {

        try {
            limit = true;
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            Float kontostand = konto.getKontostand();
            if (kontostand != null) {
                speech.setText("Ihr Kontostand beträgt " + kontostand + " Euro. Möchten Sie noch Ihr monatliches Limit erfahren");
            }else{
                speech.setText("Sie sind nicht angemeldet.");
            }
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
            Float kontostand = konto.getKontostand();
            if (kontostand != null) {
                speech.setText("Ihr Kontostand beträgt " + kontostand + " Euro. Vielen Dank und bis zum nächsten Mal.");
            } else {
                speech.setText("Sie sind nicht angemeldet.");
            }
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
            Float limit = konto.getLimit();
            if (limit != null) {
                speech.setText("Ihr Limit beträgt " + limit + " Euro. Möchten Sie noch Ihren Kontostand erfahren?");
            }else{
                speech.setText("Sie sind nicht angemeldet.");
            }
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
            Float limit = konto.getLimit();
            if (limit != null) {
                speech.setText("Ihr Limit beträgt " + limit + " Euro. Vielen Dank und bis zum nächsten Mal.");
            }else{
                speech.setText("Sie sind nicht angemeldet.");
            }
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
            Float kontostand = konto.getKontostand();
            Float limit = konto.getLimit();
            if (limit != null && kontostand != null) {
                speech.setText("Ihr Kontostand beträgt " + kontostand + " Euro und ihr Limit " + limit + " Euro. Vielen Dank und bis zum nächsten Mal.");
            }else{
                speech.setText("Sie sind nicht angemeldet.");
            }
            System.out.println(speech.getText());
            return SpeechletResponse.newTellResponse(speech);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Reprompt createRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Willkommen im LBB-Konto. Fragen Sie mich nach Ihrem Kontostand mit dem Wort Kontostand oder fragen Sie mich nach Ihrem Limit" +
                               "mit dem Wort Limit. Mit dem Wort Kontoübersicht erhalten Sie ihr Limit und Kontostand.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }

    private Reprompt passwordRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Ich habe sie nicht verstanden. Bitte widerholen sie ihr Passwort.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }

    private Reprompt benutzernameRepromptSpeech() {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("Ich habe sie nicht verstanden. Bitte widerholen sie ihren Benutzernamen.");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return reprompt;
    }
}

