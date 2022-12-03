package tdtu.finalapp.musicapp.Adapter;

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

import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.PlaylistInLibrary.DAOPlaylist;
import tdtu.finalapp.musicapp.PlaylistInLibrary.DetailPlaylistActivity;
import tdtu.finalapp.musicapp.PlaylistInLibrary.PlaylistActivity;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolderPlaylist>{

    private Context context;
    private ArrayList<Playlist> playlistArraylist;
    private DAOPlaylist daoPlaylist = new DAOPlaylist();



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
        Playlist p = null;
        this.onBindViewHolder(holder,position,p);
    }
    public void onBindViewHolder(@NonNull ViewHolderPlaylist holder, int position,Playlist p1) {



        ViewHolderPlaylist vhPlaylist = (ViewHolderPlaylist ) holder;

        Playlist p = p1==null ? playlistArraylist.get(position):p1;

        vhPlaylist.titlePlaylist.setText(p.getName());
        int fakePosition = position;

        //delete playlist (lỗi sai vị trí "position" của playlist))
        vhPlaylist.delete_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getKey()!= null){
                    daoPlaylist.remove(p.getKey()).addOnSuccessListener(suc->{
                        ToastNotification.makeTextToShow(context,"delete " + "'" + p.getName() + "'" + " successful");
                        notifyItemRemoved(fakePosition);
                        playlistArraylist.remove(p);
                    }).addOnFailureListener(err->{
                        ToastNotification.makeTextToShow(context,err.getMessage());
                    });
                }
                else{
                    daoPlaylist.remove().addOnSuccessListener(suc->{
                        ToastNotification.makeTextToShow(context,"delete "+"'" + p.getName() + "'" + " successful");
                        notifyItemRemoved(fakePosition);
                        playlistArraylist.remove(fakePosition);
                    }).addOnFailureListener(err->{
                        ToastNotification.makeTextToShow(context,err.getMessage());
                    });
                }

            }
        });

        //click playlist to show detail playlist and list all dsach
        vhPlaylist.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //navigate to DetailPlaylist acitivty
                Intent intent = new Intent(context, DetailPlaylistActivity.class);
                intent.putExtra("PLAYLIST", p);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistArraylist.size();
    }

    public class ViewHolderPlaylist extends RecyclerView.ViewHolder{

        ImageView imgSong;
        TextView titlePlaylist;
        ImageView delete_playlist;
        public ViewHolderPlaylist(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_playlist);
            titlePlaylist = itemView.findViewById(R.id.name_playlist);
            delete_playlist = itemView.findViewById(R.id.delete_playlist);
        }
    }
}
