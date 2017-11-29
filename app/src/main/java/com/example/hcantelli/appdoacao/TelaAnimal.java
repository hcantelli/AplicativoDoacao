package com.example.hcantelli.appdoacao;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaAnimal extends AppCompatActivity implements OnMapReadyCallback{

    private TextView nomeAnimal, tamanhoAnimal, pelagemAnimal, corAnimal, idadeAnimal, alergiaAnimal, doencaAnimal, descricaoAnimal;
    private ImageView fotoAnimal;
    private TextView nomeContato, enderecoContato, telefoneContato, emailContato;
    private DatabaseReference bancoDeDados_firebase;
    private GoogleMap mapa_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_animal);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String idAnimal = bundle.getString("idAnimal");

        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference().child("Animais").child(idAnimal);

        //Método para inicializar as variáveis, registrando os valores inseridos pelo usuário na tela
        inicializaVariaveis();
        //Evento realizado para mostrar ao usuários os dados dos animais inseridos na base de dados
        bancoDeDados_firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Picasso.with(TelaAnimal.this).load(dataSnapshot.child("Caracteristicas").child("fotoAnimal").getValue().toString()).placeholder(R.mipmap.placeholder_foto).into(fotoAnimal);

                nomeAnimal.setText(dataSnapshot.child("Caracteristicas").child("nomeAnimal").getValue().toString().trim());
                tamanhoAnimal.setText(dataSnapshot.child("Caracteristicas").child("tamanhoAnimal").getValue().toString().trim());
                pelagemAnimal.setText(dataSnapshot.child("Caracteristicas").child("pelagem").getValue().toString().trim());
                corAnimal.setText(dataSnapshot.child("Caracteristicas").child("cor").getValue().toString().trim());
                idadeAnimal.setText(dataSnapshot.child("Caracteristicas").child("idade").getValue().toString().trim());
                alergiaAnimal.setText(dataSnapshot.child("HistoricoMedico").child("alergias").getValue().toString().trim());
                doencaAnimal.setText(dataSnapshot.child("HistoricoMedico").child("doencas").getValue().toString().trim());
                descricaoAnimal.setText(dataSnapshot.child("Caracteristicas").child("descricao").getValue().toString().trim());

                nomeContato.setText(dataSnapshot.child("Contato").child("nomeContato").getValue().toString().trim());
                enderecoContato.setText(dataSnapshot.child("Contato").child("enderecoContato").getValue().toString().trim());
                telefoneContato.setText(dataSnapshot.child("Contato").child("telefoneContato").getValue().toString().trim());
                emailContato.setText(dataSnapshot.child("Contato").child("emailContato").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    //Método para inicializar as variáveis, registrando os valores inseridos pelo usuário na tela
    private void inicializaVariaveis(){
        nomeAnimal = (TextView) findViewById(R.id.nomeAnimal);
        tamanhoAnimal = (TextView) findViewById(R.id.tamanhoAnimal);
        pelagemAnimal = (TextView) findViewById(R.id.pelagemAnimal);
        corAnimal = (TextView) findViewById(R.id.corAnimal);
        idadeAnimal = (TextView) findViewById(R.id.idadeAnimal);
        nomeContato = (TextView) findViewById(R.id.nomeContato);
        enderecoContato = (TextView) findViewById(R.id.enderecoContato);
        telefoneContato = (TextView) findViewById(R.id.telefoneContato);
        emailContato = (TextView) findViewById(R.id.emailContato);
        fotoAnimal = (ImageView) findViewById(R.id.fotoAnimal);
        alergiaAnimal = (TextView) findViewById(R.id.alergiaAnimal);
        doencaAnimal = (TextView) findViewById(R.id.doencaAnimal);
        descricaoAnimal = (TextView) findViewById(R.id.descricaoAnimal);
    }

    //Método que retorna latitude e longitude de um endereço passado como argumento
    public LatLng pegaLocalizacaoPeloEndereco(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> endereco;
        LatLng p1 = null;
        try {
            endereco = coder.getFromLocationName(strAddress, 5);
            if (endereco == null) {
                return null;
            }
            Address localizacao = endereco.get(0);
            localizacao.getLatitude();
            localizacao.getLongitude();

            p1 = new LatLng(localizacao.getLatitude(), localizacao.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    //Método para gerar o mapa do endereço que se encontra o animal
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa_google = googleMap;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String idAnimal = bundle.getString("idAnimal");
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference().child("Animais").child(idAnimal);

        //Evento realizado para registrar o endereço do Animal
        bancoDeDados_firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LatLng contato = pegaLocalizacaoPeloEndereco(TelaAnimal.this, dataSnapshot.child("Contato").child("enderecoContato").getValue().toString());
                mapa_google.addMarker(new MarkerOptions().position(contato).title(dataSnapshot.child("Contato").child("nomeContato").getValue().toString()));
                mapa_google.moveCamera(CameraUpdateFactory.newLatLngZoom(contato , 15.0f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}