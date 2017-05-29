package com.example.hcantelli.appdoacao;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formulario4 extends AppCompatActivity {

    private RadioGroup grupoPergunta7, grupoPergunta8;
    private RadioButton btn_radio7, btn_radio8;
    private Button btn_formulario4;
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario4);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuarios");


        grupoPergunta7 = (RadioGroup) findViewById(R.id.grupoPergunta7);
        grupoPergunta8 = (RadioGroup) findViewById(R.id.grupoPergunta8);
        btn_formulario4 = (Button) findViewById(R.id.btn_formulario4);

        btn_formulario4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta7").setValue(btn_radio7.getText().toString().trim());
                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta8").setValue(btn_radio8.getText().toString().trim());
                Intent intent2 = new Intent(Formulario4.this, ListaDeAnimais.class);
                startActivity(intent2);

            }
        });

    }

    public void grupoPergunta7Click (View v){
        int radioButtonId7 = grupoPergunta7.getCheckedRadioButtonId();
        btn_radio7 = (RadioButton) findViewById(radioButtonId7);
    }

    public void grupoPergunta8Click (View v){
        int radioButtonId8 = grupoPergunta8.getCheckedRadioButtonId();
        btn_radio8 = (RadioButton) findViewById(radioButtonId8);
    }




}
