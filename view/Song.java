// Name:		Liam Davies,		Kevin Lee
// netid:		lmd312,				kjl156

package view;

public class Song {
	String song;
	String artist;
	String album;
	String year;

	public Song(String song, String artist, String album, String year) {
		this.song = song;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public Boolean equals(Song s) {
		if(s.artist.equals(this.artist) && s.song.equals(this.song))
			return true;
		else
			return false;
	}

	public String[] toArr() {
	    String[] retArr = new String[4];

	    retArr[0] = song;
	    retArr[1] = artist;
	    retArr[2] = album;
	    retArr[3] = year;

	    return retArr;
	}

	public String toString() {
		return "\"" + song + "\" by " + artist;
	}


}
