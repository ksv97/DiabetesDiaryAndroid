package com.example.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Locale;


/* TODO
    1. Сделать выпадающий Time-picker и Date-picker в добавлении
    2. Изменить EditText на TextView
    3. Попробовать сделать фрагмент и добавить его в ListView - для Edit/Add в листе.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(new Locale("ru"));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DiaryListFragment listFragment = new DiaryListFragment();
        transaction.add(R.id.list_container,listFragment);
        transaction.commit();
    }
}