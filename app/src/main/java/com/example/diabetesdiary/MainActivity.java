package com.example.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Locale;
import java.util.TimeZone;


/* TODO

 */
public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(new Locale("ru"));
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DiaryListFragment listFragment = new DiaryListFragment();
        transaction.add(R.id.list_container,listFragment);
        transaction.commit();
    }
}