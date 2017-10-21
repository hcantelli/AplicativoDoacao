package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Cadastro extends AppCompatActivity{

    private Button botao_firebase;
    private DatabaseReference bancoDeDados_firebase;
    private EditText view_nomeUsuario, view_email, view_dataDeNascimento, view_telefone, view_endereco, view_cep, view_cpf, view_senha;
    private TextInputLayout inputLayoutNomeUsuario, inputLayoutEmail, inputLayoutDataDeNascimento, inputLayoutTelefone, inputLayoutEndereco, inputLayoutCEP, inputLayoutCPF, inputLayoutSenha;
    private TextWatcher cpfMascara, dataDeNascimentoMascara, telefoneMascara, cepMascara;
    private FirebaseAuth autorizacao_firebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final long usuario = bundle.getLong("Usuario");
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference();
        autorizacao_firebase = FirebaseAuth.getInstance();

        inicializaVariaveis();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        botao_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String senha = view_senha.getText().toString().trim();
                final String email = view_email.getText().toString().trim();

                if(validaVariaveis()){
                    final ProgressDialog progressDialog = ProgressDialog.show(Cadastro.this, getText(R.string.aguarde), getText(R.string.processando), true);

                    (autorizacao_firebase.createUserWithEmailAndPassword(email, senha)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @ Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                bancoDeDados_firebase.child("Usuarios").child("Usuario" + usuario).setValue(registraUsuario()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            (autorizacao_firebase.signInWithEmailAndPassword(email, senha)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(Cadastro.this, getText(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
                                                        Intent intent1 = new Intent(Cadastro.this, TelaUsuario.class);
                                                        startActivity(intent1);
                                                    } else {
                                                        Log.e("ERROR", task.getException().toString());
                                                        Toast.makeText(Cadastro.this, getText(R.string.cadastro_erro), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(Cadastro.this, getText(R.string.cadastro_erro), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(Cadastro.this, getText(R.string.cadastro_erro), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

     private void inicializaVariaveis(){
        botao_firebase = (Button) findViewById(R.id.adiciona_firebase);

        inputLayoutNomeUsuario = (TextInputLayout) findViewById(R.id.inputLayoutNomeUsuario);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutDataDeNascimento = (TextInputLayout) findViewById(R.id.inputLayoutDataDeNascimento);
        inputLayoutTelefone = (TextInputLayout) findViewById(R.id.inputLayoutTelefone);
        inputLayoutEndereco = (TextInputLayout) findViewById(R.id.inputLayoutEndereco);
        inputLayoutCEP = (TextInputLayout) findViewById(R.id.inputLayoutCEP);
        inputLayoutCPF = (TextInputLayout) findViewById(R.id.inputLayoutCPF);
        inputLayoutSenha = (TextInputLayout) findViewById(R.id.inputLayoutSenha);

        view_nomeUsuario = (EditText) findViewById(R.id.nomeUsuario);
        view_email = (EditText) findViewById(R.id.email);
        view_dataDeNascimento = (EditText) findViewById(R.id.dataDeNascimento);
        view_telefone = (EditText) findViewById(R.id.telefone);
        view_endereco = (EditText) findViewById(R.id.endereco);
        view_cep = (EditText) findViewById(R.id.cep);
        view_cpf = (EditText) findViewById(R.id.cpf);
        view_senha = (EditText) findViewById(R.id.senha);

        view_nomeUsuario.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view_nomeUsuario, InputMethodManager.SHOW_IMPLICIT);

        cpfMascara = Mascara.insert("###.###.###-##", view_cpf);
        view_cpf.addTextChangedListener(cpfMascara);

        dataDeNascimentoMascara = Mascara.insert("##/##/####", view_dataDeNascimento);
        view_dataDeNascimento.addTextChangedListener(dataDeNascimentoMascara);

        telefoneMascara = Mascara.insert("(##)#####-####", view_telefone);
        view_telefone.addTextChangedListener(telefoneMascara);

        cepMascara = Mascara.insert("#####-###", view_cep);
        view_cep.addTextChangedListener(cepMascara);

    }

    private HashMap<String, String> registraUsuario(){
        String nomeUsuario = view_nomeUsuario.getText().toString().trim();
        final String email = view_email.getText().toString().trim();
        String dataDeNascimento = view_dataDeNascimento.getText().toString().trim();
        String telefone = view_telefone.getText().toString().trim();
        String endereco = view_endereco.getText().toString().trim();
        String cep = view_cep.getText().toString().trim();
        String cpf = view_cpf.getText().toString().trim();

        final HashMap<String, String> usuarioMap = new HashMap<>();

        usuarioMap.put("nomeUsuario", nomeUsuario);
        usuarioMap.put("email", email);
        usuarioMap.put("dataDeNascimento", dataDeNascimento);
        usuarioMap.put("telefone", telefone);
        usuarioMap.put("endereco", endereco);
        usuarioMap.put("CEP", cep);
        usuarioMap.put("CPF", cpf);

        return usuarioMap;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private boolean validaVariaveis(){
        boolean isValid = true;

        if(view_nomeUsuario.getText().toString().isEmpty()){
            inputLayoutNomeUsuario.setError(getText(R.string.obrigatorio));
            isValid = false;

        } else {
            inputLayoutNomeUsuario.setErrorEnabled(false);
        }

        if(view_email.getText().toString().isEmpty()){
            inputLayoutEmail.setError(getText(R.string.obrigatorio));
            isValid = false;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }

        if(view_dataDeNascimento.getText().toString().isEmpty()){
            inputLayoutDataDeNascimento.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_dataDeNascimento.getText().toString().length() < 10){
            inputLayoutDataDeNascimento.setError(getText(R.string.dataDeNascimentoInvalida));
            isValid = false;
        } else {
            inputLayoutDataDeNascimento.setErrorEnabled(false);
        }

        if(view_telefone.getText().toString().isEmpty()){
            inputLayoutTelefone.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_telefone.getText().toString().length() < 14) {
            inputLayoutTelefone.setError(getText(R.string.telefoneInvalido));
            isValid = false;
        } else {
            inputLayoutTelefone.setErrorEnabled(false);
        }

        if(view_endereco.getText().toString().isEmpty()){
            inputLayoutEndereco.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else {
            inputLayoutEndereco.setErrorEnabled(false);
        }

        if(view_cep.getText().toString().isEmpty()){
            inputLayoutCEP.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_cep.getText().toString().length() < 9) {
            inputLayoutCEP.setError(getText(R.string.cepInvalido));
            isValid = false;
        } else {
            inputLayoutCEP.setErrorEnabled(false);
        }

        if(view_cpf.getText().toString().isEmpty()){
            inputLayoutCPF.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_cpf.getText().toString().length() < 14) {
            inputLayoutCPF.setError(getText(R.string.cpfInvalido));
            isValid = false;
        } else {
            inputLayoutCPF.setErrorEnabled(false);
        }

        if(view_senha.getText().toString().isEmpty()){
            inputLayoutSenha.setError(getText(R.string.obrigatorio));
            isValid = false;
        } else if (view_senha.getText().toString().length() < 8) {
            inputLayoutSenha.setError(getText(R.string.senhaInvalida));
            isValid = false;
        } else {
            inputLayoutSenha.setErrorEnabled(false);
        }


        return isValid;


    }
}