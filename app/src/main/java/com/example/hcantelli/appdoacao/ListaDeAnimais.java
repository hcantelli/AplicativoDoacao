package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListaDeAnimais extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference bancoDeDados_firebase;
    private ListView listaDeAnimais;
    private ArrayList<String> vetor_animais_02 = new ArrayList<>();
    private ArrayList<String> listaDeAnimais_vetor = new ArrayList<>();
    private ArrayList<String> listaDeAnimais_vetor_fotos = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ListaDeAnimais.this, TelaUsuario.class));
        finish();
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeanimais);

        Intent intent = getIntent();
        ArrayList<Double> compatibilidadePorAnimal = (ArrayList<Double>) intent.getSerializableExtra("CompatibilidadeAnimal");
        final ArrayList<String> idAnimal = intent.getStringArrayListExtra("idAnimal");
        final Map<Double, String> vetor_animais = new HashMap<>();
        final ArrayList<String> idAnimalOrdenado = new ArrayList<>();

        //Ordenando o vetor de Compatibilidade com o vetor de Identificadores de Animais
        for(int count = 0; count < idAnimal.size();){
            vetor_animais.put(compatibilidadePorAnimal.get(count), idAnimal.get(count));
            count++;
        }
        //Ordenando o vetor de Compatibilidade com o vetor de Identificadores de Animais
        Collections.sort(compatibilidadePorAnimal);
        //Ordenando o vetor de Compatibilidade com o vetor de Identificadores de Animais
        for(Double s : compatibilidadePorAnimal){
            vetor_animais.get(s);
            idAnimalOrdenado.add(vetor_animais.get(s));
        }

        final Adaptador vetorAdaptado = new Adaptador(this, listaDeAnimais_vetor, listaDeAnimais_vetor_fotos);

        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();
        listaDeAnimais = (ListView) findViewById(R.id.listaDeAnimais);

        //Evento para armazenar os nomes de cada animal em uma lista
        bancoDeDados_firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(int count = 0; count < dataSnapshot.child("Animais").getChildrenCount();){
                    listaDeAnimais_vetor.add((String) dataSnapshot.child("Animais").child(idAnimalOrdenado.get(count)).child("Caracteristicas").child("nomeAnimal").getValue());
                    listaDeAnimais_vetor_fotos.add((String) dataSnapshot.child("Animais").child(idAnimalOrdenado.get(count)).child("Caracteristicas").child("fotoAnimal").getValue());
                    count++;
                }
                vetorAdaptado.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listaDeAnimais.setAdapter(vetorAdaptado);
        vetor_animais_02.addAll(idAnimalOrdenado);
        listaDeAnimais.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String idAnimal;
        idAnimal = vetor_animais_02.get(position);
        Intent intent = new Intent(ListaDeAnimais.this, TelaAnimal.class);
        intent.putExtra("idAnimal", idAnimal);
        startActivity(intent);
    }

}

