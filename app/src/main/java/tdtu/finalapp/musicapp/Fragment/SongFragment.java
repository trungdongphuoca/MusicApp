package tdtu.finalapp.musicapp.Fragment;

import android.Manifest;
import android.app.Activity;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import tdtu.finalapp.musicapp.Adapter.AdapterSong;

import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recycleView;
    private TextView noSong;
    private ArrayList<Song> SongsArrayList =new ArrayList<>();



    public SongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongFragment newInstance(String param1, String param2) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView  =view.findViewById(R.id.RecycleViewSong);
        noSong = view.findViewById(R.id.noSongText);

        dataInitialize();




/*        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setHasFixedSize(true);
        AdapterSong adapterSong = new AdapterSong(getContext(),SongsArrayList);
        recycleView.setAdapter(adapterSong);
        adapterSong.notifyDataSetChanged();*/ // add into recycleView
    }


    private void dataInitialize() {

        if(checkPermission() == false){
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";


        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            Song songData = new Song(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
                SongsArrayList.add(songData);
        }
        if(SongsArrayList.size()==0){
            noSong.setVisibility(View.VISIBLE);
        }else{
            //recyclerview
            recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            recycleView.setHasFixedSize(true);
            AdapterSong adapterSong = new AdapterSong(getActivity().getApplicationContext(), SongsArrayList);
            recycleView.setAdapter(adapterSong);
            adapterSong.notifyDataSetChanged();
        }

    }
    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return  result == PackageManager.PERMISSION_GRANTED ?true : false;
    }
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            ToastNotification.makeTextToShow((Activity) getContext(),"READ PERMISSION IS REQUIRED, PLS ALLOW FROM SETTINGS");
        }
        else
            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    101);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recycleView!=null){
            recycleView.setAdapter(new AdapterSong(getActivity().getApplicationContext(),SongsArrayList));
        }
    }
}