package com.example.android.contactshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Assignment extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListner;
    EditText messageWanttosend;
    ListView displayer;
    AssignmentAdapter adapt;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        displayer=(ListView)findViewById(R.id.list_item);
        messageWanttosend = (EditText)findViewById(R.id.message);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("message");
        send = (Button)findViewById(R.id.send);
        List<CustomClassForAssignment> blog=new ArrayList<>();
        adapt=new AssignmentAdapter(this,R.layout.assignment_list,blog);
        displayer.setAdapter(adapt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomClassForAssignment assignment = new CustomClassForAssignment(messageWanttosend.getText().toString(), "ANONYMUS");
                mMessageDatabaseReference.push().setValue(assignment);
                messageWanttosend.setText("");
            }
        });
        mChildEventListner=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomClassForAssignment ca=dataSnapshot.getValue(CustomClassForAssignment.class);
                adapt.add(ca);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mMessageDatabaseReference.addChildEventListener(mChildEventListner);
    }
}
