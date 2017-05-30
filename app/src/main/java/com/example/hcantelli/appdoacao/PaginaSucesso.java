package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PaginaSucesso extends AppCompatActivity {

    private Button btn_pagina_inicial;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_sucesso);

        btn_pagina_inicial = (Button) findViewById(R.id.btn_pagina_inicial);

        btn_pagina_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaginaSucesso.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}
