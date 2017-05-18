package com.example.hcantelli.appdoacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_cadastro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent("com.example.hcantelli.appdoacao.Cadastro");
                intent.putExtra("Usuario",3);
                startActivity(intent);
            }

        });



    }
}