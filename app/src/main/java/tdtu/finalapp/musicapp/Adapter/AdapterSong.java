package tdtu.finalapp.musicapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdtu.finalapp.musicapp.MainPackage.MainActivity;
import tdtu.finalapp.musicapp.Model.Playlist;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.PlayMusic.MyMediaPlayer;
import tdtu.finalapp.musicapp.PlayMusic.PlayMusicActivity;
import tdtu.finalapp.musicapp.PlaylistInLibrary.DAOPlaylist;
import tdtu.finalapp.musicapp.PlaylistInLibrary.PlaylistActivity;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;
import tdtu.finalapp.musicapp.loginAndRegis.LoginActivity;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolderSong>{

    private Context context;
    private ArrayList<Song> songsArraylist;
    private DAOPlaylist daoPlaylist = new DAOPlaylist();
    List<Playlist> listPlaylist = daoPlaylist.getAllPlaylist();

    public AdapterSong(Context context, ArrayList<Song> songsArraylist) {
        this.context = context;
        this.songsArraylist = songsArraylist;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_songs,parent,false);
        return new AdapterSong.ViewHolderSong(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSong holder, int position) {
        Song s = songsArraylist.get(position);
        holder.titleSong.setText(s.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.titleSong.setTextColor(Color.parseColor("#FFA85CB5"));
        }else{
            holder.titleSong.setTextColor(Color.parseColor("#000000"));
        }
        int fake = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = fake;
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("LIST_SONG",songsArraylist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.menu.setOnClickListener(v-> {
            PopupMenu popupMenu = new PopupMenu(this.context, holder.menu);
            for (Playlist p : listPlaylist) {
                popupMenu.getMenu().add(p.getName());
            }
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    CharSequence title = menuItem.getTitle();
                    for (Playlist p: listPlaylist) {
                        if(p.getName().equals(title)){
                            System.out.println(s.getTitle());

                            addSongToPlaylist(s,p,fake);
                            return true;
                        }
                    }
//                    }
                    return false;
                }
            });
        });
    }
    void addSongToPlaylist(Song s,Playlist p,int po){
        List<Song> existSongInPlaylist = new ArrayList<>();

        if(p.getListSong() != null){
            existSongInPlaylist.addAll(p.getListSong());
        }
        if(!existSongInPlaylist.contains(s)){

            existSongInPlaylist.add(s);

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("name",p.getName());
            hashMap.put("listSong",existSongInPlaylist);
            existSongInPlaylist.forEach(System.out::println);
            daoPlaylist.update(p.getKey(),hashMap).addOnSuccessListener(succ->{

                ToastNotification.makeTextToShow(context,"add " + s.getTitle() + " into " + p.getName()+" playlist successfully");
                notifyDataSetChanged();
//                ((Activity)context).finish();

            }).addOnFailureListener(err->{
                ToastNotification.makeTextToShow(context,err.getMessage());
            });
        }else{
            ToastNotification.makeTextToShow(context,s.getTitle() +" is exist in " + p.getName() + " playlist");
        }

    }

    @Override
    public int getItemCount() {
        return songsArraylist.size();
    }

    public class ViewHolderSong extends RecyclerView.ViewHolder{

        ImageView imgSong;
        TextView titleSong;
        ImageView menu;
        public ViewHolderSong(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_Song);
            titleSong = itemView.findViewById(R.id.nameSong);
            menu = itemView.findViewById(R.id.menu_song);

        }
    }
}
