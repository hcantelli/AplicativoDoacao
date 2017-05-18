package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity{

    private Button mFirebaseButton;
    private DatabaseReference mDatabase;
    private EditText mNameField, view_nomeUsuario, view_email, view_dataDeNascimento, view_telefone, view_endereco, view_cep, view_cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int usuario = bundle.getInt("Usuario");

        mFirebaseButton = (Button) findViewById(R.id.addFirebase);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        view_nomeUsuario = (EditText) findViewById(R.id.nomeUsuario);
        view_email = (EditText) findViewById(R.id.email);
        view_dataDeNascimento = (EditText) findViewById(R.id.dataDeNascimento);
        view_telefone = (EditText) findViewById(R.id.telefone);
        view_endereco = (EditText) findViewById(R.id.endereco);
        view_cep = (EditText) findViewById(R.id.cep);
        view_cpf = (EditText) findViewById(R.id.cpf);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = view_nomeUsuario.getText().toString().trim();
                String email = view_email.getText().toString().trim();
                String dataDeNascimento = view_dataDeNascimento.getText().toString().trim();
                String telefone = view_telefone.getText().toString().trim();
                String endereco = view_endereco.getText().toString().trim();
                String cep = view_cep.getText().toString().trim();
                String cpf = view_cpf.getText().toString().trim();

                mDatabase.child("Usuarios").child("Usuario" + usuario).child("nomeUsuario").setValue(nomeUsuario);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("email").setValue(email);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("dataDeNascimento").setValue(dataDeNascimento);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("telefone").setValue(telefone);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("endereco").setValue(endereco);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("CEP").setValue(cep);
                mDatabase.child("Usuarios").child("Usuario" + usuario).child("CPF").setValue(cpf);

            }
        });
    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}