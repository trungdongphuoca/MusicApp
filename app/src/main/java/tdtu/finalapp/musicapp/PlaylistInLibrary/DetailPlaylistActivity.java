package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tdtu.finalapp.musicapp.Adapter.AdapterSong;
import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.R;

public class DetailPlaylistActivity extends AppCompatActivity {
    private TextView name_playlist;
    private RecyclerView recyclerView;
    private TextView noSong;
    private Playlist passValue_playlist;
    private List<Song> lstSongOfPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_playlist);

        name_playlist = findViewById(R.id.name_playlist);
        recyclerView = findViewById(R.id.playlistRV);
        noSong = findViewById(R.id.noSong);

        passValue_playlist = (Playlist) getIntent().getSerializableExtra("PLAYLIST");

        name_playlist.setText(passValue_playlist.getName());
        System.out.println("hello");


        setSongIntoRecycleView();
    }
    void setSongIntoRecycleView(){
        lstSongOfPlaylist = passValue_playlist.getListSong();
//        List<Song> lstSong = lstSongOfPlaylist;

        if(lstSongOfPlaylist != null){
            ArrayList<Song> SongsArrayList = new ArrayList<>();
            for(Song s: lstSongOfPlaylist){
                if(new File(s.getPath()).exists())
                    SongsArrayList.add(s);
            }
//            if(SongsArrayList.size()==0){
//                noSong.setVisibility(View.VISIBLE);
//            }else{
                //recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailPlaylistActivity.this));
                recyclerView.setHasFixedSize(true);
                AdapterSong adapterSong = new AdapterSong(DetailPlaylistActivity.this, SongsArrayList);
                recyclerView.setAdapter(adapterSong);
                adapterSong.notifyDataSetChanged();
//            }
        }
    }
}