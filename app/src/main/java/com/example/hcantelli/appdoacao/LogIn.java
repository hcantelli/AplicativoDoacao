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

public class LogIn extends AppCompatActivity {

    private Button botao_login;
    private EditText email_login, view_senha;
    private TextInputLayout inputLayoutEmail_login, inputLayoutSenha_login;
    private FirebaseAuth autorizacao_firebase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        inicializaVariaveis();

        autorizacao_firebase = FirebaseAuth.getInstance();

        botao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaVariaveis()){
                    inicializaLogin();
                }
            }
        });

    }

    //Realiza conexão com serviço de autenticação para conectar o usuário ao serviço de autenticação
    private void inicializaLogin(){

        final String email = email_login.getText().toString().trim();
        String password = view_senha.getText().toString().trim();

        final ProgressDialog progressDialog = ProgressDialog.show(LogIn.this, getText(R.string.aguarde), getText(R.string.processando), true);
        (autorizacao_firebase.signInWithEmailAndPassword(email, password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this, getText(R.string.login_sucesso), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LogIn.this, TelaUsuario.class);
                    startActivity(intent);
                } else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(LogIn.this, getText(R.string.login_erro), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //Método para inicializar as variáveis, registrando os valores inseridos pelo usuário na tela
    private void inicializaVariaveis(){
        botao_login = (Button) findViewById(R.id.botao_login);

        inputLayoutEmail_login = (TextInputLayout) findViewById(R.id.inputLayoutEmail_login);
        inputLayoutSenha_login = (TextInputLayout) findViewById(R.id.inputLayoutSenha_login);

        email_login = (EditText) findViewById(R.id.email_login);
        view_senha = (EditText) findViewById(R.id.senha);

        email_login.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email_login, InputMethodManager.SHOW_IMPLICIT);

    }

    //Metódo que valida os caracteres dos campos inseridos antes de autenticar o usuário no serviço de autenticação
    private boolean validaVariaveis(){
        boolean isValid = true;

        if(email_login.getText().toString().isEmpty()){
            inputLayoutEmail_login.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else {
            inputLayoutEmail_login.setErrorEnabled(false);
        }
        if(view_senha.getText().toString().isEmpty()){
            inputLayoutSenha_login.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_senha.getText().toString().length() < 8) {
            inputLayoutSenha_login.setError(getText(R.string.senhaInvalida));
            isValid = false;
        } else {
            inputLayoutSenha_login.setErrorEnabled(false);
        }

        return isValid;

    }


}
