package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_cadastro;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_login = (Button) findViewById(R.id.btn_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_cadastro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent = new Intent("com.example.hcantelli.appdoacao.Cadastro");
                        long count = dataSnapshot.child("Usuarios").getChildrenCount() + 1;
                        intent.putExtra(("Usuario"), count);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_login = new Intent("com.example.hcantelli.appdoacao.LogIn");
                startActivity(intent_login);
            }
        });



    }
}