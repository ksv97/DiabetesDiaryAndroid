package com.example.diabetesdiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class AddDiaryItemFragment extends Fragment {

    EditText date,time;
    Calendar calendar;


    public AddDiaryItemFragment() {
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
        View v = inflater.inflate(R.layout.fragment_add_diary_item, container, false);

        calendar = GregorianCalendar.getInstance();
        date =  (EditText)v.findViewById(R.id.etDate);
        time = (EditText)v.findViewById(R.id.etTime);

        date.setFocusable(false);
        date.setClickable(true);
        date.setInputType(InputType.TYPE_NULL);

        time.setFocusable(false);
        time.setClickable(true);
        time.setInputType(InputType.TYPE_NULL);

        updateDateEt();
        updateTimeEt();
        setupDatePickerDialog();
        setupTimePickerDialog();


        return v;
    }

    private void setupDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateEt();
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getContext(),
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setupTimePickerDialog() {

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                updateTimeEt();
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(
                        getContext(),
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });


    }

    private void updateDateEt() {
        String dateFormatString = "EEE, d MMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString, Locale.getDefault());
        date.setText(dateFormat.format(calendar.getTime()));
    }

    private void updateTimeEt() {
        String timeFormatString = "HH:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatString, Locale.getDefault());
        timeFormat.setTimeZone(calendar.getTimeZone());
        time.setText(timeFormat.format(calendar.getTime()));
    }
}