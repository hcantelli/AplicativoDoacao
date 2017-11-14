package com.example.hcantelli.appdoacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaUsuario extends AppCompatActivity {

    private TextView nome_exibido;
    private DatabaseReference bancoDeDados_firebase;
    private Button botao_listaAnimais, botao_formulario;

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Desconectar")
                .setMessage("Você tem certeza que gostaria de sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(TelaUsuario.this, getText(R.string.desconectado), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TelaUsuario.this, TelaInicial.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
        return true;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_usuario);

        final FirebaseUser usuarioLogado = FirebaseAuth.getInstance().getCurrentUser();
        final String[] idUsuario = new String[1];

        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();
        nome_exibido = (TextView) findViewById(R.id.nomeUsuarioExibido);
        botao_listaAnimais = (Button) findViewById(R.id.botao_listaAnimais);
        botao_formulario = (Button) findViewById(R.id.botao_formulario);

        bancoDeDados_firebase.child("Usuarios")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> ListaDeIdsUsuarios = new ArrayList<>();
                        String usuarioNome = null;
                        int count;
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ListaDeIdsUsuarios.add(String.valueOf(ds.getKey()));
                        }
                        for (count = 0; count < ListaDeIdsUsuarios.size(); count++){
                            if(usuarioLogado.getEmail().equals(dataSnapshot.child(ListaDeIdsUsuarios.get(count)).child("email").getValue().toString())){
                            usuarioNome = dataSnapshot.child(String.valueOf(ListaDeIdsUsuarios.get(count))).child("nomeUsuario").getValue().toString();
                            break;
                            }
                        }
                        nome_exibido.setText(getText(R.string.bemvindo) + "\n" + usuarioNome + "\n\n" + getText(R.string.selecionar));
                        idUsuario[0] = ListaDeIdsUsuarios.get(count);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

        botao_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaUsuario.this, Formulario1.class);
                intent.putExtra(("Usuarios"), String.valueOf(idUsuario[0]));
                startActivity(intent);
            }
        });

        botao_listaAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bancoDeDados_firebase.child("ResultadoIA").child(idUsuario[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() != 0){
                            Intent intent = new Intent(TelaUsuario.this, Compatibilidade.class);
                            intent.putExtra(("Usuarios"), String.valueOf(idUsuario[0]));
                            startActivity(intent);
                        } else {
                            Toast.makeText(TelaUsuario.this, getText(R.string.listaAnimais_erro), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
