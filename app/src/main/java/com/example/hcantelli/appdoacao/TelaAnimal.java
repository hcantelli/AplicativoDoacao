package com.example.hcantelli.appdoacao;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.util.List;

public class TelaAnimal extends AppCompatActivity implements OnMapReadyCallback{

    private TextView nomeAnimal, tamanhoAnimal, pelagemAnimal, corAnimal, idadeAnimal;
    private TextView nomeOng, enderecoOng, telefoneOng, emailOng;
    private DatabaseReference mDatabase;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_animal);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Animais").child("Animal1");

        inicializaVariaveis();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomeAnimal.setText(dataSnapshot.child("Caracteristicas").child("nomeAnimal").getValue().toString().trim());
                tamanhoAnimal.setText(dataSnapshot.child("Caracteristicas").child("tamanhoAnimal").getValue().toString().trim());
                pelagemAnimal.setText(dataSnapshot.child("Caracteristicas").child("pelagem").getValue().toString().trim());
                corAnimal.setText(dataSnapshot.child("Caracteristicas").child("cor").getValue().toString().trim());
                idadeAnimal.setText(dataSnapshot.child("Caracteristicas").child("idade").getValue().toString().trim());

                nomeOng.setText(dataSnapshot.child("ONG").child("nomeOng").getValue().toString().trim());
                enderecoOng.setText(dataSnapshot.child("ONG").child("endereco").getValue().toString().trim());
                telefoneOng.setText(dataSnapshot.child("ONG").child("telefone").getValue().toString().trim());
                emailOng.setText(dataSnapshot.child("ONG").child("emailOng").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void inicializaVariaveis(){
        nomeAnimal = (TextView) findViewById(R.id.nomeAnimal);
        tamanhoAnimal = (TextView) findViewById(R.id.tamanhoAnimal);
        pelagemAnimal = (TextView) findViewById(R.id.pelagemAnimal);
        corAnimal = (TextView) findViewById(R.id.corAnimal);
        idadeAnimal = (TextView) findViewById(R.id.idadeAnimal);
        nomeOng = (TextView) findViewById(R.id.nomeOng);
        enderecoOng = (TextView) findViewById(R.id.enderecoOng);
        telefoneOng = (TextView) findViewById(R.id.telefoneOng);
        emailOng = (TextView) findViewById(R.id.emailOng);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            //erro nessa linha
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //erro pode estar relacionado a esta linha
        LatLng ong = getLocationFromAddress(TelaAnimal.this,enderecoOng.getText().toString());
        mMap.addMarker(new MarkerOptions().position(ong).title("ONG " + nomeOng.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ong , 15.0f));
    }
}