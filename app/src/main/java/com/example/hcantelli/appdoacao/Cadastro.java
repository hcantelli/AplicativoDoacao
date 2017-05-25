package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Cadastro extends AppCompatActivity{

    private Button mFirebaseButton;
    private DatabaseReference mDatabase;
    private EditText view_nomeUsuario, view_email, view_dataDeNascimento, view_telefone, view_endereco, view_cep, view_cpf, view_password;
    private TextInputLayout inputLayoutNomeUsuario, inputLayoutEmail, inputLayoutDataDeNascimento, inputLayoutTelefone, inputLayoutEndereco, inputLayoutCEP, inputLayoutCPF, inputLayoutPassword;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int usuario = bundle.getInt("Usuario");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        inicializaVariaveis();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String senha = view_password.getText().toString().trim();
                final String email = view_email.getText().toString().trim();

                if(validaVariaveis()){
                    final ProgressDialog progressDialog = ProgressDialog.show(Cadastro.this, "Por favor, aguarde...", "Processando...", true);
                    mDatabase.child("Usuarios").child("Usuario" + usuario).setValue(registraUsuario()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                (firebaseAuth.createUserWithEmailAndPassword(email, senha)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.dismiss();

                                        if (task.isSuccessful()) {
                                            Toast.makeText(Cadastro.this, "Cadastro feito com Sucesso!", Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(Cadastro.this, MainActivity.class);
                                            startActivity(intent1);
                                        } else {
//                                      fazer tratativas de erro para cada erro
//                                      auth/email-already-in-use
//                                      Thrown if there already exists an account with the given email address.
//                                      auth/invalid-email
//                                      Thrown if the email address is not valid.
//                                      auth/operation-not-allowed
//                                      Thrown if email/password accounts are not enabled. Enable email/password accounts in the Firebase Console, under the Auth tab.
//                                      auth/weak-password
//                                      Thrown if the password is not strong enough.
                                            Log.e("ERROR", task.getException().toString());
                                            Toast.makeText(Cadastro.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(Cadastro.this, "Ops... Ocorreu algum erro no Cadastro. Tente novamente.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }



            }
        });



    }

    private void inicializaVariaveis(){
        mFirebaseButton = (Button) findViewById(R.id.addFirebase);

        inputLayoutNomeUsuario = (TextInputLayout) findViewById(R.id.inputLayoutNomeUsuario);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutDataDeNascimento = (TextInputLayout) findViewById(R.id.inputLayoutDataDeNascimento);
        inputLayoutTelefone = (TextInputLayout) findViewById(R.id.inputLayoutTelefone);
        inputLayoutEndereco = (TextInputLayout) findViewById(R.id.inputLayoutEndereco);
        inputLayoutCEP = (TextInputLayout) findViewById(R.id.inputLayoutCEP);
        inputLayoutCPF = (TextInputLayout) findViewById(R.id.inputLayoutCPF);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);

        view_nomeUsuario = (EditText) findViewById(R.id.nomeUsuario);
        view_email = (EditText) findViewById(R.id.email);
        view_dataDeNascimento = (EditText) findViewById(R.id.dataDeNascimento);
        view_telefone = (EditText) findViewById(R.id.telefone);
        view_endereco = (EditText) findViewById(R.id.endereco);
        view_cep = (EditText) findViewById(R.id.cep);
        view_cpf = (EditText) findViewById(R.id.cpf);
        view_password = (EditText) findViewById(R.id.password);

        view_nomeUsuario.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view_nomeUsuario, InputMethodManager.SHOW_IMPLICIT);

    }

    private HashMap<String, String> registraUsuario(){
        String nomeUsuario = view_nomeUsuario.getText().toString().trim();
        final String email = view_email.getText().toString().trim();
        String dataDeNascimento = view_dataDeNascimento.getText().toString().trim();
        String telefone = view_telefone.getText().toString().trim();
        String endereco = view_endereco.getText().toString().trim();
        String cep = view_cep.getText().toString().trim();
        String cpf = view_cpf.getText().toString().trim();

        final HashMap<String, String> usuarioMap = new HashMap<String, String>();

        usuarioMap.put("nomeUsuario", nomeUsuario);
        usuarioMap.put("email", email);
        usuarioMap.put("dataDeNascimento", dataDeNascimento);
        usuarioMap.put("telefone", telefone);
        usuarioMap.put("endereco", endereco);
        usuarioMap.put("CEP", cep);
        usuarioMap.put("CPF", cpf);

        return usuarioMap;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
//COLOCAR TODAS AS VARIÁVEIS STRINGS QUE ESTÃO SENDO UTILIZADAS NO ARQUIVO STRING.XML!!!!
    private boolean validaVariaveis(){
        boolean isValid = true;

        if(view_nomeUsuario.getText().toString().isEmpty()){
            inputLayoutNomeUsuario.setError("Campo obrigatório");
            isValid = false;

        } else {
            inputLayoutNomeUsuario.setErrorEnabled(false);
        }

        if(view_email.getText().toString().isEmpty()){
            inputLayoutEmail.setError("Campo obrigatório");
            isValid = false;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }
//fazer validação de campo
        if(view_dataDeNascimento.getText().toString().isEmpty()){
            inputLayoutDataDeNascimento.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutDataDeNascimento.setErrorEnabled(false);
        }
//fazer validação de campo
        if(view_telefone.getText().toString().isEmpty()){
            inputLayoutTelefone.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutTelefone.setErrorEnabled(false);
        }

        if(view_endereco.getText().toString().isEmpty()){
            inputLayoutEndereco.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutEndereco.setErrorEnabled(false);
        }
//fazer validação de campo
        if(view_cep.getText().toString().isEmpty()){
            inputLayoutCEP.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutCEP.setErrorEnabled(false);
        }
//fazer validação de campo
        if(view_cpf.getText().toString().isEmpty()){
            inputLayoutCPF.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutCPF.setErrorEnabled(false);
        }
//fazer validação de campo
        if(view_password.getText().toString().isEmpty()){
            inputLayoutPassword.setError("Campo obrigatório");
            isValid = false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }


        return isValid;


    }
}