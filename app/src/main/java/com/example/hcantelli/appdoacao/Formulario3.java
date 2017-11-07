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
        final String idUsuario = bundle.getString("Usuarios");


        grupoPergunta5 = (RadioGroup) findViewById(R.id.grupoPergunta5);
        grupoPergunta6 = (RadioGroup) findViewById(R.id.grupoPergunta6);
        botao_formulario3 = (Button) findViewById(R.id.botao_formulario3);

        botao_formulario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoPergunta5.getCheckedRadioButtonId() == -1 || grupoPergunta6.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(Formulario3.this, getText(R.string.formulario_erro), Toast.LENGTH_LONG).show();
                }
                else
                {
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta5").setValue(respostaBotao5());
                    bancoDeDados_firebase.child("Formulario").child(idUsuario).child("pergunta6").setValue(respostaBotao6());
                    Intent intent2 = new Intent(Formulario3.this, Formulario4.class);
                    intent2.putExtra(("Usuarios"), idUsuario);
                    startActivity(intent2);
                }
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
    public int respostaBotao5(){
        int respostaBotao5 = 0;
        if (botao_radio5.getText().toString().trim().equals(getText(R.string.textoRadioButton21))){
            respostaBotao5 = 1;
        } else if(botao_radio5.getText().toString().trim().equals(getText(R.string.textoRadioButton22))){
            respostaBotao5 = 2;
        } else if(botao_radio5.getText().toString().trim().equals(getText(R.string.textoRadioButton23))){
            respostaBotao5 = 3;
        } else if(botao_radio5.getText().toString().trim().equals(getText(R.string.textoRadioButton24))){
            respostaBotao5 = 4;
        } else if(botao_radio5.getText().toString().trim().equals(getText(R.string.textoRadioButton25))){
            respostaBotao5 = 5;
        }
        return respostaBotao5;
    }
    public int respostaBotao6(){
        int respostaBotao6 = 0;
        if (botao_radio6.getText().toString().trim().equals(getText(R.string.textoRadioButton26))){
            respostaBotao6 = 1;
        } else if(botao_radio6.getText().toString().trim().equals(getText(R.string.textoRadioButton27))){
            respostaBotao6 = 2;
        } else if(botao_radio6.getText().toString().trim().equals(getText(R.string.textoRadioButton28))){
            respostaBotao6 = 3;
        } else if(botao_radio6.getText().toString().trim().equals(getText(R.string.textoRadioButton29))){
            respostaBotao6 = 4;
        } else if(botao_radio6.getText().toString().trim().equals(getText(R.string.textoRadioButton30))){
            respostaBotao6 = 5;
        }
        return respostaBotao6;
    }




}
