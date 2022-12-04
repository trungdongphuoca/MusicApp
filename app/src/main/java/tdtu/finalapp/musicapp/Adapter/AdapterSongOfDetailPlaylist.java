package tdtu.finalapp.musicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.PlayMusic.MyMediaPlayer;
import tdtu.finalapp.musicapp.PlayMusic.PlayMusicActivity;
import tdtu.finalapp.musicapp.PlaylistInLibrary.DAOPlaylist;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class AdapterSongOfDetailPlaylist extends RecyclerView.Adapter<AdapterSongOfDetailPlaylist.ViewHolderSongPlaylist> {


    private Context context;
    private ArrayList<Song> songsArraylist;
    private DAOPlaylist daoPlaylist = new DAOPlaylist();
    private Playlist playlist;


    public AdapterSongOfDetailPlaylist(Context context, ArrayList<Song> songsArraylist, Playlist p1) {
        this.context = context;
        this.songsArraylist = songsArraylist;
        this.playlist = p1;
    }

    @NonNull
    @Override
    public ViewHolderSongPlaylist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_song_playlist,parent,false);
        return new AdapterSongOfDetailPlaylist.ViewHolderSongPlaylist(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSongPlaylist holder, @SuppressLint("RecyclerView") int position) {
        Song s = songsArraylist.get(position);

        holder.titleSong.setText(s.getTitle());


        //play nhac
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                MyMediaPlayer.FakeIndex = position;
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("LIST_SONG",songsArraylist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        //delete song from playlist
        holder.menu_delete.setOnClickListener(v-> {

            List<Song> allSongInPlaylist = new ArrayList<>();
            allSongInPlaylist.addAll(playlist.getListSong());

            allSongInPlaylist.remove(s);
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("name",playlist.getName());
            hashMap.put("listSong",allSongInPlaylist);

            daoPlaylist.update(playlist.getKey(),hashMap).addOnSuccessListener(succ->{
                ToastNotification.makeTextToShow(context,"delete " + s.getTitle() + " from " + playlist.getName()+" playlist successfully");
                songsArraylist.remove(s);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position,songsArraylist.size());
                playlist.setListSong(songsArraylist);
            }).addOnFailureListener(err->{
                ToastNotification.makeTextToShow(context,err.getMessage());
            });
        });

    }

    @Override
    public int getItemCount() {
        return songsArraylist.size();
    }

    public class ViewHolderSongPlaylist extends RecyclerView.ViewHolder{

        ImageView imgSong;
        TextView titleSong;
        ImageView menu_delete;
        public ViewHolderSongPlaylist(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_Song);
            titleSong = itemView.findViewById(R.id.nameSongPlaylist);
            menu_delete = itemView.findViewById(R.id.menu_delete_song);

        }
    }

}
