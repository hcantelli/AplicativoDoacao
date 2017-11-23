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
        final String idUsuario = bundle.getString("Usuarios");


        grupoPergunta3 = (RadioGroup) findViewById(R.id.grupoPergunta3);
        grupoPergunta4 = (RadioGroup) findViewById(R.id.grupoPergunta4);
        botao_formulario2 = (Button) findViewById(R.id.botao_formulario2);

        botao_formulario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoPergunta3.getCheckedRadioButtonId() == -1 || grupoPergunta4.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Formulario2.this, getText(R.string.formulario_erro), Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Armazena respostas selecionadas na base de dados
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta3").setValue(respostaBotao3());
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta4").setValue(respostaBotao4());
                    Intent intent2 = new Intent(Formulario2.this, Formulario3.class);
                    intent2.putExtra(("Usuarios"), idUsuario);
                    startActivity(intent2);
                }
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
    //Método que garante que ao menos uma opção foi selecionada
    public int respostaBotao3(){
        int respostaBotao3 = 0;
        if (botao_radio3.getText().toString().trim().equals(getText(R.string.textoRadioButton11))){
            respostaBotao3 = 1;
        } else if(botao_radio3.getText().toString().trim().equals(getText(R.string.textoRadioButton12))){
            respostaBotao3 = 2;
        } else if(botao_radio3.getText().toString().trim().equals(getText(R.string.textoRadioButton13))){
            respostaBotao3 = 3;
        } else if(botao_radio3.getText().toString().trim().equals(getText(R.string.textoRadioButton14))){
            respostaBotao3 = 4;
        } else if(botao_radio3.getText().toString().trim().equals(getText(R.string.textoRadioButton15))){
            respostaBotao3 = 5;
        }
        return respostaBotao3;
    }
    //Método que garante que ao menos uma opção foi selecionada
    public int respostaBotao4(){
        int respostaBotao4 = 0;
        if (botao_radio4.getText().toString().trim().equals(getText(R.string.textoRadioButton16))){
            respostaBotao4 = 1;
        } else if(botao_radio4.getText().toString().trim().equals(getText(R.string.textoRadioButton17))){
            respostaBotao4 = 2;
        } else if(botao_radio4.getText().toString().trim().equals(getText(R.string.textoRadioButton18))){
            respostaBotao4 = 3;
        } else if(botao_radio4.getText().toString().trim().equals(getText(R.string.textoRadioButton19))){
            respostaBotao4 = 4;
        } else if(botao_radio4.getText().toString().trim().equals(getText(R.string.textoRadioButton20))){
            respostaBotao4 = 5;
        }
        return respostaBotao4;
    }




}
