package com.example.mediapp.clientApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mediapp.R;

import data.Data;
import data.MyApp;


public class MedicalHistoryActv extends AppCompatActivity {
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history_actv);
        data = MyApp.getData();

        TextView txt_name = (TextView) findViewById(R.id.txt_name);
        TextView txt_surname = (TextView) findViewById(R.id.txt_surname);

        txt_name.setText(txt_name.getText() + " " + data.getUser().getName());
        txt_surname.setText(txt_surname.getText() + " " + data.getUser().getSurname());
    }
}