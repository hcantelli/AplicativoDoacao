package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListaDeAnimais extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference bancoDeDados_firebase;
    private ListView listaDeAnimais;
    private ArrayList<String> listaDeAnimais_vetor = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ListaDeAnimais.this, TelaUsuario.class));
        finish();
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeanimais);

        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference().child("Animais").child("Animal1").child("Caracteristicas").child("nomeAnimal");
        listaDeAnimais = (ListView) findViewById(R.id.listaDeAnimais);

        Intent intent = getIntent();
        ArrayList<Double> compatibilidadePorAnimal = (ArrayList<Double>) intent.getSerializableExtra("CompatibilidadeAnimal");
        ArrayList<Integer> idAnimal = intent.getIntegerArrayListExtra("idAnimal");

        Log.e("ERROR", String.valueOf(compatibilidadePorAnimal));
        Log.e("ERROR", String.valueOf(idAnimal));


        final ArrayAdapter<String> vetorAdaptado = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeAnimais_vetor);

        listaDeAnimais.setAdapter(vetorAdaptado);
//Arrumar lista
        bancoDeDados_firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeAnimal = dataSnapshot.getValue().toString().trim();
                listaDeAnimais_vetor.add(nomeAnimal);
                vetorAdaptado.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listaDeAnimais.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(ListaDeAnimais.this, TelaAnimal.class);
        startActivity(intent);

    }

}
