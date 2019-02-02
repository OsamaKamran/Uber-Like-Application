package com.example.dellcom.passenger;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText name, phone, pass, cnic, add, email;
    static String n, ph,e, ps, c, a;
    Button b;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authlistener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name= (EditText)findViewById(R.id.editText3);
        phone= (EditText)findViewById(R.id.editText4);
        pass= (EditText)findViewById(R.id.editText5);
        cnic= (EditText)findViewById(R.id.editText6);
        add= (EditText)findViewById(R.id.editText7);
        b= (Button)findViewById(R.id.button2);
        email= (EditText)findViewById(R.id.editText8);

        firebaseAuth= FirebaseAuth.getInstance();

        authlistener= new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user= firebaseAuth.getCurrentUser();

                if(user != null){

                }
                else{

                }
            }
        };

        if( !FirebaseApp.getApps(this).isEmpty())
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReferenceFromUrl("https://utaxi-af7f6.firebaseio.com/");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                n= name.getText().toString();
                ph= phone.getText().toString();
                e= email.getText().toString();
                ps= pass.getText().toString();
                c= cnic.getText().toString();
                a= add.getText().toString();


                if(!TextUtils.isEmpty(e) && !TextUtils.isEmpty(ps)){

                    firebaseAuth.createUserWithEmailAndPassword(e, ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent i= new Intent(signup.this, login.class);
                                startActivity(i);

                            }
                            else{
                                Toast.makeText(signup.this, "Failed to create Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    DatabaseReference nchild= databaseReference.child("NewData").push();

                    nchild.child("Name").setValue(n);
                    nchild.child("Phone").setValue(ph);
                    nchild.child("Email").setValue(e);
                    nchild.child("Password").setValue(ps);
                    nchild.child("CNIC").setValue(c);
                    nchild.child("Address").setValue(a);
                }
            }
        });
    }

    @Override
    public void onStart(){

        super.onStart();
        firebaseAuth.addAuthStateListener(authlistener);
    }

    @Override
    public void onStop(){

        super.onStop();
        firebaseAuth.removeAuthStateListener(authlistener);
    }
}
