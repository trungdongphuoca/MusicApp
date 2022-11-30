package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import tdtu.finalapp.musicapp.Model.Playlist;


public class PlaylistFireBase {
    private DatabaseReference databaseReference;
    private FirebaseUser currUser;

    public PlaylistFireBase(){
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db  = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Playlists");
    }


    public Task<Void> add(Playlist p){

        return databaseReference.child(currUser.getUid()).push().setValue(p);
    }

    public Task<Void> addSongIntoPlaylist(Playlist p){

        return databaseReference.child(currUser.getUid()).child("-NI8UHwKsGOOB5B7KlGn").setValue(p);
    }

    public void getPlaylist(){
        databaseReference.child(currUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    System.out.println("fail");
                }
                else {
                    String rs = String.valueOf(task.getResult().child("-NI8BRX29J3GdN02bAFY").getValue());
//                    String[] rsSplit  = rs.split(", ");
//                    for(String s : rsSplit){
//                        System.out.println(s);
//                    }
                    System.out.println(rs);
                }
            }
        });
    }

}
