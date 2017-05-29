package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeAnimais extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DatabaseReference mDatabase;
    private ListView listaDeAnimais;
    private ArrayList<String> listaDeAnimais_array = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeanimais);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Animais").child("Animal1").child("Caracteristicas").child("nomeAnimal");
        listaDeAnimais = (ListView) findViewById(R.id.listaDeAnimais);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDeAnimais_array);

        listaDeAnimais.setAdapter(arrayAdapter);
//Arrumar lista
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeAnimal = dataSnapshot.getValue().toString().trim();
                listaDeAnimais_array.add(nomeAnimal);
                arrayAdapter.notifyDataSetChanged();
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
