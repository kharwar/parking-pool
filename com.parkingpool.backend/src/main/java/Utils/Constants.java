package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class Constants {
    public static Statement stmt;
    public static Connection conn;
    public static Synthesizer synthesizer;
    public static Scanner sc = new Scanner(System.in);
    public static void setStatement(Statement statement){
        stmt = statement;
    }
    public static void setConnection(Connection connection) { conn = connection; }

    public static Synthesizer initializeSpeechEngine() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        try {
            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
        }catch (Exception e)
        {
            System.out.println("Something is wrong with speech engine");

        }
        return synthesizer;
    }

    public static Synthesizer getSynthesizer(){
        if(synthesizer!=null)
        {
            return synthesizer;
        }
        synthesizer = initializeSpeechEngine();
        return synthesizer;
    }

    public static void printAndSpeak(String str)
    {
        if(synthesizer==null)
        {
            synthesizer = initializeSpeechEngine();
        }
        System.out.println(str);
        synthesizer.speakPlainText(str, null);
        try {
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with waitEngine");
        }
    }

}
