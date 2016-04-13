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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    //TimbrePlayer P = new TimbrePlayer();
    Boolean PlayOrPause = false;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            String ste = "SELECT LOCATION FROM ALLSONGS";
            Statement st;
            ResultSet rs;
            Connection conn;
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/TimbreDB", "root", "root");
            st = conn.createStatement();
            rs=st.executeQuery(ste);
            rs.next();
            String path=rs.getString(1);
            Mp3File mpf2 = new Mp3File(path);
            if (mpf2.hasId3v2Tag()) {
                ID3v2 id3v2tag = mpf2.getId3v2Tag();
                byte[] imageData = id3v2tag.getAlbumImage();
                if (imageData != null) {
                    System.out.println("debug:: imageData is not null");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    albumArt.setImage(image);
                    backGround.setImage(image);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onPlayPressed(Event e) {
        try {
            String song = "sample.mp3";
            Mp3File mpf2 = new Mp3File(song);
            if (mpf2.hasId3v2Tag()) {
                ID3v2 id3v2tag = mpf2.getId3v2Tag();
                byte[] imageData = id3v2tag.getAlbumImage();
                if (imageData != null) {
                    System.out.println("debug:: imageData is not null");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                    Image image = SwingFXUtils.toFXImage(img, null);
                    albumArt.setImage(image);
                    backGround.setImage(image);
                }
            }
            if (PlayOrPause == false) {
                Timbre.p.play(song);
                PlayOrPause = true;
            } else {
                Timbre.p.pause();
                PlayOrPause = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    public void switchToMedia(Event event) throws IOException {
        try{
             Parent root = FXMLLoader.load(getClass().getResource("MediaLibrary.fxml"));

        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e){
            System.out.println(e);
        }
       
    }
    
    @FXML
    public void switchToNowPlaying(Event event) throws IOException {
        try{
             Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e){
            System.out.println(e);
        }
       
    }

    @FXML
    public void switchToPlaylist(Event event) throws IOException {
        try{
             Parent root = FXMLLoader.load(getClass().getResource("PlaylistLib.fxml"));

        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e){
            System.out.println(e);
        }
       
    }
}
