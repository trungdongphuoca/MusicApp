package tdtu.finalapp.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.PlayMusic.MyMediaPlayer;
import tdtu.finalapp.musicapp.PlayMusic.PlayMusicActivity;
import tdtu.finalapp.musicapp.R;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolderSong>{

    private Context context;
    private ArrayList<Song> songsArraylist;

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

//        if(MyMediaplayer.currentIndex==position){
//            holder.titleSong.setTextColor(Color.parseColor("#FF0000"));
//        }else{
//            holder.titleSong.setTextColor(Color.parseColor("#000000"));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("LIST_SONG",songsArraylist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songsArraylist.size();
    }

    public class ViewHolderSong extends RecyclerView.ViewHolder{

        ShapeableImageView imgSong;
        TextView titleSong;

        public ViewHolderSong(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_Song);
            titleSong = itemView.findViewById(R.id.nameSong);

        }
    }
}
