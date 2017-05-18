package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Cadastro extends AppCompatActivity{

    private Button mFirebaseButton;
    private DatabaseReference mDatabase;
    private EditText view_nomeUsuario, view_email, view_dataDeNascimento, view_telefone, view_endereco, view_cep, view_cpf, view_password;
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

        mFirebaseButton = (Button) findViewById(R.id.addFirebase);
        view_nomeUsuario = (EditText) findViewById(R.id.nomeUsuario);
        view_email = (EditText) findViewById(R.id.email);
        view_dataDeNascimento = (EditText) findViewById(R.id.dataDeNascimento);
        view_telefone = (EditText) findViewById(R.id.telefone);
        view_endereco = (EditText) findViewById(R.id.endereco);
        view_cep = (EditText) findViewById(R.id.cep);
        view_cpf = (EditText) findViewById(R.id.cpf);
        view_password = (EditText) findViewById(R.id.password);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String nomeUsuario = view_nomeUsuario.getText().toString().trim();
        final String email = view_email.getText().toString().trim();
        String dataDeNascimento = view_dataDeNascimento.getText().toString().trim();
        final String telefone = view_telefone.getText().toString().trim();
        String endereco = view_endereco.getText().toString().trim();
        String cep = view_cep.getText().toString().trim();
        String cpf = view_cpf.getText().toString().trim();
        final String senha = view_password.getText().toString().trim();

        final HashMap<String, String> usuarioMap = new HashMap<String, String>();
//Erro aqui
        usuarioMap.put("nomeUsuario", nomeUsuario);
        usuarioMap.put("email", email);
        usuarioMap.put("dataDeNascimento", dataDeNascimento);
        usuarioMap.put("telefone", telefone);
        usuarioMap.put("endereco", endereco);
        usuarioMap.put("CEP", cep);
        usuarioMap.put("CPF", cpf);


        mFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Erro aqui
                final ProgressDialog progressDialog = ProgressDialog.show(Cadastro.this, "Por favor, aguarde...","Processando...", true);
                mDatabase.child("Usuarios").child("Usuario" + usuario).setValue(usuarioMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//Erro aqui
                        if(task.isSuccessful()){
                            Toast.makeText(Cadastro.this, "Cadastro feito com Sucesso!",Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(Cadastro.this,MainActivity.class);
                            startActivity(intent1);
                        } else{
                            Toast.makeText(Cadastro.this, "Ops... Ocorreu algum erro no Cadastro. Tente novamente.",Toast.LENGTH_LONG).show();
                        }

                    }
                });
//Erro aqui
                (firebaseAuth.createUserWithEmailAndPassword(email, senha)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Cadastro.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}