package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private Button btn_login;
    private EditText email_login, view_password;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        email_login = (EditText) findViewById(R.id.email_login);
        view_password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSignIn();

            }
        });



    }

    public void onStart() {
        super.onStart();

    }

    private void startSignIn(){

        String email = email_login.getText().toString().trim();
        String password = view_password.getText().toString().trim();

        final ProgressDialog progressDialog = ProgressDialog.show(LogIn.this, "Por favor, aguarde...", "Processando...", true);
        (mAuth.signInWithEmailAndPassword(email, password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this, "LogIn feito com Sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
