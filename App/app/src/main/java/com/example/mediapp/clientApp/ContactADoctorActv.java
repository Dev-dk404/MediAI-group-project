package com.example.mediapp.clientApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mediapp.R;

import java.util.ArrayList;

import data.Client;
import data.Data;
import data.Doctor;
import data.MyApp;

public class ContactADoctorActv extends AppCompatActivity {
    Data data;
    LinearLayout layout;
    LinearLayout.LayoutParams lparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_adoctor_actv);
        data = MyApp.getData();


        layout = findViewById(R.id.linear_doctor_types);
        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ArrayList<String> occupations = new ArrayList<String>();
        for (Doctor doc : data.getDoctors()) {
            if (!occupations.contains(doc.getOccupation())) {
                occupations.add(doc.getOccupation());
                Button btn = new Button(this);
                btn.setText(doc.getOccupation());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDoctors(doc.getOccupation());
                    }
                });
                btn.setLayoutParams(lparams);
                layout.addView(btn);
            }
        }
    }


    //once a doctor type is selected, shows all available doctors of said occupation
    //@param: occupation, string to filter doctors by
    private void showDoctors(String occupation) {

        layout.removeAllViews();

        for (Doctor doc : data.getDoctors()) {
            if (doc.getOccupation().equals(occupation)) {
                Button btn = new Button(this);
                btn.setText("Dr. " + doc.getName() + " " + doc.getSurname());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDates(doc);
                    }
                });
                btn.setLayoutParams(lparams);
                layout.addView(btn);
            }
        }
    }


    //once a doctor  is selected, shows all available dates to see that doctor
    //@param: doc, selected Doctor
    private void showDates(Doctor doc) {
        TextView txt = findViewById(R.id.txt_action_doctor);
        String str = getString(R.string.doc_name) + doc.getSurname() + ", " + doc.getOccupation();
        txt.setText(str);
        layout.removeAllViews();


    }
}