package ProjectGUI;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceDirectory;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory;

public class Text2Speech {
    VoiceManager freeVM;
    Voice voice;

    public Text2Speech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceDirectory vd = new KevinVoiceDirectory();
        voice =  vd.getVoices()[0];


        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            voice.setRate(140);// Setting the rate of the voice
            voice.setPitch(190);// Setting the Pitch of the voice
            voice.setVolume(2);// Setting the volume of the voice
            try {
                SpeakText(words);// Calling speak() method
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void SpeakText(String words) {
        voice.speak(words);
    }

}
