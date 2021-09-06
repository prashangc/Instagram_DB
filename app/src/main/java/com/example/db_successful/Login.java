package com.example.db_successful;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.db_successful.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView SignUp;
    ActivityLoginBinding binding;
    ProgressDialog loading;
    private FirebaseAuth auth;
//    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        SignUp = (TextView)findViewById(R.id.signUp);

        loading = new ProgressDialog(Login.this);
        loading.setTitle("Login");
        loading.setMessage("Logging to your account");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                auth.signInWithEmailAndPassword(binding.username.getText().toString() , binding.password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.dismiss();
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(Login.this , MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , com.example.db_successful.SignUp.class);
                startActivity(intent);
            }
        });

//        this is for login with given username and password

//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validate(Username.getText().toString(),Password.getText().toString());
//            }
//            private void validate(String Username, String Password) {
//                try {
//                    if (Username.equals("prashanna")) {
//                        if (Password.equals("admin")) {
//                            Intent intent = new Intent(Login.this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(Login.this, "Incorrect Password !!!", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(Login.this, "Incorrect Username !!!", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}

