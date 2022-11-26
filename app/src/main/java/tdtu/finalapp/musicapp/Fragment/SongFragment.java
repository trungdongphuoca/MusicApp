package tdtu.finalapp.musicapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Adapter.AdapterSong;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.R;

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
    private ArrayList<Song> SongsArrayList;
    private String[] SongsTitle;
    private int[] ImgResouceID;
    private RecyclerView recycleView;

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

        dataInitialize();

        recycleView  =view.findViewById(R.id.RecycleViewSong);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setHasFixedSize(true);
        AdapterSong adapterSong = new AdapterSong(getContext(),SongsArrayList);
        recycleView.setAdapter(adapterSong);
        adapterSong.notifyDataSetChanged();
    }

    private void dataInitialize() {
        SongsArrayList = new ArrayList<>();

        SongsTitle = new String[] {
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
                getString(R.string.hello_blank_fragment),
        };

        ImgResouceID = new int[]{
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24,
                R.drawable.ic_baseline_music_note_24
        };
        for (int i = 0; i <SongsTitle.length ; i++) {
            Song song = new Song(SongsTitle[i],ImgResouceID[i]);
            SongsArrayList.add(song);
        }
    }
}