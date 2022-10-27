package com.example.mediapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import data.Data;
import data.MyApp;

public class SupportActv extends AppCompatActivity {
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_actv);
        data = MyApp.getData();
    }
}