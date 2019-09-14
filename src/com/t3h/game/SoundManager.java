package com.t3h.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {
    static void play(String name){
        try{
            File f = new File("src/sound/"+name);
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception ex){
            ex.printStackTrace();}
    }
}
