package com.example.app;

// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String... args) throws Exception {
        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {
      
          // The path to the audio file to transcribe
          String gcsUri = args[0];
          
          // Builds the sync recognize request
          RecognitionConfig config =
              RecognitionConfig.newBuilder()
                  .setEncoding(AudioEncoding.OGG_OPUS)
                  .setSampleRateHertz(16000)
                  .setLanguageCode("es-ES")
                  .build();
          RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();
      
          // Performs speech recognition on the audio file
          RecognizeResponse response = speechClient.recognize(config, audio);
          List<SpeechRecognitionResult> results = response.getResultsList();
      
          for (SpeechRecognitionResult result : results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcription: %s%n", alternative.getTranscript());
          }
        }
      }
}
