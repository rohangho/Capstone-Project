package com.example.android.contactshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ToLogIn extends AppCompatActivity {

    EditText signed_email;
    EditText signed_password;
    Button check_credential;
    private FirebaseAuth fbAuth;
    String email_extracted;
    ArrayList<String> user_in_array = new ArrayList<String>();
    FirebaseAuth.AuthStateListener mAuthListner;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_log_in);
        fbAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        signed_email = (EditText) findViewById(R.id.check_signed_mail);
        signed_password = (EditText) findViewById(R.id.check_signed_password);

        final Intent myintent = new Intent(this, Subject.class);


        check_credential = (Button) findViewById(R.id.user_credential);
        check_credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fbAuth.signInWithEmailAndPassword((signed_email.getText().toString()), (signed_password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            startActivity(myintent);
                        } else
                            Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();
                    }
                });


            }


        });

    }
}
