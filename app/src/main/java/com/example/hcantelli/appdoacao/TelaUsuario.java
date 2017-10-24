package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaUsuario extends AppCompatActivity {

    private TextView nome_exibido;
    private DatabaseReference bancoDeDados_firebase;
    private Button botao_listaAnimais, botao_formulario;
    private long contador;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_usuario);

        final FirebaseUser usuarioLogado = FirebaseAuth.getInstance().getCurrentUser();

        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();
        nome_exibido = (TextView) findViewById(R.id.nomeUsuarioExibido);
        botao_listaAnimais = (Button) findViewById(R.id.botao_listaAnimais);
        botao_formulario = (Button) findViewById(R.id.botao_formulario);

        bancoDeDados_firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usuarioNome = null;
                long count;

                for (count = 1; count <= dataSnapshot.child("Usuarios").getChildrenCount(); count++){
                    if(usuarioLogado.getEmail().equals(dataSnapshot.child("Usuarios").child("Usuario" + count).child("email").getValue().toString())){
                        usuarioNome = dataSnapshot.child("Usuarios").child("Usuario" + count).child("nomeUsuario").getValue().toString();
                    }
                }
                nome_exibido.setText(getText(R.string.bemvindo) + "\n" + usuarioNome + "\n\n" + getText(R.string.selecionar));
                contador = count--;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        botao_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaUsuario.this, Formulario1.class);
                intent.putExtra(("Usuarios"), contador);
                startActivity(intent);
            }
        });

        botao_listaAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaUsuario.this, ListaDeAnimais.class);
                startActivity(intent);
            }
        });





    }



}
