package com.example.diabetesdiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class PreferencesFragment extends Fragment {


    static String PROFILE_PREFS = "DiabetesProfile";
    static String GOAL_SUGAR_PREF_NAME = "goalSugar";
    static String LOW_SUGAR_PREF_NAME = "lowSugar";
    static String HIGH_SUGAR_PREF_NAME = "highSugar";

    EditText etGoalSugar, etLowSugar, etHighSugar;

    SharedPreferences preferences;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_preferences, container, false);

        etGoalSugar = v.findViewById(R.id.etGoalSugar);
        etLowSugar = v.findViewById(R.id.etLowSugar);
        etHighSugar = v.findViewById(R.id.etHighSugar);

        ((Button)v.findViewById(R.id.btnSavePreferences)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(v);
            }
        });

        etGoalSugar.setText(preferences.getFloat(GOAL_SUGAR_PREF_NAME, 6.0f) + "");
        etLowSugar.setText(preferences.getFloat(LOW_SUGAR_PREF_NAME, 4.0f)+"");
        etHighSugar.setText(preferences.getFloat(HIGH_SUGAR_PREF_NAME, 9.0f)+"");

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void savePreferences(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(GOAL_SUGAR_PREF_NAME, Float.parseFloat(etGoalSugar.getText().toString()));
        editor.putFloat(LOW_SUGAR_PREF_NAME, Float.parseFloat(etLowSugar.getText().toString()));
        editor.putFloat(HIGH_SUGAR_PREF_NAME, Float.parseFloat(etHighSugar.getText().toString()));

        editor.commit();

        Snackbar.make(getView(),"Настройки сохранены",Snackbar.LENGTH_LONG).show();
    }
}