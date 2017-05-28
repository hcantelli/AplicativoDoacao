package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private TextInputLayout inputLayoutEmail_login, inputLayoutPassword_login;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        inicializaVariaveis();

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaVariaveis()){
                    startSignIn();
                }
            }
        });

    }

    private void startSignIn(){

        String email = email_login.getText().toString().trim();
        String password = view_password.getText().toString().trim();

        final ProgressDialog progressDialog = ProgressDialog.show(LogIn.this, getText(R.string.aguarde), getText(R.string.processando), true);
        (mAuth.signInWithEmailAndPassword(email, password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this, getText(R.string.login_sucesso), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LogIn.this, HomePage.class);
                    startActivity(intent);
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

    private void inicializaVariaveis(){
        btn_login = (Button) findViewById(R.id.btn_login);

        inputLayoutEmail_login = (TextInputLayout) findViewById(R.id.inputLayoutEmail_login);
        inputLayoutPassword_login = (TextInputLayout) findViewById(R.id.inputLayoutPassword_login);

        email_login = (EditText) findViewById(R.id.email_login);
        view_password = (EditText) findViewById(R.id.password);

        email_login.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email_login, InputMethodManager.SHOW_IMPLICIT);

    }

    private boolean validaVariaveis(){
        boolean isValid = true;

        if(email_login.getText().toString().isEmpty()){
            inputLayoutEmail_login.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else {
            inputLayoutEmail_login.setErrorEnabled(false);
        }
        if(view_password.getText().toString().isEmpty()){
            inputLayoutPassword_login.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_password.getText().toString().length() < 8) {
            inputLayoutPassword_login.setError(getText(R.string.passwordinvalid));
            isValid = false;
        } else {
            inputLayoutPassword_login.setErrorEnabled(false);
        }

        return isValid;

    }


}
