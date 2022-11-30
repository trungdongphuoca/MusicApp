package tdtu.finalapp.musicapp.Model;

import java.util.List;

public class Playlist {
    private String title;
    private List<String> listSong;

    public Playlist() {
    }

    public Playlist(String title, List<String> listSong) {
        this.title = title;
        this.listSong = listSong;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public List<String> getListSong() {
        return listSong;
    }

    public void setListSong(List<String> listSong) {
        this.listSong = listSong;
    }
}
