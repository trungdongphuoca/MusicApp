package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdtu.finalapp.musicapp.Model.Playlist;


public class DAOPlaylist {

    private FirebaseUser currUser;
    private DatabaseReference root ;

    private ArrayList<Playlist> playlistsList ; // get all playlist
    public DAOPlaylist(){
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        root = db.getReference().child("Playlists").child(currUser.getUid());

        playlistsList =new ArrayList<>();
    }


    public Task<Void> add(Playlist p){

        return root.push().setValue(p);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap) {
        return root.child(key).updateChildren(hashMap);
    }

    //remove bth khi chưa tới final playlist
    public Task<Void> remove(String key)
    {
        return root.child(key).removeValue();
    }
    public Task<Void> remove() // remove the user to addplaylist
    {
        return root.removeValue();
    }

    public Query getRoot()
    {
        return root;
    }

    public List<Playlist> getAllPlaylist(){
        getRoot().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Playlist p = dataSnapshot.getValue(Playlist.class);
                   if(p!= null){
                       p.setKey(dataSnapshot.getKey());
                       playlistsList.add(p);
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return playlistsList;
    }
}
