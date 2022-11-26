package tdtu.finalapp.musicapp.Model;

public class Song {
    private String nameOfSong;
    private int ImgOfSong;

    public Song(String nameOfSong, int imgOfSong) {
        this.nameOfSong = nameOfSong;
        this.ImgOfSong = imgOfSong;
    }

    public String getNameOfSong() {
        return nameOfSong;
    }

    public void setNameOfSong(String nameOfSong) {
        this.nameOfSong = nameOfSong;
    }

    public int getImgOfSong() {
        return ImgOfSong;
    }

    public void setImgOfSong(int imgOfSong) {
        this.ImgOfSong = imgOfSong;
    }
}
