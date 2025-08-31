package org.example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer; 


public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        LibVosk.setLogLevel(LogLevel.DEBUG);

        try (Model model = new Model("model");
                    InputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                        new FileInputStream("")));
                        Recognizer recognizer = new Recognizer(model, 16000);) {

                    int nbytes; 
                    byte[] b = new byte[4096];
                    while ((nbytes = inputStream.read(b)) >= 0) {
                        if (recognizer.acceptWaveForm(b, nbytes)) {
                            System.out.println(recognizer.getResult());
                        } else {
                            System.out.println(recognizer.getPartialResult());
                        }
                    }
                    System.out.println(recognizer.getFinalResult());
        }
    }
}