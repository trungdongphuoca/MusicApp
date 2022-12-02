package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.R;

public class DetailPlaylistActivity extends AppCompatActivity {
    private TextView name_playlist;
    private RecyclerView recyclerView;
    private TextView noSong;
    private Playlist pass_playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_playlist);

        name_playlist = findViewById(R.id.name_playlist);
        recyclerView = findViewById(R.id.playlistRV);
        noSong = findViewById(R.id.noSong);

        pass_playlist = (Playlist) getIntent().getSerializableExtra("PLAYLIST");

        name_playlist.setText(pass_playlist.getName());

    }
}