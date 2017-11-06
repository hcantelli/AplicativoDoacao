package com.example.hcantelli.appdoacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaInicial extends AppCompatActivity {

    private Button botao_login;
    private Button botao_cadastro;
    private DatabaseReference bancoDeDados_firebase;

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        botao_cadastro = (Button) findViewById(R.id.botao_cadastro);
        botao_login = (Button) findViewById(R.id.botao_login);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

        botao_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.hcantelli.appdoacao.Cadastro");
                startActivity(intent);
            }
        });

        botao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_login = new Intent("com.example.hcantelli.appdoacao.LogIn");
                startActivity(intent_login);
            }
        });



    }
}