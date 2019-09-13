package com.example.ybm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicInfo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText infoname,infoage;
    TextView male,female,other,biharbelongY,biharbelongN,currentlyY,currentlyN,liveinbihar;
    FirebaseAuth mAuth;
    DatabaseReference mrefrence;
    String name,age,gender,belongyes,currentyes="NotfromBihar",districtstring="NotfromBihar";
    Spinner spinner;
    TextView district;
    Drawable backgrounddraw;
    Drawable backgrounddraw1;
    Button nextAndSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        infoname=findViewById(R.id.info_name);
        infoage=findViewById(R.id.info_age);
        male=findViewById(R.id.info_male);
        female=findViewById(R.id.info_female);
        other=findViewById(R.id.info_other);
        biharbelongY=findViewById(R.id.bihar_belong_Y);
        biharbelongN=findViewById(R.id.bihar_belong_N);
        currentlyY=findViewById(R.id.currentyes);
        currentlyN=findViewById(R.id.currentno);
        nextAndSubmit=findViewById(R.id.nextAndUpload);
        liveinbihar=findViewById(R.id.liveinbihar);
        backgrounddraw=getResources().getDrawable(R.drawable.shape_text_input_box_on_back_change);
        backgrounddraw1=getResources().getDrawable(R.drawable.shape_text_input_box);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        other.setOnClickListener(this);
        biharbelongY.setOnClickListener(this);
        biharbelongN.setOnClickListener(this);
        currentlyY.setOnClickListener(this);
        currentlyN.setOnClickListener(this);
        nextAndSubmit.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        String s=mAuth.getCurrentUser().getUid();
        mrefrence= FirebaseDatabase.getInstance().getReference().child("usersProfile").child(s);
        district = findViewById(R.id.basicinfo_district);
        district.setVisibility(View.GONE);
        currentlyY.setVisibility(View.GONE);
        currentlyN.setVisibility(View.GONE);
        liveinbihar.setVisibility(View.GONE);

        district.setOnClickListener(this);

        spinner = findViewById(R.id.basicinfo_spinner);
        spinner.setVisibility(View.GONE);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.listOfDistricts));

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==district){
            spinner.setVisibility(View.VISIBLE);
            district.setVisibility(View.GONE);
        }
        if(v==male){
            male.setBackground(backgrounddraw);
            male.setTextColor(Color.WHITE);
            female.setBackground(backgrounddraw1);
            female.setTextColor(Color.BLACK);
            other.setBackground(backgrounddraw1);
            other.setTextColor(Color.BLACK);
            gender=male.getText().toString();
        }
        if(v==female){
            female.setBackground(backgrounddraw);
            female.setTextColor(Color.WHITE);
            male.setBackground(backgrounddraw1);
            male.setTextColor(Color.BLACK);
            other.setBackground(backgrounddraw1);
            other.setTextColor(Color.BLACK);
            gender=female.getText().toString();
        }
        if(v==other){
            other.setBackground(backgrounddraw);
            other.setTextColor(Color.WHITE);
            male.setBackground(backgrounddraw1);
            male.setTextColor(Color.BLACK);
            female.setBackground(backgrounddraw1);
            female.setTextColor(Color.BLACK);
            gender=other.getText().toString();
        }
        if(v==biharbelongY){
            biharbelongY.setTextColor(Color.WHITE);
            biharbelongY.setBackground(backgrounddraw);
            biharbelongN.setTextColor(Color.BLACK);
            biharbelongN.setBackground(backgrounddraw1);
            district.setVisibility(View.VISIBLE);
            currentlyY.setVisibility(View.VISIBLE);
            currentlyN.setVisibility(View.VISIBLE);
            liveinbihar.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            belongyes=biharbelongY.getText().toString();
        }
        if(v==biharbelongN){
            biharbelongN.setTextColor(Color.WHITE);
            biharbelongN.setBackground(backgrounddraw);
            biharbelongY.setTextColor(Color.BLACK);
            biharbelongY.setBackground(backgrounddraw1);
            belongyes=biharbelongN.getText().toString();
            district.setVisibility(View.GONE);
            currentlyY.setVisibility(View.GONE);
            currentlyN.setVisibility(View.GONE);
            liveinbihar.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            currentyes="NotfromBihar";
            districtstring="NotfromBihar";
        }
        if(v==currentlyY){
            currentlyY.setTextColor(Color.WHITE);
            currentlyY.setBackground(backgrounddraw);
            currentlyN.setBackground(backgrounddraw1);
            currentlyN.setTextColor(Color.BLACK);
            currentyes=currentlyY.getText().toString();
        }
        if(v==currentlyN){
            currentlyN.setTextColor(Color.WHITE);
            currentlyN.setBackground(backgrounddraw);
            currentlyY.setBackground(backgrounddraw1);
            currentlyY.setTextColor(Color.BLACK);
            currentyes=currentlyN.getText().toString();
        }
        if(v==nextAndSubmit){
            name=infoname.getText().toString();
            age=infoage.getText().toString();

            if (name.trim().isEmpty())
            {
                infoname.setError("Can't be empty");
                infoname.requestFocus();
                return;
            }
            if (age.trim().isEmpty())
            {
                infoage.setError("Age can't be empty");
                infoage.requestFocus();
                return;
            }

            UploadBasicInfo u=new UploadBasicInfo(name,age,gender,belongyes,districtstring,currentyes);
            mrefrence.setValue(u);
            Intent i=new Intent(BasicInfo.this,UploadBiodata.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        districtstring=spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}