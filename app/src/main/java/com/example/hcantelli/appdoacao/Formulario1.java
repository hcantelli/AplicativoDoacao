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

public class Formulario1 extends AppCompatActivity {

    private RadioGroup grupoPergunta1, grupoPergunta2;
    private RadioButton btn_radio1, btn_radio2;
    private Button btn_formulario1;
    private DatabaseReference mDatabase;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario1);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuarios");


        grupoPergunta1 = (RadioGroup) findViewById(R.id.grupoPergunta1);
        grupoPergunta2 = (RadioGroup) findViewById(R.id.grupoPergunta2);
        btn_formulario1 = (Button) findViewById(R.id.btn_formulario1);

        btn_formulario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta1").setValue(btn_radio1.getText().toString().trim());
                mDatabase.child("Formulario").child("Usuario" + usuario).child("pergunta2").setValue(btn_radio2.getText().toString().trim());
                Intent intent2 = new Intent(Formulario1.this, Formulario2.class);
                intent2.putExtra(("Usuarios"),usuario);
                startActivity(intent2);

            }
        });

    }

    public void grupoPergunta1Click (View v){
        int radioButtonId1 = grupoPergunta1.getCheckedRadioButtonId();
        btn_radio1 = (RadioButton) findViewById(radioButtonId1);
    }

    public void grupoPergunta2Click (View v){
        int radioButtonId2 = grupoPergunta2.getCheckedRadioButtonId();
        btn_radio2 = (RadioButton) findViewById(radioButtonId2);
    }




}
