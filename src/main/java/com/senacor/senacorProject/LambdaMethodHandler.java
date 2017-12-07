package com.senacor.senacorProject;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaMethodHandler implements RequestStreamHandler {

    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input stream
     * @param output  The Lambda function output stream
     * @param context The Lambda execution environment context object.
     * @throws IOException
     */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        context.getLogger().log("Input: " + input);
        output.write("hallo".getBytes());
    }

    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        return "Hello World - " + input;
    }
}
