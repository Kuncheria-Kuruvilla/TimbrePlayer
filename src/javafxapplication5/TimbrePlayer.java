/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ProgressBar;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javazoom.jl.decoder.Equalizer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

/**
 *
 * @author Ameen
 */
public class TimbrePlayer {

    Player player;
    Equalizer eq = new Equalizer();
    FileInputStream fis;
    BufferedInputStream bis;
    long PauseLocation;
    long SongLength;
    String FileLocation;
    boolean control = true;
    String songTable = "AllSongs";
    String presets[] = {"Custom", "Classical", "Club", "Dance", "Dubstep", "Full Bass",
        "Full Treble", "Full Bass + Treble", "HipHop", "Kuduro", "Laptop/Headphones",
        "Large Hall", "Live", "Party", "Pop", "Psychedelic", "Reggae", "Rock", "Soft", "Ska", "Soft Rock", "Techno", "Zero"};

    public static void main(String[] args) {

    }

    public void play(String path) {
        try {
            stop();
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            SongLength = fis.available();
            FileLocation = path + "";
        } catch (IOException ex) {
            System.err.println("CANNOT ASSIGN PLAYER");
        } catch (JavaLayerException ex) {
            System.err.println("CANNOT ASSIGN PLAYER");
        }
        control = true;
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    System.err.println("PLAYER CANNOT PLAY");
                }
            }
        }.start();
        setEqualizer(path);
    }

    public void stop() {
        control = false;
        if (player != null) {
            player.close();
            PauseLocation = SongLength;
        }
    }

    public void pause() {
        control = false;
        if (player != null) {
            try {
                PauseLocation = fis.available();
                player.close();
            } catch (IOException ex) {
                System.err.println("PLAYER NOT CLOSE");
            }

        }
    }

    public void resume() {
        if (control == false) {
            try {
                fis = new FileInputStream(FileLocation);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
                fis.skip(SongLength - PauseLocation);
            } catch (IOException ex) {
                System.err.println("PLAYER ISSUE");
            } catch (JavaLayerException ex) {
                System.err.println("CANNOT ASSIGN PLAYER");
            }
            control = true;
            new Thread() {

                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                        System.err.println("PLAYER CANNOT PLAY");
                    }
                }
            }.start();

        }
    }

    public void volChange(float vl) {
        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();
                    if (port.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        vl = vl / 100;
                        volume.setValue(vl);
                    }
                    port.close();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro\n" + e);
        }
    }

    public void slider(ProgressBar jb) {
        float pos;
        int intpos;
        if (fis != null) {
            try {
                pos = (SongLength - fis.available());
                pos = ((pos / SongLength) * 200);
                intpos = (int) pos;
                //jb.setValue(intpos);
                jb.setProgress(pos);
            } catch (IOException ex) {
                System.err.println("PLAYER ISSUE");
            }
        }
    }

    public void seekTo(int val) {
        try {
            fis = new FileInputStream(FileLocation);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            float floatval = (float) val;
            floatval = (float) (floatval / 200.0);
            floatval = (floatval * SongLength);
            fis.skip((long) floatval);

        } catch (IOException ex) {
            System.err.println("PLAYER ISSUE");
        } catch (JavaLayerException ex) {
            System.err.println("CANNOT ASSIGN PLAYER");
        }
        control = true;
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    System.err.println("PLAYER CANNOT PLAY");
                }
            }
        }.start();

    }

    public String genreToString(byte b) {
        String genre = new String();
        switch (b) {
            case 0:
                genre = "Blues";
                break;
            case 1:
                genre = "Classic Rock";
                break;
            case 2:
                genre = "Country";
                break;
            case 3:
                genre = "Dance";
                break;
            case 4:
                genre = "Disco";
                break;
            case 5:
                genre = "Funk";
                break;
            case 6:
                genre = "Grunge";
                break;
            case 7:
                genre = "HipHop";
                break;
            case 8:
                genre = "Jazz";
                break;
            case 9:
                genre = "Metal";
                break;
            case 10:
                genre = "New Age";
                break;
            case 11:
                genre = "Oldies";
                break;
            case 12:
                genre = "Other";
                break;
            case 13:
                genre = "Pop";
                break;
            case 14:
                genre = "R&B";
                break;
            case 15:
                genre = "Rap";
                break;
            case 16:
                genre = "Reggae";
                break;
            case 17:
                genre = "Rock";
                break;
            case 18:
                genre = "Techno";
                break;
            case 19:
                genre = "Industrial";
                break;
            case 20:
                genre = "Alternative";
                break;
            case 21:
                genre = "Ska";
                break;
            case 22:
                genre = "Death Metal";
                break;
            case 23:
                genre = "Pranks";
                break;
            case 24:
                genre = "Soundtrack";
                break;
            case 25:
                genre = "Euro-Techno";
                break;
            case 26:
                genre = "Ambient";
                break;
            case 27:
                genre = "Trip-Hop";
                break;
            case 28:
                genre = "Vocal";
                break;
            case 29:
                genre = "Jazz+Funk";
                break;
            case 30:
                genre = "Fusion";
                break;
            case 31:
                genre = "Trance";
                break;
            case 32:
                genre = "Classical";
                break;
            case 33:
                genre = "Instrumental";
                break;
            case 34:
                genre = "Acid";
                break;
            case 35:
                genre = "House";
                break;
            case 36:
                genre = "Game";
                break;
            case 37:
                genre = "Sound Clip";
                break;
            case 38:
                genre = "Gospel";
                break;
            case 39:
                genre = "Noise";
                break;
            case 40:
                genre = "AlternRock";
                break;
            case 41:
                genre = "Bass";
                break;
            case 42:
                genre = "Soul";
                break;
            case 43:
                genre = "Punk";
                break;
            case 44:
                genre = "Space";
                break;
            case 45:
                genre = "Meditative";
                break;
            case 46:
                genre = "Instrumental Pop";
                break;
            case 47:
                genre = "Instrumental Rock";
                break;
            case 48:
                genre = "Ethnic";
                break;
            case 49:
                genre = "Gothic";
                break;
            case 50:
                genre = "Darkwave";
                break;
            case 51:
                genre = "Techno-Industrial";
                break;
            case 52:
                genre = "Electronic";
                break;
            /* 53. Pop-Folk
 54. Eurodance
 55. Dream
 56. Southern Rock
 57. Comedy
 58. Cult
 59. Gangsta
 60. Top 40
 61. Christian Rap
 62. Pop/Funk
 63. Jungle
 64. Native American
 65. Cabaret
 66. New Wave
 67. Psychadelic
 68. Rave
 69. Showtunes
 70. Trailer
 71. Lo-Fi
 72. Tribal
 73. Acid Punk
 74. Acid Jazz
 75. Polka
 76. Retro
 77. Musical
 78. Rock & Roll
 79. Hard Rock*/
        }
        return genre;
    }

    public String matchPreset(String genre) {
        int l = presets.length;
        System.out.println("length:" + l);
        for (int i = 0; i < l; i++) {
            if (presets[i].contains(genre)) {
                return presets[i];
            }
        }
        return "Zero";
    }

    public float[] getEqualizerValue(String preset) {
        float[] value = new float[32];
        for (int i = 0; i < 32; i++) {
            value[i] = 0;
        }
        switch (preset) {
            case "Custom":
                for (int i = 0; i < 32; i++) {
                    value[i] = 0;
                }
                break;
            case "Zero":
                for (int i = 0; i < 32; i++) {
                    value[i] = 0;
                }
                break;
            case "Classical":
                value[2] = 0;
                value[5] = 0;
                value[8] = 0;
                value[11] = 0;
                value[14] = 0;
                value[17] = 0;
                value[20] = 0;
                value[23] = 0;
                value[26] = 0;
                value[29] = 0;
                break;
            case "Club":
                value[2] = 0;
                value[5] = 0;
                value[8] = 0;
                value[11] = 0;
                value[14] = 0;
                value[17] = 0;
                value[20] = (float) -0.40;
                value[23] = (float) -0.40;
                value[26] = (float) -0.40;
                value[29] = (float) -0.50;
                break;
            case "Dance":
                value[2] = (float) 0.50;
                value[5] = (float) 0.35;
                value[8] = (float) 0.10;
                value[11] = (float) 0;
                value[14] = (float) 0;
                value[17] = (float) -0.30;
                value[20] = (float) -0.40;
                value[23] = (float) -0.40;
                value[26] = (float) 0;
                value[29] = (float) 0;
                break;
            case "Dubstep":
                value[2] = (float) 0;
                value[5] = (float) 0.36;
                value[8] = (float) 0.85;
                value[11] = (float) 0.58;
                value[14] = (float) 0.30;
                value[17] = (float) 0;
                value[20] = (float) 0.36;
                value[23] = (float) 0.60;
                value[26] = (float) 0.96;
                value[29] = (float) 0.62;
                break;
            case "Pop":
                value[2] = (float) -0.10;
                value[5] = (float) 0.25;
                value[8] = (float) 0.35;
                value[11] = (float) 0.40;
                value[14] = (float) 0.25;
                value[17] = (float) -0.5;
                value[20] = (float) -0.15;
                value[23] = (float) -0.15;
                value[26] = (float) -0.10;
                value[29] = (float) -0.10;
                break;
            default:
                for (int i = 0; i < 32; i++) {
                    value[i] = 0;
                }
                break;

        }
        return value;
    }

    public void setEqualizer(String path) {
        try {
            File f = new File(path);
            String genre;
            String preset;
            byte genreNo;
            float[] eqSetting = new float[32];
            MP3File mpf = new MP3File(f);
            String title = new String();
            ID3v1 id = new ID3v1();
            id = mpf.getID3v1Tag();
            genreNo = id.getGenre();
            if (genreNo != -1) {
                genre = genreToString(genreNo);
                preset = matchPreset(genre);
                eqSetting = getEqualizerValue(preset);
                Timbre.p.eq.setFrom(eqSetting);

            } else {
                /*---------------------TO BE UPDATED-----------------------------------*/
            }
        } catch (IOException | TagException ex) {
            Logger.getLogger(TimbrePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void extract(String p) {
        File f = new File(p);
        File l[] = f.listFiles();
        DBHandler db = new DBHandler();
        File path;
        for (File x : l) {
            if (x == null) {
                return;
            }
            if (x.isHidden() || !x.canRead()) {
                continue;
            }
            if (x.isDirectory()) {
                extract(x.getPath());
            } else if (x.getName().endsWith(".mp3")) {
                path = new File(x.getPath());
                try {
                    MP3File mpf = new MP3File(path);
                    //Mp3File  mpf2 =new Mp3File(song);
                    String title = new String();
                    ID3v1 id = new ID3v1();
                    id = mpf.getID3v1Tag();
                    String titlename = id.getSongTitle();
                    String artist = id.getArtist();
                    String album = id.getAlbumTitle();
                    String year = id.getYearReleased();
                    //String genere = id.getSongGenre();
                    String loc = x.getPath();
                    String stmt = "INSERT INTO " + songTable + " VALUES('" + titlename + "','" + artist + "','" + album + "','" + year + "','" + loc + "')";
                    db.execute(stmt);
                } catch (IOException | TagException ex) {
                    //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

}
