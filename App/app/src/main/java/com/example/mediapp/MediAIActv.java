package com.example.mediapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import data.Data;
import data.MyApp;

public class MediAIActv extends AppCompatActivity {
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_aiactv);
        data = MyApp.getData();
    }
}