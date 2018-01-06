package com.senacor.senacorProject;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public final class LambdaMethodHandler extends SpeechletRequestStreamHandler{
/* LambdaMethodHandler erbt von SpeechletRequestStreamHandler, eine vorgefertigte Klasse, die mit Lambda-Functions arbeitet.
 Diese muss erstmal importiert werden (s.o.)
Eine weitere Art wie man einen Handler schreiben könnte ist : SpeechletServlet.
Der Servlet ist aber eher für HTTP-Webanwendungen geeignet.
*/

    private static final Set <String> supportedApplicationIds;
/*Anlegen eines String-Sets namens supportedApplicationIds.
 SupporttedApplicationIds werden jedem Skill (Application) vergeben.
Kann man unter dem Developer-Account -> Alexa Skill -> LBB-Konto -> Edit -> Skill Information -> Application Id

*/

    static {

        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true");

        supportedApplicationIds=new HashSet<>();  //erstellen eines neuen HashSets (Elemente werden als Hashtabelle gespeichert,damit das Programm schneller auf sie zugreifen kann)
        //supportedApplicationIds.add("amzn1.ask.skill.935c5193-d548-42ed-b6b5-945469f6e418");

/*HashSet wird befüllt mit .add und unsere LBB-Konto ApplicationId wird eingetragen.
Mit der Id erlauben wir dem Handler auf unseren Skill zuzugreifen, wir können auch mehrere Ids hinzufügen (falls wir mehr Skills einbauen möchten)
*/
    }

    public LambdaMethodHandler() {
        super(new LBBSpeechlet(), supportedApplicationIds);
/*super (Konstruktor vom SpeechletRequestStreamHandler) hat Parameter: new speechlet(),supportedApplicationIds.
Vergeben also unser Speechlet und die SupportedApplicationIds:  new LBBSpeechlet(),supportedApplicationIds
*/  }


}
