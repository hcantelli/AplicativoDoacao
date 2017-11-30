package com.example.hcantelli.appdoacao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InserirAnimal extends AppCompatActivity{

    private DatabaseReference bancoDeDados_firebase;
    private Button tirarFoto, inserirAnimal;
    private StorageReference galeriaImagens_firebase;
    private ImageView fotoAnimal;
    private String fotoAnimal_url, generoAnimal, pelagemAnimal, tipoAnimal, tamanhoAnimal,
            idadeAnimal, doencaAnimal, alergiaAnimal,idAnimalAtributo;
    private EditText nomeAnimal, corAnimal, descricaoAnimal, emailContato,
            enderecoContato, nomeContato, telefoneContato;
    private static final String[] generoAnimal_estados = {"Macho", "Fêmea"};
    private static final String[] pelagemAnimal_estados = {"Sem pelos", "Curta", "Média", "Longa", "Muito Longa"};
    private static final String[] tiposAnimal_estados = {"Canino", "Felino"};
    private static final String[] idadeAnimal_estados = {"0 a 3 anos", "Acima de 3 anos e abaixo de 6 anos",
            "Acima de 6 anos e abaixo de 9 anos", "Acima de 9 anos e abaixo de 12 anos", "Acima de 12 anos a 15 anos"};
    private static final String[] doencas_estados = {"Nenhuma", "Não infecciosa", "Infecciosa"};
    private static final String[] alergias_estados = {"Não", "Sim"};
    private static final String[] tamanho_estados = {"Pequeno", "Médio", "Grande"};
    private DiscreteSeekBar agitado,amigavel,carente,carinhoso;
    private double agitado_valor,amigavel_valor,carente_valor,carinhoso_valor,alergia_valor, doenca_valor, genero_valor, idade_valor,
            tipo_valor, pelagem_valor;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextWatcher telefoneMascara;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir_animal);

        inicializaVariaveis();

        requestPermissions(new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

        tirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(InserirAnimal.this.checkSelfPermission(android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    onLaunchCamera();
//                    dispatchTakePictureIntent();
                }else{
                    Toast.makeText(InserirAnimal.this,getText(R.string.permissao_camera),Toast.LENGTH_LONG).show();
                    requestPermissions(new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }

                }
        });

       inserirAnimal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final ProgressDialog progressDialog = ProgressDialog.show(InserirAnimal.this, getText(R.string.aguarde), getText(R.string.processando), true);
               bancoDeDados_firebase.push().child("Caracteristicas").setValue(insereCaracteristicasAnimal()).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           bancoDeDados_firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   ArrayList<String> ListaDeIdsAnimais = new ArrayList<>();
                                   int count;
                                   for(DataSnapshot ds : dataSnapshot.getChildren()){
                                       ListaDeIdsAnimais.add(String.valueOf(ds.getKey()));
                                   }
                                   System.out.println(ListaDeIdsAnimais);
                                   for (count = 0; count < ListaDeIdsAnimais.size(); count++){
                                       if(fotoAnimal_url.equals(dataSnapshot.child(ListaDeIdsAnimais.get(count)).child("Caracteristicas").child("fotoAnimal").getValue().toString())){
                                           idAnimalAtributo = ListaDeIdsAnimais.get(count);
                                       }
                                   }
                                   bancoDeDados_firebase.child(idAnimalAtributo).child("Contato").setValue(insereContato());
                                   bancoDeDados_firebase.child(idAnimalAtributo).child("HistoricoMedico").setValue(insereHistoricoMedicoAnimal());
                                   bancoDeDados_firebase.child(idAnimalAtributo).child("Personalidade").setValue(inserePersonalidade()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               Toast.makeText(InserirAnimal.this, getText(R.string.inserirSucesso), Toast.LENGTH_LONG).show();
                                               Handler handler = new Handler();
                                               handler.postDelayed(new Runnable() {
                                                   public void run() {
                                                       progressDialog.dismiss();
                                                       Intent intent = new Intent(InserirAnimal.this, TelaUsuario.class);
                                                       startActivity(intent);
                                                   }
                                               }, 5000);
                                           }
                                       }
                                   });

                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                               }
                           });
                       }
                   }
               });
           }
       });
    }


    public void inicializaVariaveis(){
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference().child("Animais");
        galeriaImagens_firebase = FirebaseStorage.getInstance().getReference().child("fotosAnimais");
        fotoAnimal = (ImageView) findViewById(R.id.fotoAnimal);
        tirarFoto = (Button) findViewById(R.id.tirarFoto);
        inserirAnimal = (Button) findViewById(R.id.inserirAnimal);
        nomeAnimal = (EditText) findViewById(R.id.nomeAnimal);
        corAnimal = (EditText) findViewById(R.id.corAnimal);
        descricaoAnimal = (EditText) findViewById(R.id.descricaoAnimal);
        emailContato = (EditText) findViewById(R.id.emailContato);
        enderecoContato = (EditText) findViewById(R.id.enderecoContato);
        nomeContato = (EditText) findViewById(R.id.nomeContato);
        telefoneContato = (EditText) findViewById(R.id.telefoneContato);

        agitado = (DiscreteSeekBar) findViewById(R.id.agitadoSeekBar);
        amigavel = (DiscreteSeekBar) findViewById(R.id.amigavelSeekBar);
        carente = (DiscreteSeekBar) findViewById(R.id.carenteSeekBar);
        carinhoso = (DiscreteSeekBar) findViewById(R.id.carinhosoSeekBar);

        telefoneMascara = Mascara.insert("(##)#####-####", telefoneContato);
        telefoneContato.addTextChangedListener(telefoneMascara);

        agitado.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                agitado_valor = value;
            }
            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });
        amigavel.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                amigavel_valor = value;
            }
            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });

        carente.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                carente_valor = value;
            }
            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });
        carinhoso.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                carinhoso_valor = value;
            }
            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });


        Spinner generoSpinner = (Spinner)findViewById(R.id.spinnerGeneroAnimal);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, generoAnimal_estados);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generoSpinner.setAdapter(adaptador1);
        generoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                generoAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner pelagemSpinner = (Spinner)findViewById(R.id.spinnerPelagemAnimal);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, pelagemAnimal_estados);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pelagemSpinner.setAdapter(adaptador2);
        pelagemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                pelagemAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner tipoSpinner = (Spinner)findViewById(R.id.spinnerTipoAnimal);
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, tiposAnimal_estados);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSpinner.setAdapter(adaptador3);
        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                tipoAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner idadeSpinner = (Spinner)findViewById(R.id.spinnerIdadeAnimal);
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, idadeAnimal_estados);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idadeSpinner.setAdapter(adaptador4);
        idadeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                idadeAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner doencasSpinner = (Spinner)findViewById(R.id.spinnerDoencasAnimal);
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, doencas_estados);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doencasSpinner.setAdapter(adaptador5);
        doencasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                doencaAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner alergiasSpinner = (Spinner)findViewById(R.id.spinnerAlergiasAnimal);
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, alergias_estados);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alergiasSpinner.setAdapter(adaptador6);
        alergiasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                alergiaAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        Spinner tamanhoSpinner = (Spinner)findViewById(R.id.spinnerTamanhoAnimal);
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<>(InserirAnimal.this,
                android.R.layout.simple_spinner_item, tamanho_estados);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tamanhoSpinner.setAdapter(adaptador7);
        tamanhoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                tamanhoAnimal = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Random r = new Random();
            int i1 = r.nextInt(180 - 65) + 65;

            final Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            StorageReference filepath = galeriaImagens_firebase.child(i1 + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();

            final ProgressDialog progressDialog = ProgressDialog.show(InserirAnimal.this, null, getText(R.string.upload), true);

            UploadTask uploadTask = filepath.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    fotoAnimal_url = String.valueOf(downloadUrl);
                    progressDialog.dismiss();
                    Picasso.with(InserirAnimal.this).load(downloadUrl).into(fotoAnimal);
                    Toast.makeText(InserirAnimal.this, getText(R.string.upload_sucesso), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private HashMap<String, String> insereCaracteristicasAnimal(){
        final HashMap<String, String> usuarioMap = new HashMap<>();

        usuarioMap.put("cor", corAnimal.getText().toString().trim());
        usuarioMap.put("descricao", descricaoAnimal.getText().toString().trim());
        usuarioMap.put("fotoAnimal", fotoAnimal_url);
        usuarioMap.put("genero", generoAnimal);
        usuarioMap.put("idade", idadeAnimal);
        usuarioMap.put("nomeAnimal", nomeAnimal.getText().toString().trim());
        usuarioMap.put("pelagem", pelagemAnimal);
        usuarioMap.put("tamanhoAnimal", tamanhoAnimal);
        usuarioMap.put("tipoAnimal", tipoAnimal);

        return usuarioMap;
    }
    private HashMap<String, String> insereHistoricoMedicoAnimal(){
        final HashMap<String, String> usuarioMap = new HashMap<>();

        usuarioMap.put("alergias", alergiaAnimal);
        usuarioMap.put("doencas", doencaAnimal);

        return usuarioMap;
    }
    private HashMap<String, String> insereContato(){
        final HashMap<String, String> usuarioMap = new HashMap<>();
        usuarioMap.put("emailContato", emailContato.getText().toString().trim());
        usuarioMap.put("enderecoContato", enderecoContato.getText().toString().trim());
        usuarioMap.put("nomeContato", nomeContato.getText().toString().trim());
        usuarioMap.put("telefoneContato", telefoneContato.getText().toString().trim());

        return usuarioMap;
    }
    private HashMap<String, Double> inserePersonalidade(){
        final HashMap<String, Double> usuarioMap = new HashMap<>();

        switch (alergiaAnimal){
            case "Não": alergia_valor = 1.0; break;
            case "Sim": alergia_valor = 2.0; break;
        }
        switch (doencaAnimal) {
            case "Nenhuma": doenca_valor = 1.0; break;
            case "Não infecciosa": doenca_valor = 2.0; break;
            case "Infecciosa": doenca_valor = 3.0; break;
        }
        switch (generoAnimal){
            case "Macho": genero_valor = 1.0; break;
            case "Fêmea": genero_valor = 2.0; break;
        }
        switch (idadeAnimal){
            case "0 a 3 anos": idade_valor = 1.0; break;
            case "Acima de 3 anos e abaixo de 6 anos": idade_valor = 2.0; break;
            case "Acima de 6 anos e abaixo de 9 anos": idade_valor = 3.0; break;
            case "Acima de 9 anos e abaixo de 12 anos": idade_valor = 4.0; break;
            case "Acima de 12 anos a 15 anos": idade_valor = 5.0; break;
        }
        switch (tipoAnimal){
            case "Canino": tipo_valor = 1.0; break;
            case "Felino": tipo_valor = 2.0; break;
        }
        switch (pelagemAnimal){
            case "Sem pelos": pelagem_valor = 1.0; break;
            case "Curta": pelagem_valor = 2.0; break;
            case "Média": pelagem_valor = 3.0; break;
            case "Longa": pelagem_valor = 4.0; break;
            case "Muito Longa": pelagem_valor = 5.0; break;
        }

        usuarioMap.put("Agitado", agitado_valor);
        usuarioMap.put("Alergias", alergia_valor);
        usuarioMap.put("Amigavel", amigavel_valor);
        usuarioMap.put("Carente", carente_valor);
        usuarioMap.put("Carinhoso", carinhoso_valor);
        usuarioMap.put("Doencas", doenca_valor);
        usuarioMap.put("Genero", genero_valor);
        usuarioMap.put("Idade", idade_valor);
        usuarioMap.put("Pelagem", pelagem_valor);
        usuarioMap.put("Tipo", tipo_valor);

        return usuarioMap;
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(InserirAnimal.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


//    private File createImageFile() throws Exception{
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//          imageFileName,
//                ".jpg",
//                storageDir
//        );
//        return image;
//    }

//    private void dispatchTakePictureIntent(){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null ){
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if(photoFile != null){
//                Uri photoUri = FileProvider.getUriForFile(InserirAnimal.this,
//                        "com.example.android.fileprovider",
//                        photoFile
//                );
//                //Verificar erro aqui, photoUri está com valor mas o intent vem nulo
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

}
