package com.example.ybm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText username,password;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseUser user;
    Button getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText)findViewById(R.id.username_regis);
        password = (EditText)findViewById(R.id.password);
        getotp = (Button)findViewById(R.id.getotp);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //initializing user
        user = mAuth.getCurrentUser();

        if(mAuth.getCurrentUser() != null){
            Intent i=new Intent(Registration.this,BasicInfo.class);
            startActivity(i);
            finish();
        }

        getotp.setOnClickListener(this);
    }

    private void SignUp() {

        String email = username.getText().toString().trim();
        String Password = password.getText().toString().trim();

        //CHECK IF MAIL OR PASSWORD IS LEFT EMPTY
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            //sendEmailVerification();
                            //signing out the user from the divice
                            //finishing the signup activity
                            Intent i=new Intent(Registration.this,BasicInfo.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //SENDING MAIL VERIFICATION LINK
    /*private void sendEmailVerification() {
        // Disable button
        getotp.setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        getotp.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(SignupActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
*/


    @Override
    public void onClick(View view) {
        if(view == getotp){
            SignUp();
        }
    }



}
