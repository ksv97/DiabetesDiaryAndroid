package com.example.diabetesdiary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StatisticsFragment extends Fragment {

    Calendar startDate, endDate;

    EditText etStartDate, etEndDate;
    LineChart chart;

    ArrayList<DiaryItemChartModel> itemsToShow;

    public StatisticsFragment() {
        endDate = GregorianCalendar.getInstance();
        startDate = GregorianCalendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -7);
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
        View v =  inflater.inflate(R.layout.fragment_statistics, container, false);

        chart = (LineChart)v.findViewById(R.id.chart);
        etStartDate = v.findViewById(R.id.etStartDate);
        etEndDate = v.findViewById(R.id.etEndDate);

        etStartDate.setFocusable(false);
        etStartDate.setClickable(true);
        etStartDate.setInputType(InputType.TYPE_NULL);

        etEndDate.setFocusable(false);
        etEndDate.setClickable(true);
        etEndDate.setInputType(InputType.TYPE_NULL);

        ((Button)v.findViewById(R.id.btnRefreshChart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChart();
            }
        });

        DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDate.set(Calendar.YEAR, year);
                startDate.set(Calendar.MONTH, month);
                startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDate.set(Calendar.HOUR_OF_DAY, 0);
                endDate.set(Calendar.MINUTE, 0);
                endDate.set(Calendar.SECOND, 0);
                DateTimeHelper.updateDateViewLabel(etStartDate,startDate);
            }
        };
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getContext(),
                        startDateListener,
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDate.set(Calendar.YEAR, year);
                endDate.set(Calendar.MONTH, month);
                endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDate.set(Calendar.HOUR_OF_DAY, 23);
                endDate.set(Calendar.MINUTE, 59);
                endDate.set(Calendar.SECOND, 59);
                DateTimeHelper.updateDateViewLabel(etEndDate,endDate);
            }
        };
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getContext(),
                        endDateListener,
                        endDate.get(Calendar.YEAR),
                        endDate.get(Calendar.MONTH),
                        endDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DateTimeHelper.updateDateViewLabel(etStartDate,startDate);
        DateTimeHelper.updateDateViewLabel(etEndDate,endDate);

        // formatting chart

        //formatting X axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMax(15);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {

                long dataInMillis = (long)value * 1000;
                Date time = new Date(dataInMillis);
                SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM HH:mm");

                return dateFormat.format(time);
            }
        });

        //formatting y axis

        YAxis chartAxisRight = chart.getAxisRight();
        chartAxisRight.setEnabled(false);

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setSpaceMin(0f);
        axisLeft.setSpaceTop(20f);



        showChart();

        return v;
    }

    private void showChart() {
        DiaryDb db = new DiaryDb(getContext());
        itemsToShow = db.selectChartModelItems(startDate,endDate);
        ArrayList<Entry> entries = new ArrayList<>();
        for (DiaryItemChartModel item: itemsToShow) {
            entries.add(new Entry(item.getDate().getTimeInMillis() / 1000, item.getSugarLevel()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Глюкоза крови");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

    }
}