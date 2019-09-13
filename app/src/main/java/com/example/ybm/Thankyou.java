package com.example.ybm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Thankyou extends AppCompatActivity implements View.OnClickListener {
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        button=(Button)findViewById(R.id.btn_gotohome);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==button)
            startActivity(new Intent(this,Bio.class));
        finish();
    }
}
