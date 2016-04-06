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
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * FXML Controller class
 *
 * @author Ameen
 */
public class FXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label label;
    @FXML
    private Slider volSlider;
    @FXML
    private ImageView albumArt;
    @FXML
    private ImageView backGround;
    @FXML
    private ProgressBar pBar;
    @FXML
    private ImageView play;
    
    TimbrePlayer P= new TimbrePlayer();
    Boolean PlayOrPause =false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
public void onPlayPressed(){
        try {
            String song="sample.mp3";
            Mp3File  mpf2 =new Mp3File(song);
            if(mpf2.hasId3v2Tag()){
                ID3v2 id3v2tag = mpf2.getId3v2Tag();
                    byte[] imageData = id3v2tag.getAlbumImage();
                    if (imageData!=null){
                        System.out.println("debug:: imageData is not null");
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                        Image image = SwingFXUtils.toFXImage(img, null);
                        albumArt.setImage(image);
                        backGround.setImage(image);
                    }
                    }
            if(PlayOrPause==false)
            {P.play(song);
            PlayOrPause=true;
            }
            else {
                P.pause();
                PlayOrPause=false;
            }      } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public void ss(){
    System.out.println("what the fuck");
}


    
}
