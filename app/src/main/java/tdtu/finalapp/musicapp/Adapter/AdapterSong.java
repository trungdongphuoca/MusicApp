package tdtu.finalapp.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.R;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolderSong>{

    Context context;
    ArrayList<Song> songsArraylist;

    public AdapterSong(Context context, ArrayList<Song> songsArraylist) {
        this.context = context;
        this.songsArraylist = songsArraylist;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_songs,parent,false);
        return new ViewHolderSong(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSong holder, int position) {
        Song s = songsArraylist.get(position);
        holder.titleSong.setText(s.getNameOfSong());
        holder.imgSong.setImageResource(s.getImgOfSong());

    }

    @Override
    public int getItemCount() {
        return songsArraylist.size();
    }

    public static class ViewHolderSong extends RecyclerView.ViewHolder{

        ShapeableImageView imgSong;
        TextView titleSong;

        public ViewHolderSong(@NonNull View itemView) {
            super(itemView);

            imgSong = itemView.findViewById(R.id.image_Song);
            titleSong = itemView.findViewById(R.id.nameSong);

        }
    }
}
