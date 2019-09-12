package com.example.ybm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicInfo extends AppCompatActivity {
    EditText infoname,infoage;
    TextView male,female,other,biharbelongY,biharbelongN,currentlyY,currentlyN;
    FirebaseAuth mAuth;
    DatabaseReference mrefrence;
    String name,age,gender,belongyes;
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

        mAuth=FirebaseAuth.getInstance();
        String s=mAuth.getCurrentUser().getUid();
        mrefrence= FirebaseDatabase.getInstance().getReference().child("usersProfile").child(s);

    }

}