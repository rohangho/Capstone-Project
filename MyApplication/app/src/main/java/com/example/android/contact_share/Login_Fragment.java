package com.example.android.contact_share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by ROHAN on 01-11-2017.
 */

public class Login_Fragment extends Fragment {

    public Login_Fragment()
    {

    }

    EditText email;
    EditText password;
    Button login;
    Button CreateAccount;
    private FirebaseAuth fbAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    DatabaseReference mDatabaseRef;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        email=(EditText)rootView.findViewById(R.id.email);
        password=(EditText)rootView.findViewById(R.id.password);
        login=(Button)rootView.findViewById(R.id.login);
        CreateAccount=(Button)rootView.findViewById(R.id.create_account);
        fbAuth=FirebaseAuth.getInstance();
        FirebaseUser user=fbAuth.getCurrentUser();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference();
        if(user!=null)
            startActivity(new Intent(getActivity(), Subject.class));


        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final String username,passcode;
                username=email.getText().toString();

                passcode=password.getText().toString();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(passcode))
                {
                    //Log.e("Hi i am printing",username);
                    fbAuth.createUserWithEmailAndPassword(username,passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                DatabaseReference mChildDatabase=mDatabaseRef.child("Users").push();
                                mChildDatabase.child("verification").setValue("Unverified");
                                mChildDatabase.child("email").setValue(username);
                                mChildDatabase.child("password").setValue(passcode);
                                mChildDatabase.child("Time").setValue(Calendar.getInstance().getTime().toString());
                                Toast.makeText(getActivity(), "User Account Created", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getActivity(), Subject.class));


                            }
                            else {
                                Toast.makeText(getActivity(), "Some Error occured contact the developer", Toast.LENGTH_LONG).show();
                                Log.e("hii",task.toString());
                            }
                        }
                    });
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),to_log_in.class));
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });



        fbAuth.signOut();
        return rootView;
    }








}
