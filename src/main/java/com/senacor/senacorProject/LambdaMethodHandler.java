package com.senacor.senacorProject;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public final class LambdaMethodHandler extends SpeechletRequestStreamHandler{

    private static final Set <String> supportedApplicationIds;

    static {

        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true");

        supportedApplicationIds=new HashSet<>();
    }

    public LambdaMethodHandler() {
        super(new LBBSpeechlet(), supportedApplicationIds);
    }


}
