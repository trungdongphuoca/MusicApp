package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tdtu.finalapp.musicapp.Adapter.AdapterSong;
import tdtu.finalapp.musicapp.Adapter.AdapterSongOfPlaylist;
import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.PlayMusic.MyMediaPlayer;
import tdtu.finalapp.musicapp.R;

public class DetailPlaylistActivity extends AppCompatActivity {
    private ImageView backInPlayMusic;
    private TextView name_playlist;
    private RecyclerView recyclerView;
    private TextView noSong;
    private Playlist passValue_playlist;
    private List<Song> lstSongOfPlaylist;

    ArrayList<Song> SongsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_playlist);

        backInPlayMusic = findViewById(R.id.backInPlayMusic);
        name_playlist = findViewById(R.id.name_playlist);
        recyclerView = findViewById(R.id.playlistRV);
        noSong = findViewById(R.id.noSong);

        backInPlayMusic.setOnClickListener(v->startActivity(new Intent(DetailPlaylistActivity.this,PlaylistActivity.class)));
        passValue_playlist = (Playlist) getIntent().getSerializableExtra("PLAYLIST");
        name_playlist.setText(passValue_playlist.getName());


        setSongIntoRecycleView();
    }
    void setSongIntoRecycleView(){
        lstSongOfPlaylist = passValue_playlist.getListSong();
        if(lstSongOfPlaylist != null){
            noSong.setVisibility(View.GONE);

            for(Song s: lstSongOfPlaylist){
                if(new File(s.getPath()).exists())
                    SongsArrayList.add(s);
            }
            if(SongsArrayList.size() <=0){
                noSong.setVisibility(View.VISIBLE);
            }else{
                noSong.setVisibility(View.GONE);
                //recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);
                AdapterSongOfPlaylist adapterSong = new AdapterSongOfPlaylist(DetailPlaylistActivity.this, SongsArrayList,passValue_playlist);
                recyclerView.setAdapter(adapterSong);
                adapterSong.notifyDataSetChanged();
            }
        }
        else{
            noSong.setVisibility(View.VISIBLE);
        }
    }
//    public void restartRecycleView(){
//        playlistsList.clear();
//        AddPlaylistIntoRecycleView();
//    }
}