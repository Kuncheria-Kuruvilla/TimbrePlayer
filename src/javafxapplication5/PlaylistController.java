/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ameen
 */
public class PlaylistController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
   @FXML
    public void onPlayPressed(Event e) {
        if (Timbre.p.control == false) {
            Timbre.p.resume();
            Timbre.p.control = true;
        } else {
            Timbre.p.pause();
            Timbre.p.control = false;
        }
    } 
    @FXML
    public void switchToMedia(Event event) throws IOException {
        try{
             Parent root = FXMLLoader.load(getClass().getResource("MediaScene.fxml"));

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
             Parent root = FXMLLoader.load(getClass().getResource("playlist.fxml"));

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
