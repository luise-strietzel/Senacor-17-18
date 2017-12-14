package com.senacor.senacorProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTestSendGet {

    HttpClient sut;

    @Before  //erzeugt jedes mal neue Instanz
    public void setup(){
        sut = new HttpClient();    //sut= System under test
    }


    @Test

    public void testSendGet(){



    }


    @After  //räumt auf. Beim Testen mit der Datenbank nützlich,da Daten eingelesen und gespeichert werden
    public void tearDown(){

    }

}
