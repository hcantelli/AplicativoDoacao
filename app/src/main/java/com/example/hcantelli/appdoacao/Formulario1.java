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

public class Formulario1 extends AppCompatActivity {

    private RadioGroup grupoPergunta1, grupoPergunta2;
    private RadioButton botao_radio1, botao_radio2;
    private Button botao_formulario1;
    private DatabaseReference bancoDeDados_firebase;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario1);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String idUsuario = bundle.getString("Usuarios");

        grupoPergunta1 = (RadioGroup) findViewById(R.id.grupoPergunta1);
        grupoPergunta2 = (RadioGroup) findViewById(R.id.grupoPergunta2);
        botao_formulario1 = (Button) findViewById(R.id.botao_formulario1);

        botao_formulario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoPergunta1.getCheckedRadioButtonId() == -1 || grupoPergunta2.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Formulario1.this, getText(R.string.formulario_erro), Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Armazena respostas selecionadas na base de dados
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta1").setValue(respostaBotao1());
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta2").setValue(respostaBotao2());
                    Intent intent2 = new Intent(Formulario1.this, Formulario2.class);
                    intent2.putExtra(("Usuarios"),idUsuario);
                    startActivity(intent2);
                }
            }
        });
    }

    public void grupoPergunta1Click (View v){
        int radioButtonId1 = grupoPergunta1.getCheckedRadioButtonId();
        botao_radio1 = (RadioButton) findViewById(radioButtonId1);
    }

    public void grupoPergunta2Click (View v){
        int radioButtonId2 = grupoPergunta2.getCheckedRadioButtonId();
        botao_radio2 = (RadioButton) findViewById(radioButtonId2);
    }
    //Método que garante que ao menos uma opção foi selecionada
    public int respostaBotao1(){
        int respostaBotao1 = 0;
        if (botao_radio1.getText().toString().trim().equals(getText(R.string.textoRadioButton1))){
            respostaBotao1 = 1;
        } else if(botao_radio1.getText().toString().trim().equals(getText(R.string.textoRadioButton2))){
            respostaBotao1 = 2;
        } else if(botao_radio1.getText().toString().trim().equals(getText(R.string.textoRadioButton3))){
            respostaBotao1 = 3;
        } else if(botao_radio1.getText().toString().trim().equals(getText(R.string.textoRadioButton4))){
            respostaBotao1 = 4;
        } else if(botao_radio1.getText().toString().trim().equals(getText(R.string.textoRadioButton5))){
            respostaBotao1 = 5;
        }
        return respostaBotao1;
    }
    //Método que garante que ao menos uma opção foi selecionada
    public int respostaBotao2(){
        int respostaBotao2 = 0;
        if (botao_radio2.getText().toString().trim().equals(getText(R.string.textoRadioButton6))){
            respostaBotao2 = 1;
        } else if(botao_radio2.getText().toString().trim().equals(getText(R.string.textoRadioButton7))){
            respostaBotao2 = 2;
        } else if(botao_radio2.getText().toString().trim().equals(getText(R.string.textoRadioButton8))){
            respostaBotao2 = 3;
        } else if(botao_radio2.getText().toString().trim().equals(getText(R.string.textoRadioButton9))){
            respostaBotao2 = 4;
        } else if(botao_radio2.getText().toString().trim().equals(getText(R.string.textoRadioButton10))){
            respostaBotao2 = 5;
        }
        return respostaBotao2;
    }




}
