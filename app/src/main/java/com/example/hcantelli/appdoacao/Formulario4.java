package com.example.hcantelli.appdoacao;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formulario4 extends AppCompatActivity {

    private RadioGroup grupoPergunta7, grupoPergunta8;
    private RadioButton botao_radio7, botao_radio8;
    private Button botao_formulario4;
    private DatabaseReference bancoDeDados_firebase;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario4);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String idUsuario = bundle.getString("Usuarios");


        grupoPergunta7 = (RadioGroup) findViewById(R.id.grupoPergunta7);
        grupoPergunta8 = (RadioGroup) findViewById(R.id.grupoPergunta8);
        botao_formulario4 = (Button) findViewById(R.id.botao_formulario4);

        botao_formulario4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoPergunta7.getCheckedRadioButtonId() == -1 || grupoPergunta8.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Formulario4.this, getText(R.string.formulario_erro), Toast.LENGTH_LONG).show();
                }
                else
                {
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta7").setValue(respostaBotao7());
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta8").setValue(respostaBotao8());
                    Intent intent2 = new Intent(Formulario4.this, Compatibilidade.class);
                    intent2.putExtra(("Usuarios"), idUsuario);
                    startActivity(intent2);
                }
            }
        });

    }

    public void grupoPergunta7Click (View v){
        int radioButtonId7 = grupoPergunta7.getCheckedRadioButtonId();
        botao_radio7 = (RadioButton) findViewById(radioButtonId7);
    }

    public void grupoPergunta8Click (View v){
        int radioButtonId8 = grupoPergunta8.getCheckedRadioButtonId();
        botao_radio8 = (RadioButton) findViewById(radioButtonId8);
    }
    public int respostaBotao7(){
        int respostaBotao7 = 0;
        if (botao_radio7.getText().toString().trim().equals(getText(R.string.textoRadioButton31))){
            respostaBotao7 = 1;
        } else if(botao_radio7.getText().toString().trim().equals(getText(R.string.textoRadioButton32))){
            respostaBotao7 = 2;
        } else if(botao_radio7.getText().toString().trim().equals(getText(R.string.textoRadioButton33))){
            respostaBotao7 = 3;
        } else if(botao_radio7.getText().toString().trim().equals(getText(R.string.textoRadioButton34))){
            respostaBotao7 = 4;
        } else if(botao_radio7.getText().toString().trim().equals(getText(R.string.textoRadioButton35))){
            respostaBotao7 = 5;
        }
        return respostaBotao7;
    }
    public int respostaBotao8(){
        int respostaBotao8 = 0;
        if (botao_radio8.getText().toString().trim().equals(getText(R.string.textoRadioButton36))){
            respostaBotao8 = 1;
        } else if(botao_radio8.getText().toString().trim().equals(getText(R.string.textoRadioButton37))){
            respostaBotao8 = 2;
        } else if(botao_radio8.getText().toString().trim().equals(getText(R.string.textoRadioButton38))){
            respostaBotao8 = 3;
        } else if(botao_radio8.getText().toString().trim().equals(getText(R.string.textoRadioButton39))){
            respostaBotao8 = 4;
        } else if(botao_radio8.getText().toString().trim().equals(getText(R.string.textoRadioButton40))){
            respostaBotao8 = 5;
        }
        return respostaBotao8;
    }




}
