/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import java.lang.String;

/**
 *
 * @author Ameen
 */
public class AllSongs {

    String Title;
    String Artist;
    String Album;
    String Year;
    String Location;

    public String getLocation() {
        return Location;
    }

    public AllSongs(String Title, String Artist, String Album, String Year, String Location) {
        this.Title = new String(Title);
        this.Artist = new String(Artist);
        this.Album = new String(Album);
        this.Year = new String(Year);
        this.Location = new String(Location);
    }

    public String getAlbum() {
        return Album;
    }

    public String getArtist() {
        return Artist;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

}
