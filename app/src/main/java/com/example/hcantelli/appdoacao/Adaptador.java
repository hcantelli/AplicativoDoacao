package com.example.hcantelli.appdoacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends ArrayAdapter<String>{

    private ArrayList<String> listaDeAnimais_fotos;
    private ArrayList<String> listaDeAnimais_nomes;
    private Context c;
    private LayoutInflater inflater;

    public Adaptador(Context context, ArrayList<String> listaDeAnimais_nomes, ArrayList<String> listaDeAnimais_fotos) {
        super(context, R.layout.modelo_lista_animais, listaDeAnimais_nomes);

        this.c = context;
        this.listaDeAnimais_nomes = listaDeAnimais_nomes;
        this.listaDeAnimais_fotos = listaDeAnimais_fotos;
    }

    public class ViewHolder{
        TextView nomeAnimal;
        CircleImageView fotoAnimal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.modelo_lista_animais, null);
        }

        final ViewHolder holder = new ViewHolder();

        holder.nomeAnimal = (TextView) convertView.findViewById(R.id.nomeAnimal);
        holder.fotoAnimal = (CircleImageView) convertView.findViewById(R.id.fotoAnimal);

        Picasso.with(c).load(listaDeAnimais_fotos.get(position)).into(holder.fotoAnimal);
        holder.nomeAnimal.setText(listaDeAnimais_nomes.get(position));

        return convertView;
    }
}
