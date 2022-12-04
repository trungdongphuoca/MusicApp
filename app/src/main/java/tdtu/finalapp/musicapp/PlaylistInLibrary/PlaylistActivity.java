package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import tdtu.finalapp.musicapp.Adapter.AdapterPlaylist;
import tdtu.finalapp.musicapp.MainPackage.MainActivity;
import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class PlaylistActivity extends AppCompatActivity {
    private ImageView backInPlayMusic;
    private LinearLayout playlistLinear;
    private RecyclerView RecycleViewPlaylist;
    private ArrayList<Playlist> playlistsList =new ArrayList<>();
    private DAOPlaylist dDAOPlaylist =new DAOPlaylist();

    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        backInPlayMusic = findViewById(R.id.backInPlayMusic);
        playlistLinear = findViewById(R.id.playlistLinear);
        RecycleViewPlaylist = findViewById(R.id.RecycleViewPlaylist);

        backInPlayMusic.setOnClickListener(v->startActivity(new Intent(PlaylistActivity.this, MainActivity.class)));
        playlistLinear.setOnClickListener(v->showCustomDialogAddPlaylist());

        AddPlaylistIntoRecycleView();

    }
    void AddPlaylistIntoRecycleView(){
        RecycleViewPlaylist.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewPlaylist.setHasFixedSize(true);
        AdapterPlaylist adapterPlaylist = new AdapterPlaylist(this, playlistsList);
        RecycleViewPlaylist.setAdapter(adapterPlaylist);

        //lay danh sach cac playlist ra va them vao object và list
        dDAOPlaylist.getRoot().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    key = dataSnapshot.getKey();
                    Playlist p = dataSnapshot.getValue(Playlist.class);
                    p.setKey(key);
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
        List<Playlist> checkAllPlaylist = dDAOPlaylist.getAllPlaylist();
        Dialog dialog  = new Dialog(PlaylistActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.custom_dialog_addplaylist);

        final EditText titlePlaylist = dialog.findViewById(R.id.name_playlist);
        final Button submitBtn = dialog.findViewById(R.id.confirmBtn);
        final Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

        submitBtn.setOnClickListener(v->{
            boolean checkExist = true;
            String title  = titlePlaylist.getText().toString();
            if(!title.equals("")){
                for(Playlist p1 : checkAllPlaylist){
                    if(p1.getName().equalsIgnoreCase(title)){
                        checkExist = false;
                    }
                }
                if(checkExist){
                    Playlist playlist = new Playlist(title,null);
                    addPlaylistIntoFirebase(playlist);
                    restartRecycleView();
                }
                else{
                    ToastNotification.makeTextToShow(this,"Name Playlist is exist!");
                }
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
        dDAOPlaylist.add(p).addOnSuccessListener(succ->{
            ToastNotification.makeTextToShow(PlaylistActivity.this,"add "+p.getName() + " successful");

        }).addOnFailureListener(err->{
            ToastNotification.makeTextToShow(PlaylistActivity.this,err.getMessage());
        });
    }

    public void restartRecycleView(){
        playlistsList.clear();
        AddPlaylistIntoRecycleView();
    }
}