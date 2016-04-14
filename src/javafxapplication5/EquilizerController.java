/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

/**
 * FXML Controller class
 *
 * @author Ameen
 */
public class EquilizerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Slider Hz32;
    @FXML
    Slider Hz64;
    @FXML
    Slider Hz125;
    @FXML
    Slider Hz250;
    @FXML
    Slider Hz500;
    @FXML
    Slider Hz1K;
    @FXML
    Slider Hz2K;
    @FXML
    Slider Hz4K;
    @FXML
    Slider Hz8K;
    @FXML
    Slider Hz16K;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Hz32.setValue(Timbre.p.eq.getBand(2));
        Hz64.setValue(Timbre.p.eq.getBand(5));
        Hz125.setValue(Timbre.p.eq.getBand(8));
        Hz250.setValue(Timbre.p.eq.getBand(11));
        Hz500.setValue(Timbre.p.eq.getBand(14));
        Hz1K.setValue(Timbre.p.eq.getBand(17));
        Hz2K.setValue(Timbre.p.eq.getBand(20));
        Hz4K.setValue(Timbre.p.eq.getBand(23));
        Hz8K.setValue(Timbre.p.eq.getBand(26));
        Hz16K.setValue(Timbre.p.eq.getBand(29));
       }

}
