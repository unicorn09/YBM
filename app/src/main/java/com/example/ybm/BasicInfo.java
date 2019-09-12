package com.example.ybm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class BasicInfo extends AppCompatActivity {

    Spinner spinner;
    TextView district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        district = findViewById(R.id.basicinfo_district);

        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                district.setVisibility(View.GONE);

            }
        });

        spinner = findViewById(R.id.basicinfo_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.listOfDistricts));

        spinner.setAdapter(arrayAdapter);





    }
}