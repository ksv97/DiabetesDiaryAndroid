package com.example.diabetesdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(new Locale("ru"));
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.diary:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.list_container, new DiaryListFragment())
                                .commit();
                        break;
                    case R.id.statistics:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.list_container, new StatisticsFragment())
                                .commit();
                        break;
                    case R.id.preferences:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.list_container, new PreferencesFragment())
                                .commit();
                        break;
                }

                return true;
            }
        };

        bottomNavigationView.setOnItemSelectedListener(listener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_container, new DiaryListFragment())
                .commit();

        // удаляем данные 3-хмесячной давности с целью освобождения памяти в отдельном потоке
        new Thread(new Runnable() {
            @Override
            public void run() {
                DiaryDb db = new DiaryDb(MainActivity.this);
                db.deleteOldData();
            }
        }).start();
    }


}