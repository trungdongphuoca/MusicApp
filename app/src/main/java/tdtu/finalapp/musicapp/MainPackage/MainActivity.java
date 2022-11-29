package tdtu.finalapp.musicapp.MainPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Fragment.HomeFragment;
import tdtu.finalapp.musicapp.Fragment.LibraryFragment;
import tdtu.finalapp.musicapp.Fragment.SongFragment;
import tdtu.finalapp.musicapp.PlayMusic.PlayMusicActivity;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;
import tdtu.finalapp.musicapp.loginAndRegis.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView userImg,searchImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchImg = findViewById(R.id.SearchBtn);
        userImg = findViewById(R.id.UserBtn);
        initViewPage();

        userImg.setOnClickListener(v->showUser());
    }
    void showUser(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,userImg);
        popupMenu.getMenu().add("See your profile");
        popupMenu.getMenu().add("Sign out");
        popupMenu.getMenu().add("Exit");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                CharSequence title = menuItem.getTitle();
                if ("Sign out".equals(title)) {
                    FirebaseAuth.getInstance().signOut();
                    ToastNotification.makeTextToShow(MainActivity.this, "Sign out successful");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    return true;
                } else if ("See your profile".equals(title)) {
                    ToastNotification.makeTextToShow(MainActivity.this, "Your profile will present now!!");
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
                    return true;
                } else if ("Exit".equals(title)) {
                    ToastNotification.makeTextToShow(MainActivity.this, "Exit app");
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void initViewPage() {
        ViewPager viewPager = findViewById(R.id.viewPage);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
//        viewPageAdapter.addFragments(new HomeFragment(),"Home");
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