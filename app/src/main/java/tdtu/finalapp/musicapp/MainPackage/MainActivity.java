package tdtu.finalapp.musicapp.MainPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Fragment.HomeFragment;
import tdtu.finalapp.musicapp.Fragment.LibraryFragment;
import tdtu.finalapp.musicapp.Fragment.SongFragment;
import tdtu.finalapp.musicapp.R;

public class MainActivity extends AppCompatActivity {
//    private ViewPager viewPager;
//    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        viewPager = findViewById(R.id.viewPage);
//        tabLayout = findViewById(R.id.tab_layout);


        initViewPage();
    }

    private void initViewPage() {
        ViewPager viewPager = findViewById(R.id.viewPage);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragments(new HomeFragment(),"Home");
        viewPageAdapter.addFragments(new SongFragment(),"Song");
        viewPageAdapter.addFragments(new LibraryFragment(),"Library");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public static class ViewPageAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        void addFragments(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }
    }

}