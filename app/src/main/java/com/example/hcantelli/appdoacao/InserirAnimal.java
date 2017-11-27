package com.example.hcantelli.appdoacao;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InserirAnimal extends AppCompatActivity{

    private DatabaseReference bancoDeDados_firebase;
    private Button tirarFoto;
    private StorageReference galeriaImagens_firebase;
    private ImageView fotoAnimal;
    private String idUsuarioAtributo;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir_animal);
        bancoDeDados_firebase = FirebaseDatabase.getInstance().getReference().child("Animais");
        galeriaImagens_firebase = FirebaseStorage.getInstance().getReference().child("fotosAnimais");
        fotoAnimal = (ImageView) findViewById(R.id.fotoAnimal);
        tirarFoto = (Button) findViewById(R.id.tirarFoto);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idUsuarioAtributo = bundle.getString("Usuarios");
        requestPermissions(new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

        tirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(InserirAnimal.this.checkSelfPermission(android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    dispatchTakePictureIntent();
                }else{
                    Toast.makeText(InserirAnimal.this,getText(R.string.permissao_camera),Toast.LENGTH_LONG).show();
                    requestPermissions(new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }

                }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

//            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//
//            StorageReference filepath = galeriaImagens_firebase.child(idUsuarioAtributo + ".jpg");
//            fotoAnimal.setImageBitmap(imageBitmap);
//            fotoAnimal.setDrawingCacheEnabled(true);
//            fotoAnimal.buildDrawingCache();
//            Bitmap bitmap = fotoAnimal.getDrawingCache();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data1 = baos.toByteArray();
//
//            UploadTask uploadTask = filepath.putBytes(data1);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                public void onFailure(@NonNull Exception exception) {
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(InserirAnimal.this, "Deu certo.", Toast.LENGTH_LONG).show();
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                }
//            });
        }
    }

    private File createImageFile() throws Exception{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
          imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null ){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(InserirAnimal.this,
                        "com.example.android.fileprovider",
                        photoFile
                );
                //Verificar erro aqui, photoUri está com valor mas o intent vem nulo
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}
