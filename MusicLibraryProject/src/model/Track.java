package model;

public class Track {
    private final Song song;  
    private int plays;         

    public Track(String title, String album, String artist) {
        this.song = new Song(title, album, artist); 
        this.plays = 0; 
    }
    
    public Track(String title, String album, String artist, int plays) {
    	this.song = new Song(title, album, artist);
    	this.plays = plays;
    }

    public String getTitle() {
        return this.song.getTitle(); 
    }

    public String getAlbum() {
        return this.song.getAlbum();  
    }

    public String getArtist() {
        return this.song.getArtist(); 
    }
    
    public Song getSong() {
    	return this.song;
    }

    public void incrementPlayCount() {
        this.plays++;  
    }

    public int getPlayCount() {
        return this.plays;  
    }

    @Override
    public String toString() {
        return song.toString() + " | Plays: " + this.plays;
    }
}
