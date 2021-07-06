package com.jsstech.pushnotificationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {
    //for token
//    TextView textViewshow_token;

    EditText editTextEmail, editTextPass;
    Button btnsignUp;
    ProgressBar progressBar;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//For Token generation
//        textViewshow_token=findViewById(R.id.tokenshow);
//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if (task.isSuccessful()){
//                    String token=task.getResult().getToken();
//                    textViewshow_token.setText("Token"+token);
//                }else {
//                    textViewshow_token.setText(task.getException().getMessage());
//                }
//            }
//        });
//
        editTextEmail = findViewById(R.id.et_email);
        editTextPass = findViewById(R.id.et_pass);
        btnsignUp = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress);
        mauth = FirebaseAuth.getInstance();

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creteUser();

            }
        });


    }

    private void creteUser() {
        String email=editTextEmail.getText().toString().trim();
        String pass=editTextPass.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("field required");
            editTextEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            editTextPass.setError("password required");
            editTextPass.requestFocus();
            return;
        }
        if (pass.length()<6){
            editTextPass.setError("not less then 6");
            editTextPass.requestFocus();

        }
        progressBar.setVisibility(View.VISIBLE);

        mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startProfileActivity();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        userLogin(email,pass);
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mauth.getCurrentUser()!= null){
            startProfileActivity();

        }
    }

    private void userLogin(String email,String pass) {
        mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startProfileActivity();
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void startProfileActivity() {
        Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}