package com.example.ybm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bio extends AppCompatActivity {
TextView name1,age1;
    DatabaseReference mdatabase;
    String user_id= FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        name1=findViewById(R.id.bio_name);
        age1=findViewById(R.id.text_age_address);
        mdatabase=FirebaseDatabase.getInstance().getReference().child("usersProfile").child(user_id);
       Log.i("pay",mdatabase.toString());
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot postSnapshot) {

                            Log.i(" HARSH RAJ", postSnapshot.toString());
                            String s1 = postSnapshot.child("Name").getValue().toString();
                            Log.i("image", s1);
                            s1=s1.toUpperCase();
                            name1.setText(s1);
                            String s2 = postSnapshot.child("Age").getValue().toString();
                            String s3 = postSnapshot.child("District").getValue().toString();
                            String s4=postSnapshot.child("IsCurrentlyLivingInBihar").getValue().toString();
                            if(s4.equalsIgnoreCase("NotfromBihar"))
                                age1.setText(s2+"yrs");
                            else
                            age1.setText(s2+"yrs, "+s3+", Bihar");
                        }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       }

}
