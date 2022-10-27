package com.example.mediapp.clientApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mediapp.R;

import data.Client;
import data.Data;
import data.MyApp;

public class InsuranceDetailsActv extends AppCompatActivity {
    Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details_actv);
        data = MyApp.getData();
        if (data.getUser() instanceof Client)
            ((Client) data.getUser()).setInsurance(data.retrieveInsurance(data.getUser().getEmail()));


        LinearLayout layout = findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (String str : ((Client) data.getUser()).getInsuranceDetails()) {
            TextView tv = new TextView(this);
            tv.setText(str);
            tv.setLayoutParams(lparams);
            layout.addView(tv);
        }

    }
}