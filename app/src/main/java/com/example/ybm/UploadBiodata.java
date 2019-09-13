package com.example.ybm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class UploadBiodata extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST =1 ;
    private static final int PICK_IMAGE_REQUEST =234 ;
    private static final int PICK_PDF_CODE =3 ;
    private static final int GALLERY_INTENT = 5;
    private static final int PICK_FROM_CAMERA =6 ;
    private Button button;
    private ImageView pdf;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private Uri outPutfileUri;
    private ImageView camera_image;
    private ProgressDialog progressDialog;
    String user_id= FirebaseAuth.getInstance().getUid();
    String user_id1= FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_biodata);
        pdf=(ImageView)findViewById(R.id.upload_cv);
        pdf.setOnClickListener(this);
        camera_image=(ImageView)findViewById(R.id.camera_image);
        camera_image.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog=new ProgressDialog(this);
        button=(Button)findViewById(R.id.finish);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==pdf)
            getPDF();
        if(v==camera_image)
        {
            getpicture();
        }
        if(v==button)
        {
            Intent i=new Intent(this,Thankyou.class);
            startActivity(i);
        }
    }

    private void getpicture() {
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
        outPutfileUri = Uri.fromFile(file);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(captureIntent, PICK_FROM_CAMERA);
    }

    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), GALLERY_INTENT); }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK ) {
            //if a file is selected
            if (data.getData() != null) {
                Uri uri1=data.getData();
                final StorageReference sRef = storageReference.child(user_id+ getFileExtension(uri1));
                progressDialog.show();
   //kundan     final DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("cv");
                sRef.putFile(uri1)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                           //kundan     final DatabaseReference newpost = mDatabase.push();
                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String pdfurl = uri.toString();
                                              //kundan  newpost.child("image").setValue(pdfurl);
                                                progressDialog.dismiss();

                                            }
                                        });
                                    }
                                }
                                //displaying success toast
                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });
            } else {

            }
        }}
        }

