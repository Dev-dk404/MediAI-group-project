package com.example.mediapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mediapp.clientApp.ContactADoctorActv;
import com.example.mediapp.clientApp.InsuranceDetailsActv;
import com.example.mediapp.clientApp.MedicalHistoryActv;

import data.Client;
import data.Doctor;
import data.MyApp;

public class MenuActv extends AppCompatActivity {
    Button btn_history, btn_insurance, btn_ratings, btn_mediai, btn_contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long t = System.currentTimeMillis();
      /*  while(System.currentTimeMillis()<t+7000){//there's a deadlock somewhere
            Log.d("menu", "waiting");
        }*/
        Log.d("menu", MyApp.getData().getUser().getName());
        if(MyApp.getData().getUser() instanceof Client){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Logged in as Client",
                    Toast.LENGTH_SHORT);

            toast.show();
        }else if (MyApp.getData().getUser() instanceof Doctor){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Logged in as Doctor",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
        setContentView(R.layout.activity_menu_actv);

        btn_history = (Button) findViewById(R.id.btn_history);
        btn_insurance = (Button) findViewById(R.id.btn_insurance);
        btn_mediai = (Button) findViewById(R.id.btn_mediai);
        btn_ratings = (Button) findViewById(R.id.btn_ratings);
        btn_contact = (Button) findViewById(R.id.btn_contact);

        addListeners();

    }

    private void addListeners() {

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MenuActv.this, MedicalHistoryActv.class);
                //send the data manager class to next activity
                startActivity(send);
            }
        });

        btn_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MenuActv.this, InsuranceDetailsActv.class);
                //send the data manager class to next activity
                startActivity(send);
            }
        });

        btn_mediai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MenuActv.this, MediAIActv.class);
                //send the data manager class to next activity
                startActivity(send);
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MenuActv.this, ContactADoctorActv.class);
                //send the data manager class to next activity
                startActivity(send);
            }
        });

        btn_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MenuActv.this, RatingsAndReviewsActv.class);
                //send the data manager class to next activity
                startActivity(send);
            }
        });

    }
}