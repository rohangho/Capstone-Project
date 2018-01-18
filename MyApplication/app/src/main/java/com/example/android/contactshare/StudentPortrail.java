package com.example.android.contactshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StudentPortrail extends AppCompatActivity {

    private FirebaseStorage storage ;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portrail);
        storage= FirebaseStorage.getInstance();
        storageRef=storage.getReferenceFromUrl("gs://contact-2901.appspot.com").child("");



    }
}
