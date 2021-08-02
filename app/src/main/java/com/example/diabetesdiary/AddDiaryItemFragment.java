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

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;

// Фрагмент, который отвечает за изменение\добавление записи в дневнике самоконтроля
public class AddDiaryItemFragment extends Fragment {

    // тип действия - добавление или изменение записи
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
                if (fromUser)
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
                if (fromUser)
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
                if (fromUser)
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
                if (fromUser)
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
                if (fromUser)
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
                if (fromUser)
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


        // Делаем невозможным редактирование EditText-ов для даты и времени
        // чтобы пользователь пользовался исключительно DatePicker-ом и TimePicker-ом
        etDate.setFocusable(false);
        etDate.setClickable(true);
        etDate.setInputType(InputType.TYPE_NULL);

        etTime.setFocusable(false);
        etTime.setClickable(true);
        etTime.setInputType(InputType.TYPE_NULL);

        setupDatePickerDialog();
        setupTimePickerDialog();

        btnAction = v.findViewById(R.id.btnAction);
        btnCancel = v.findViewById(R.id.btnCancel);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action == ACTION_TYPE.ACTION_ADD) {
                    // Для добавления записи необходимо, чтобы хотя бы одно поле было не пустым
                    if (item.getCarbUnits() > 0 || item.getSugarLevel() > 0  || item.getInsulinRapidCount() > 0 || !item.getNote().equals("")) {
                        addItemAndClose();
                    }
                    else {
                        Snackbar.make(getView(),"Заполните хотя бы одно поле перед добавлением записи!", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else if (action == ACTION_TYPE.ACTION_EDIT) {
                    updateItemAndClose();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishFragment();
            }
        });

        // установка значений в элементы управления в соответствии с текущим Item-ом
        setupValues();

        return v;
    }

    private void addItemAndClose() {
        // выполнение добавления в БД. Не вынесено в отдельный поток, т.к. не является трудоемкой операцией
        DiaryDb db = new DiaryDb(getContext());
        db.insert(item);
        Snackbar.make(getView(),"Запись успешно добавлена", Snackbar.LENGTH_SHORT).show();

        // асинхронное обновление данных в ListView
        ((DiaryListFragment)getParentFragment()).updateListAsync();
        finishFragment();
    }

    private void updateItemAndClose() {
        // выполнение обновления записи в БД. Не вынесено в отдельный поток, т.к. не является трудоемкой операцией
        DiaryDb db = new DiaryDb(getContext());
        db.update(item);
        Snackbar.make(getView(),"Запись успешно обновлена", Snackbar.LENGTH_SHORT).show();

        // асинхронное обновление данных в ListView
        ((DiaryListFragment)getParentFragment()).updateListAsync();
        finishFragment();
    }

    /**
     * Настройка DatePicker-а и установка обработчика событий на клик по EditText-у для даты записи в дневнике
     */
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


    /**
     * Настройка TimePicker-а и установка обработчика событий на клик по EditText-у для времени записи в дневнике
     */
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


    /**
     * Обновление данных и представления для хлебных единиц
     */
    private void updateCarbUnits() {
        float newCarbUnits = sbCarbFloatPart.getProgress() * 0.1f + sbCarbIntPart.getProgress();
        item.setCarbUnits(newCarbUnits);
        tvCarbUnits.setText(newCarbUnits + "");
    }

    /**
     * Обновление данных и представления для уровня глюкозы
     */
    private void updateSugar() {
        float newSugar = sbSugarFloatPart.getProgress() * 0.1f + sbSugarIntPart.getProgress();
        item.setSugarLevel(newSugar);
        tvSugar.setText(newSugar + "");
    }


    /**
     * Обновление данных и представления для введенного короткого инсулина
     */
    private void updateInsulin() {
        float newInsulin = sbInsulinFloatPart.getProgress() * 0.1f + sbInsulinIntPart.getProgress();
        item.setInsulinRapidCount(newInsulin);
        tvInsulin.setText(newInsulin + "");
    }

    /**
     * Установка значений в представление в соответствии с текущим DiaryItem-ом
     */
    private void setupValues() {
        DateTimeHelper.updateDateViewLabel(etDate,calendar);
        DateTimeHelper.updateTimeViewLabel(etTime,calendar);
        sbInsulinIntPart.setProgress((int)item.getInsulinRapidCount());
        sbInsulinFloatPart.setProgress((int)(item.getInsulinRapidCount() * 10 % 10));
        sbSugarIntPart.setProgress((int)item.getSugarLevel());
        sbSugarFloatPart.setProgress((int)(item.getSugarLevel() * 10 % 10));
        sbCarbIntPart.setProgress((int)item.getCarbUnits());
        sbCarbFloatPart.setProgress((int)(item.getCarbUnits() * 10 % 10));
        updateCarbUnits();
        updateInsulin();
        updateSugar();
        etNote.setText(item.getNote());
    }

    /**
     * Удаление фрагмента из контейнера
     */
    private void finishFragment() {
        getParentFragment().getChildFragmentManager().beginTransaction().remove(AddDiaryItemFragment.this).commit();
    }
}