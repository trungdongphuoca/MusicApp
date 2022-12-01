package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import tdtu.finalapp.musicapp.Adapter.AdapterPlaylist;
import tdtu.finalapp.musicapp.Adapter.AdapterSong;
import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class PlaylistActivity extends AppCompatActivity {
    private LinearLayout playlistLinear;
    private RecyclerView RecycleViewPlaylist;
    private ArrayList<Playlist> playlistsList =new ArrayList<>();
    private PlaylistFireBase playlistFireBase =new PlaylistFireBase();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference root = db.getReference().child("Playlists").child(currUser.getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        playlistLinear = findViewById(R.id.playlistLinear);
        RecycleViewPlaylist = findViewById(R.id.RecycleViewPlaylist);

        playlistLinear.setOnClickListener(v->showCustomDialogAddPlaylist());

        AddPlaylistIntoRecycleView();

    }
    void AddPlaylistIntoRecycleView(){
        RecycleViewPlaylist.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewPlaylist.setHasFixedSize(true);
        AdapterPlaylist adapterPlaylist = new AdapterPlaylist(this, playlistsList);
        RecycleViewPlaylist.setAdapter(adapterPlaylist);

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Playlist p = dataSnapshot.getValue(Playlist.class);
                    playlistsList.add(p);
                }
                adapterPlaylist.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    void showCustomDialogAddPlaylist(){
        Dialog dialog  = new Dialog(PlaylistActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.custom_dialog_addplaylist);

        final EditText titlePlaylist = dialog.findViewById(R.id.name_playlist);
        final Button submitBtn = dialog.findViewById(R.id.confirmBtn);
        final Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

        submitBtn.setOnClickListener(v->{
            String title  = titlePlaylist.getText().toString();
            if(!title.equals("")){
                Playlist playlist = new Playlist(title,null);
                addPlaylistIntoFirebase(playlist);
                restartRecycleView();
                dialog.dismiss();
            }
            else{
                ToastNotification.makeTextToShow(this,"Pls fill name Playlist or cancel add playlist");

            }

        });

        cancelBtn.setOnClickListener(v->dialog.dismiss());

        dialog.show();
    }
    void addPlaylistIntoFirebase(Playlist p){
        playlistFireBase.add(p).addOnSuccessListener(succ->{
            ToastNotification.makeTextToShow(PlaylistActivity.this,"add "+p.getName() + " successful");
        }).addOnFailureListener(err->{
            ToastNotification.makeTextToShow(PlaylistActivity.this,err.getMessage());
        });
    }

    void restartRecycleView(){
        playlistsList.clear();
        AddPlaylistIntoRecycleView();
    }
}