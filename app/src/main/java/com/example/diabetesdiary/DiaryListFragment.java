package com.example.diabetesdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DiaryListFragment extends Fragment {

    DiaryDb db;
    ListView listView;
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

        listView = (ListView)v.findViewById(R.id.items_list);
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
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryItem item = adapter.getItem(position);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details_container, new AddDiaryItemFragment(AddDiaryItemFragment.ACTION_TYPE.ACTION_EDIT, item))
                        .commit();
            }
        });


        return v;
    }

    public void updateList() {
        adapter.setItems(db.selectAll());
        adapter.notifyDataSetChanged();
    }

    class DiaryItemArrayAdapter extends ArrayAdapter<DiaryItem> {

        public ArrayList<DiaryItem> getItems() {
            return items;
        }

        public void setItems(ArrayList<DiaryItem> items) {
            this.items = items;
        }

        ArrayList<DiaryItem> items;

        public DiaryItemArrayAdapter(@NonNull Context context, ArrayList<DiaryItem> items) {
            super(context, R.layout.diary_list_item, items);
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Nullable
        @Override
        public DiaryItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            DiaryItem item = getItem(position);
            if(item != null) {
                return item.getId();
            }

            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            DiaryItem item = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_list_item,null);
            }

            TextView tvDate = convertView.findViewById(R.id.tvDate);
            TextView tvTime = convertView.findViewById(R.id.tvTime);
            TextView tvSugar = convertView.findViewById(R.id.tvSugar);
            TextView tvCarbUnits = convertView.findViewById(R.id.tvCarbUnits);
            TextView tvInsulin = convertView.findViewById(R.id.tvInsulin);
            TextView tvNote = convertView.findViewById(R.id.tvNote);
            ImageButton btnDeleteItem = convertView.findViewById(R.id.btnDeleteItem);
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryItem deletedItem = DiaryItemArrayAdapter.this.getItem(position);
                    DiaryDb db = new DiaryDb(getContext());
                    db.delete(item.getId());
                    Snackbar.make(parent, "Запись удалена", Snackbar.LENGTH_LONG)
                            .setAction("ОТМЕНА", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.insert(deletedItem);
                                    updateList();
                                }
                            }).show();
                    updateList();

                }
            });

            DateTimeHelper.updateTimeViewLabel(tvTime,item.getDate());
            DateTimeHelper.updateDateViewLabel(tvDate,item.getDate());
            tvSugar.setText(item.getSugarLevel() + "");
            tvCarbUnits.setText(item.getCarbUnits() + "");
            tvInsulin.setText(item.getInsulinRapidCount() + "");
            tvNote.setText(item.getNote());

            return convertView;
        }

    }


}