package com.example.dellcom.passenger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;



public class login extends AppCompatActivity {

    TextView signup;
    EditText email , pass;
    Button login;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authlistener;
    String ee, pss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup= (TextView)findViewById(R.id.textView2);
        email= (EditText)findViewById(R.id.editText);
        pass= (EditText)findViewById(R.id.editText2);
        login= (Button)findViewById(R.id.button);

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getBaseContext(), signup.class);
                startActivity(i);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ee= email.getText().toString(); pss=pass.getText().toString();

                if( !TextUtils.isEmpty(ee) && !TextUtils.isEmpty(pss)){

                    firebaseAuth.signInWithEmailAndPassword(ee, pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent i = new Intent(new Intent(login.this, navigation.class));
                                startActivity(i);

                            }
                            else
                                Toast.makeText(login.this, "Login Failed.", Toast.LENGTH_LONG).show();
                        }
                    });
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
