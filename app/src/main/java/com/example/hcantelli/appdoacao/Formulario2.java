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

public class Formulario2 extends AppCompatActivity {

    private RadioGroup grupoPergunta3, grupoPergunta4;
    private RadioButton botao_radio3, botao_radio4;
    private Button botao_formulario2;
    private DatabaseReference bancoDeDados_firebase;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario2);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuarios");


        grupoPergunta3 = (RadioGroup) findViewById(R.id.grupoPergunta3);
        grupoPergunta4 = (RadioGroup) findViewById(R.id.grupoPergunta4);
        botao_formulario2 = (Button) findViewById(R.id.botao_formulario2);

        botao_formulario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bancoDeDados_firebase.child("Formulario").child("Usuario" + usuario).child("pergunta3").setValue(botao_radio3.getText().toString().trim());
                bancoDeDados_firebase.child("Formulario").child("Usuario" + usuario).child("pergunta4").setValue(botao_radio4.getText().toString().trim());
                Intent intent2 = new Intent(Formulario2.this, Formulario3.class);
                intent2.putExtra(("Usuarios"),usuario);
                startActivity(intent2);

            }
        });

    }

    public void grupoPergunta3Click (View v){
        int radioButtonId3 = grupoPergunta3.getCheckedRadioButtonId();
        botao_radio3 = (RadioButton) findViewById(radioButtonId3);
    }

    public void grupoPergunta4Click (View v){
        int radioButtonId4 = grupoPergunta4.getCheckedRadioButtonId();
        botao_radio4 = (RadioButton) findViewById(radioButtonId4);
    }




}
