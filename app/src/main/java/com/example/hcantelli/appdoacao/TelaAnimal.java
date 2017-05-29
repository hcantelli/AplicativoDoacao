package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaAnimal extends AppCompatActivity {

    private TextView nomeAnimal, tamanhoAnimal, pelagemAnimal, corAnimal, idadeAnimal;
    private TextView nomeOng, enderecoOng, telefoneOng, emailOng;
    private Button btn_paypal;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_animal);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Animais").child("Animal1");

        inicializaVariaveis();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomeAnimal.setText(dataSnapshot.child("Caracteristicas").child("nomeAnimal").getValue().toString().trim());
                tamanhoAnimal.setText(dataSnapshot.child("Caracteristicas").child("tamanhoAnimal").getValue().toString().trim());
                pelagemAnimal.setText(dataSnapshot.child("Caracteristicas").child("pelagem").getValue().toString().trim());
                corAnimal.setText(dataSnapshot.child("Caracteristicas").child("cor").getValue().toString().trim());
                idadeAnimal.setText(dataSnapshot.child("Caracteristicas").child("idade").getValue().toString().trim());

                nomeOng.setText(dataSnapshot.child("ONG").child("nomeOng").getValue().toString().trim());
                enderecoOng.setText(dataSnapshot.child("ONG").child("endereco").getValue().toString().trim());
                telefoneOng.setText(dataSnapshot.child("ONG").child("telefone").getValue().toString().trim());
                emailOng.setText(dataSnapshot.child("ONG").child("emailOng").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//fazer webeservice com mapsapi, pegar lat e long, clicar na imagem e enviar.
//        Intent intent = new Intent(TelaAnimal.this, LocalizacaoOng.class);
//        startActivity(intent);
        btn_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent intent = new Intent(TelaAnimal.this, LocalizacaoOng.class);
        startActivity(intent);
            }
        });

    }

    private void inicializaVariaveis(){
        nomeAnimal = (TextView) findViewById(R.id.nomeAnimal);
        tamanhoAnimal = (TextView) findViewById(R.id.tamanhoAnimal);
        pelagemAnimal = (TextView) findViewById(R.id.pelagemAnimal);
        corAnimal = (TextView) findViewById(R.id.corAnimal);
        idadeAnimal = (TextView) findViewById(R.id.idadeAnimal);
        nomeOng = (TextView) findViewById(R.id.nomeOng);
        enderecoOng = (TextView) findViewById(R.id.enderecoOng);
        telefoneOng = (TextView) findViewById(R.id.telefoneOng);
        emailOng = (TextView) findViewById(R.id.emailOng);
        btn_paypal = (Button) findViewById(R.id.btn_paypal);
    }
}