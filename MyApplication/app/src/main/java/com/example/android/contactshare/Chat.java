package com.example.android.contactshare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListner;
    EditText messageWanttosend;
    ListView displayer;
    ChatAdapter adapt;
    String username;
    FirebaseAuth.AuthStateListener mAuthListner;
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
        List<CustomClassForChat> blog=new ArrayList<>();
        adapt=new ChatAdapter(this,R.layout.assignment_list,blog);
        displayer.setAdapter(adapt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(messageWanttosend.getText().toString().isEmpty()==false)
                {
                    mAuthListner=new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            username= user.getDisplayName();
                        }


                    };
                    CustomClassForChat assignment = new CustomClassForChat(messageWanttosend.getText().toString(), username);
                mMessageDatabaseReference.push().setValue(assignment);
                messageWanttosend.setText("");
                }
            }
        });

        mChildEventListner=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomClassForChat ca=dataSnapshot.getValue(CustomClassForChat.class);
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


