package tdtu.finalapp.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.PlayMusic.MyMediaPlayer;
import tdtu.finalapp.musicapp.PlayMusic.PlayMusicActivity;
import tdtu.finalapp.musicapp.PlaylistInLibrary.PlaylistActivity;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolderPlaylist>{

    private Context context;
    private ArrayList<Playlist> playlistArraylist;

    public AdapterPlaylist(Context context, ArrayList<Playlist> playlistArraylist) {
        this.context = context;
        this.playlistArraylist = playlistArraylist;
    }

    @NonNull
    @Override
    public ViewHolderPlaylist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_playlist,parent,false);
        return new AdapterPlaylist.ViewHolderPlaylist(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlaylist holder, int position) {
        Playlist p = playlistArraylist.get(position);
        holder.titlePlaylist.setText(p.getName());

        holder.menu_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list dsach (delete playlist)
                System.out.println("delete success");
            }
        });

        int fakePo = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to DetailPlaylist acitivty
                System.out.println(p.getName());
//                MyMediaPlayer.getInstance().reset();
//                MyMediaPlayer.currentIndex = fakePo;
//                Intent intent = new Intent(context, PlayMusicActivity.class);
//                intent.putExtra("LIST_SONG",playlistArraylist);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

            }
        });
    }
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference root = db.getReference().child("Playlists").child(currUser.getUid());
    @Override
    public int getItemCount() {
        return playlistArraylist.size();
    }

    public class ViewHolderPlaylist extends RecyclerView.ViewHolder{

        ImageView imgSong;
        TextView titlePlaylist;
        ImageView menu_playlist;
        public ViewHolderPlaylist(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_playlist);
            titlePlaylist = itemView.findViewById(R.id.name_playlist);
            menu_playlist = itemView.findViewById(R.id.menu_playlist);
        }
    }
}
