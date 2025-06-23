package Base;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

// Most of this class was made by AI, or I looked it up. I would say 70-80 percent
public class PlaySound {

    private Clip clip;

    /**
     * (this mehthod was written by me)
     * This method chooses a random song to play
     * then passes it to playSound
     */
    public void getReadyToPlaySound()
    {
        Random rn = new Random();
        int randomNum = rn.nextInt(5);

        switch (randomNum) {
            case 0:
                playsound("src/AllAudio/BackgroundMusic/Yeat -  sega mäde!.wav");
                break;
            case 1:
                playsound("src/AllAudio/BackgroundMusic/Yeat - Kant change.wav");
                break;
            case 2:
                playsound("src/AllAudio/BackgroundMusic/Yeat - Money so big.wav");
                break;
            case 3:
                playsound("src/AllAudio/BackgroundMusic/Yeat - On tha line.wav");
                break;
            case 4:
                playsound("src/AllAudio/BackgroundMusic/Yeat - Swerved It.wav");
                break;
        }
    }

    /**
     * This method takes a file path and makes a file out of it
     * then makes a clip of it then plays the music
     * it also checks for volume changes and the music to end
     * @param filePath the path to the song that was chosen in getReadyToPlaySound
     */
    public void playsound(String filePath)
    {
        try
        {
            File soundFile = new File(filePath);// This makes a File of the sound
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);// Create an AudioInputStream from the sound file to process the audio data
            Clip clip = AudioSystem.getClip();//this makes a clip of the audio

            // Add a LineListener to detect when the audio finishes
            clip.addLineListener(new LineListener()
            {
                @Override
                public void update(LineEvent event)
                {
                    if (event.getType() == LineEvent.Type.STOP)
                    {
                        //audio has finished playing
                        getReadyToPlaySound();//plays random song
                    }
                }
            });
            
            clip.open(audioStream);// Opens clip to play and change the volume

            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(SettingsWindow.musicVolumeF);

            clip.start();//plays the clip

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * this takes in volume and sets the value of volumeControl to the volume
     * @param volume this is the volume were changing to.
     */
    public void setVolume(float volume)
    {
        if (clip != null && clip.isOpen())
        {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }
    }
}
