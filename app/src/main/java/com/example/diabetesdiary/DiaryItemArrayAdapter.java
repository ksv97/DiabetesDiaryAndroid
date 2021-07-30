package com.example.diabetesdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DiaryItemArrayAdapter extends ArrayAdapter<DiaryItem> {

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_diary_list,null);
        }

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvTime = convertView.findViewById(R.id.tvTime);
        TextView tvSugar = convertView.findViewById(R.id.tvSugar);
        TextView tvCarbUnits = convertView.findViewById(R.id.tvCarbUnits);
        TextView tvInsulin = convertView.findViewById(R.id.tvInsulin);
        TextView tvNote = convertView.findViewById(R.id.tvNote);

        DateTimeHelper.updateTimeViewLabel(tvTime,item.getDate());
        DateTimeHelper.updateDateViewLabel(tvDate,item.getDate());
        tvSugar.setText(item.getSugarLevel() + "");
        tvCarbUnits.setText(item.getCarbUnits() + "");
        tvInsulin.setText(item.getInsulinRapidCount() + "");
        tvNote.setText(item.getNote());

        return convertView;
    }

}
