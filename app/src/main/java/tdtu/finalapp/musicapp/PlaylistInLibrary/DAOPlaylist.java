package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

import tdtu.finalapp.musicapp.Model.Playlist;


public class DAOPlaylist {

    private FirebaseDatabase db;
    private FirebaseUser currUser;
    private DatabaseReference root ;

    public DAOPlaylist(){
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        db  = FirebaseDatabase.getInstance();
        root = db.getReference().child("Playlists").child(currUser.getUid());
    }


    public Task<Void> add(Playlist p){

        return root.push().setValue(p);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap) {
        return root.child(key).updateChildren(hashMap);
    }

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
}
