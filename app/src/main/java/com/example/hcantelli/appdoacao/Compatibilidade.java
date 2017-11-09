package com.example.hcantelli.appdoacao;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Compatibilidade extends AppCompatActivity {
    private DatabaseReference bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compatibilidade);

        final double[] compatibilidade = {0};
        final ArrayList<Double> compatibilidadePorAnimal = new ArrayList<>();
        final ArrayList<Integer> idAnimal = new ArrayList<>();
        final int[] count = new int[1];

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String idUsuario = bundle.getString("Usuarios");

        final ProgressDialog progressDialog = ProgressDialog.show(Compatibilidade.this, getText(R.string.aguarde), getText(R.string.calculo_compatibilidade), true);
        progressDialog.show();

        bancoDeDados_firebase
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<Double> caracteristicasAdotante = new ArrayList<>();
                        final ArrayList<Double> caracteristicasAnimal = new ArrayList<>();
                        for(count[0] = 0; count[0] < dataSnapshot.child("Animais").getChildrenCount(); count[0]++) {
                            bancoDeDados_firebase.child("ResultadoIA").child(idUsuario)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                caracteristicasAdotante.add((Double) ds.getValue());
                                            }
                                            bancoDeDados_firebase.child("Animais").child("Animal" + count[0]).child("Personalidade")
                                                    .addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                                caracteristicasAnimal.add((Double) ds.getValue());
                                                            }
                                                            for (int count2 = 0; count2 < 10; count2++){
                                                                compatibilidade[0] += Math.pow(caracteristicasAdotante.get(count2) - caracteristicasAnimal.get(count2), 2);
                                                            }
                                                            compatibilidadePorAnimal.add(Math.sqrt(compatibilidade[0]));
                                                            idAnimal.add(count[0]);
                                                            Handler handler = new Handler();
                                                            handler.postDelayed(new Runnable() {
                                                                public void run() {
                                                                    progressDialog.dismiss();
                                                                    Intent intent = new Intent(Compatibilidade.this, ListaDeAnimais.class);
                                                                    intent.putExtra(("CompatibilidadeAnimal"), compatibilidadePorAnimal);
                                                                    intent.putExtra(("idAnimal"), idAnimal);
                                                                    startActivity(intent);
                                                                }
                                                            }, 5000);

                                                        }
                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                        }
                                                    });
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
                );
    }
}
