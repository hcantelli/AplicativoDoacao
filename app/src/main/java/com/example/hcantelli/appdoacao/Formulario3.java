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

public class Formulario3 extends AppCompatActivity {

    private RadioGroup grupoPergunta5, grupoPergunta6;
    private RadioButton btn_radio5, btn_radio6;
    private Button btn_formulario3;
    private DatabaseReference mDatabase;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario3);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuarios");


        grupoPergunta5 = (RadioGroup) findViewById(R.id.grupoPergunta5);
        grupoPergunta6 = (RadioGroup) findViewById(R.id.grupoPergunta6);
        btn_formulario3 = (Button) findViewById(R.id.btn_formulario3);

        btn_formulario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta5").setValue(btn_radio5.getText().toString().trim());
                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta6").setValue(btn_radio6.getText().toString().trim());
                Intent intent2 = new Intent(Formulario3.this, Formulario4.class);
                intent2.putExtra(("Usuarios"),usuario);
                startActivity(intent2);

            }
        });

    }

    public void grupoPergunta5Click (View v){
        int radioButtonId5 = grupoPergunta5.getCheckedRadioButtonId();
        btn_radio5 = (RadioButton) findViewById(radioButtonId5);
    }

    public void grupoPergunta6Click (View v){
        int radioButtonId6 = grupoPergunta6.getCheckedRadioButtonId();
        btn_radio6 = (RadioButton) findViewById(radioButtonId6);
    }




}
