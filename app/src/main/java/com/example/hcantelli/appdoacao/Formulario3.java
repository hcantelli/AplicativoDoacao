package com.example.hcantelli.appdoacao;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formulario3 extends AppCompatActivity {

    private RadioGroup grupoPergunta5, grupoPergunta6;
    private RadioButton botao_radio5, botao_radio6;
    private Button botao_formulario3;
    private DatabaseReference bancoDeDados_firebase;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario3);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuarios");


        grupoPergunta5 = (RadioGroup) findViewById(R.id.grupoPergunta5);
        grupoPergunta6 = (RadioGroup) findViewById(R.id.grupoPergunta6);
        botao_formulario3 = (Button) findViewById(R.id.botao_formulario3);

        botao_formulario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bancoDeDados_firebase.child("Formulario").child("Usuario" + usuario).child("pergunta5").setValue(botao_radio5.getText().toString().trim());
                bancoDeDados_firebase.child("Formulario").child("Usuario" + usuario).child("pergunta6").setValue(botao_radio6.getText().toString().trim());
                Intent intent2 = new Intent(Formulario3.this, Formulario4.class);
                intent2.putExtra(("Usuarios"),usuario);
                startActivity(intent2);

            }
        });

    }

    public void grupoPergunta5Click (View v){
        int radioButtonId5 = grupoPergunta5.getCheckedRadioButtonId();
        botao_radio5 = (RadioButton) findViewById(radioButtonId5);
    }

    public void grupoPergunta6Click (View v){
        int radioButtonId6 = grupoPergunta6.getCheckedRadioButtonId();
        botao_radio6 = (RadioButton) findViewById(radioButtonId6);
    }




}
