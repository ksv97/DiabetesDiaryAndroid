package com.example.diabetesdiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddDiaryItemFragment extends Fragment {

    public enum ACTION_TYPE {
        ACTION_ADD, ACTION_EDIT
    }

    TextView tvCarbUnits,tvSugar, tvInsulin;
    EditText etDate, etTime, etNote;
    Button btnCancel, btnAction;
    SeekBar sbSugarIntPart,sbSugarFloatPart, sbCarbIntPart, sbCarbFloatPart,sbInsulinIntPart,sbInsulinFloatPart;
    Calendar calendar;
    DiaryItem item;
    ACTION_TYPE action;


    public AddDiaryItemFragment(ACTION_TYPE action, DiaryItem item) {
        this.action = action;
        if (item == null)
            this.item = new DiaryItem();
        else {
            this.item = item;
        }
        calendar = this.item.getDate();
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


        etDate =  (EditText)v.findViewById(R.id.etDate);
        etTime = (EditText)v.findViewById(R.id.etTime);
        etNote = (EditText)v.findViewById(R.id.etNote);
        sbCarbFloatPart = (SeekBar)v.findViewById(R.id.sbCarbFloatPart);
        sbCarbIntPart = (SeekBar)v.findViewById(R.id.sbCarbIntPart);
        sbInsulinFloatPart = (SeekBar)v.findViewById(R.id.sbInsulinFloatPart);
        sbInsulinIntPart = (SeekBar)v.findViewById(R.id.sbInsulinIntPart);
        sbSugarFloatPart = (SeekBar)v.findViewById(R.id.sbSugarFloatPart);
        sbSugarIntPart = (SeekBar)v.findViewById(R.id.sbSugarIntPart);

        tvCarbUnits =(TextView)v.findViewById(R.id.tvCarbUnits);
        tvSugar = (TextView)v.findViewById(R.id.tvSugar);
        tvInsulin = (TextView)v.findViewById(R.id.tvInsulin);

        sbCarbFloatPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCarbUnits();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbCarbIntPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCarbUnits();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbSugarIntPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSugar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbSugarFloatPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSugar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbInsulinIntPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateInsulin();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbInsulinFloatPart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateInsulin();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        etNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setNote(etNote.getText().toString());
            }
        });

        etDate.setFocusable(false);
        etDate.setClickable(true);
        etDate.setInputType(InputType.TYPE_NULL);

        etTime.setFocusable(false);
        etTime.setClickable(true);
        etTime.setInputType(InputType.TYPE_NULL);

        DateTimeHelper.updateDateViewLabel(etDate,calendar);
        DateTimeHelper.updateTimeViewLabel(etTime,calendar);

        setupDatePickerDialog();
        setupTimePickerDialog();

        btnAction = v.findViewById(R.id.btnAction);
        btnCancel = v.findViewById(R.id.btnCancel);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action == ACTION_TYPE.ACTION_ADD) {
                    addItemAndClose();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishFragment();
            }
        });


        return v;
    }

    private void addItemAndClose() {
        DiaryDb db = new DiaryDb(getContext());
        db.insert(item);
        ((DiaryListFragment)getParentFragment()).updateList();
        finishFragment();
    }

    private void setupDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateTimeHelper.updateDateViewLabel(etDate,calendar);
            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
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
                DateTimeHelper.updateTimeViewLabel(etTime,calendar);
            }
        };

        etTime.setOnClickListener(new View.OnClickListener() {
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

    private void updateCarbUnits() {
        float newCarbUnits = sbCarbFloatPart.getProgress() * 0.1f + sbCarbIntPart.getProgress();
        item.setCarbUnits(newCarbUnits);
        tvCarbUnits.setText(newCarbUnits + "");
    }

    private void updateSugar() {
        float newSugar = sbSugarFloatPart.getProgress() * 0.1f + sbSugarIntPart.getProgress();
        item.setSugarLevel(newSugar);
        tvSugar.setText(newSugar + "");
    }

    private void updateInsulin() {
        float newInsulin = sbInsulinFloatPart.getProgress() * 0.1f + sbInsulinIntPart.getProgress();
        item.setInsulinRapidCount(newInsulin);
        tvInsulin.setText(newInsulin + "");
    }

    private void finishFragment() {
        getParentFragment().getChildFragmentManager().beginTransaction().remove(AddDiaryItemFragment.this).commit();
    }
}