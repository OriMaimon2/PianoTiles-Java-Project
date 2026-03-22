import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

//This file is responsible for the audio that we hear during the game
//That means the volume, which song and more

public class AudioPlayer {

    private static Clip sounds;
    private static Clip music;

    private static FloatControl gainControlMusic;
    private static FloatControl gainControlSound;

    private static double musicVolume = 0.5;         // Music volume, between 0 and 1
    private static double soundVolume = 0.5;         // Sound volume, between 0 and 1

    // Method to play music in game
    /* song =
    0 - menu music
    1 - Despacito
    2- Gangam Style
    3 - Blinding lights
    4 - Levitating
     */

    public static void playMusic(int song) throws InputMismatchException {
        try {
            AudioInputStream menuMusic;
            switch (song) {
                case 0:
                    menuMusic = AudioSystem.getAudioInputStream(new File("res/menu_music.wav"));
                    break;
                case 1:
                    menuMusic = AudioSystem.getAudioInputStream(new File("res/Despacito.wav"));
                    break;
                case 2:
                    menuMusic = AudioSystem.getAudioInputStream(new File("res/Gangam.wav"));
                    break;
                case 3:
                    menuMusic = AudioSystem.getAudioInputStream(new File("res/Lights.wav"));
                    break;
                case 4:
                    menuMusic = AudioSystem.getAudioInputStream(new File("res/Levitating.wav"));
                    break;
                default:
                    throw new InputMismatchException("No such song");

            }
            AudioInputStream menuMusicLn = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100f, 16, 1, 2, 44100f, true), menuMusic);
            music = AudioSystem.getClip();
            music.open(menuMusicLn);
            gainControlMusic = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(musicVolume) / Math.log(10.0) * 20.0);
            gainControlMusic.setValue(dB);
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    // Method to play mouse click sound
    public static void playMouseSound(){
        try {
            AudioInputStream mouseSound = AudioSystem.getAudioInputStream(new File("res/mouse_click.wav"));
            AudioInputStream mouseSoundLn = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100f, 16, 1, 2, 44100f, true), mouseSound);
            sounds = AudioSystem.getClip();
            sounds.open(mouseSoundLn);
            gainControlSound = (FloatControl) sounds.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(soundVolume) / Math.log(10.0) * 20.0);
            gainControlSound.setValue(dB);
            sounds.loop(0);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    // Method to play firework sound
    public static void playFireworkSound(){
        try {
            AudioInputStream mouseSound = AudioSystem.getAudioInputStream(new File("res/fireworks.wav"));
            AudioInputStream mouseSoundLn = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100f, 16, 1, 2, 44100f, true), mouseSound);
            sounds = AudioSystem.getClip();
            sounds.open(mouseSoundLn);
            gainControlSound = (FloatControl) sounds.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(soundVolume) / Math.log(10.0) * 20.0);
            gainControlSound.setValue(dB);
            sounds.loop(0);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        music.stop();
    }

    public static void setMusicVolume(double volume){
        musicVolume = volume;
        float dB = (float) (Math.log(musicVolume) / Math.log(10.0) * 20.0);
        gainControlMusic.setValue(dB);
    }

    public static void setSoundVolume(double volume){
        soundVolume = volume;
        float dB = (float) (Math.log(soundVolume) / Math.log(10.0) * 20.0);
        gainControlSound.setValue(dB);
    }
}