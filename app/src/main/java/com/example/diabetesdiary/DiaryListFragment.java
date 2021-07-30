package com.example.diabetesdiary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DiaryListFragment extends Fragment {

    DiaryDb db;
    Handler handler;
    FloatingActionButton fabAdd;
    DiaryItemArrayAdapter adapter;

    public DiaryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_diary_list, container, false);
        fabAdd = (FloatingActionButton)v.findViewById(R.id.btnAddItem);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =  getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.details_container, new AddDiaryItemFragment(AddDiaryItemFragment.ACTION_TYPE.ACTION_ADD, null));
                transaction.commit();

            }
        });

        db = new DiaryDb(getContext());
        adapter = new DiaryItemArrayAdapter(getContext(), db.selectAll() );
        ((ListView)v.findViewById(R.id.items_list)).setAdapter(adapter);


        return v;
    }

    public void updateList() {
        adapter.setItems(db.selectAll());
        adapter.notifyDataSetChanged();
    }
}