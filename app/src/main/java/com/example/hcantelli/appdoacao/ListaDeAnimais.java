package com.example.hcantelli.appdoacao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaDeAnimais extends AppCompatActivity{

    private DatabaseReference mDatabase;
    private ListView mUserList;
    private ArrayList<String> mUsernames = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeanimais);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Animais").child("Animal1").child("Caracteristicas");
        mUserList = (ListView) findViewById(R.id.listaDeAnimais);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);

        mUserList.setAdapter(arrayAdapter);
//Arrumar essa lista
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String nomeAnimal = dataSnapshot.getValue().toString().trim();
                mUsernames.add(nomeAnimal);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
