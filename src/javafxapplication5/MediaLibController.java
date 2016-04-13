/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import java.io.File;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ameen
 */
public class MediaLibController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Stage stage;
    TimbrePlayer p = new TimbrePlayer();
    @FXML
    private TableView<AllSongs> songList;
    @FXML
    private TableColumn<AllSongs, String> TrackName;
    @FXML
    private TableColumn<AllSongs, String> Artist;
    @FXML
    private TableColumn<AllSongs, String> Album;
    @FXML
    private TableColumn<AllSongs, String> Year;
    @FXML
    private TableColumn<AllSongs, String> Loc;

    ObservableList<AllSongs> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //updateAllSongs();
        TrackName.setCellValueFactory(new PropertyValueFactory<AllSongs, String>("Title"));
        Artist.setCellValueFactory(new PropertyValueFactory<AllSongs, String>("Artist"));
        Album.setCellValueFactory(new PropertyValueFactory<AllSongs, String>("Album"));
        Year.setCellValueFactory(new PropertyValueFactory<AllSongs, String>("Year"));
        updateAllSongs();
        songList.setItems(data);
        songList.setRowFactory(tv -> {
            TableRow<AllSongs> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    AllSongs rowData = row.getItem();
                    System.out.println("DATA:" + rowData.Location);
                    p.play(rowData.Location);
                }
            });
            return row;
        });

    }

    @FXML
    public void addFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open Directory");
        File file = chooser.showDialog(new Stage());
        String s = file + "";
        System.out.println(s);
        p.extract(s);
        updateAllSongs();
    }

    @FXML
    public void updateAllSongs() {
        try {
            ResultSet rs;
            Statement st;
            Connection conn;
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/TimbreDB", "root", "root");
            st = conn.createStatement();
            int i = 0, h = 0;
            String str;
            try {

                String ste = "SELECT COUNT(TRACKNAME) FROM ALLSONGS";
                rs = st.executeQuery(ste);
                rs.next();
                h = rs.getInt(1);
                System.err.println("hello:" + h);
            } catch (Exception err) {
                System.err.println(err);

            }
            try {

                str = "SELECT TRACKNAME,ARTIST,ALBUM,RELEASEYEAR,LOCATION FROM ALLSONGS";
                rs = st.executeQuery(str);

                while (rs.next()) {
                    //System.out.println(rs.getString("NAME") + " " + rs.getInt("ROLLNO"));
                    data.add(new AllSongs(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    ));
                    //System.out.println(rs.getString(1));
                }

// TODO add your handling code here:
            } catch (Exception ex) {
                // Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MediaLibController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    public void switchToMedia(Event event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MediaLibrary.fxml"));

            Scene scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    public void switchToNowPlaying(Event event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

            Scene scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    public void switchToPlaylist(Event event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PlaylistLib.fxml"));

            Scene scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
