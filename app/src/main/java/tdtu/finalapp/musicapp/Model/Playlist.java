package tdtu.finalapp.musicapp.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class Playlist implements Serializable {
    @Exclude  // not add "key" property into firebase
    private String key;
    private String title;
    private List<String> listSong;

    public Playlist() {
    }

    public Playlist(String title, List<String> listSong) {
        this.title = title;
        this.listSong = listSong;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
