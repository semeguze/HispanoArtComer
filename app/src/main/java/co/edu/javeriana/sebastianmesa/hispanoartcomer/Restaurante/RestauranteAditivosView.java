package co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.Aditivos;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class RestauranteAditivosView extends AppCompatActivity implements Serializable {

    private TextView titulo, subTitulo, enunciadoPrincipal, descripcionAditivos, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_aditivos_view);

        String tipo = null, fechaDelPlato = null;
        String nombrePlatoPrincipal = null,nombrePlatoSecundario = null,nombrePlatoPostre = null, nombreBebida = null;
        String nombrePlatoNueves = null, nombreBebidaNueves = null;
        String nombrePlatoOnces = null, nombreBebidaOnces = null;
        ArrayList<Aditivos> listaAditivos = new ArrayList<>();

        titulo              = (TextView) findViewById(R.id.nombrePlato);
        subTitulo           = (TextView) findViewById(R.id.miniDescripcionPlato);
        enunciadoPrincipal  = (TextView) findViewById(R.id.enunciadoA);
        descripcion         = (TextView) findViewById(R.id.descipcionAlimento);
        descripcionAditivos = (TextView) findViewById(R.id.descipcionAditivos);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tipo = null;
            } else {
                tipo = extras.getString("typeMenu");
            }
        } else {
            tipo = (String) savedInstanceState.getSerializable("typeMenu");
        }


        if (tipo.equals("almuerzo") || tipo=="almuerzo" ){

            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    nombrePlatoPrincipal = extras.getString("nomPlatoPrincipal");
                    nombrePlatoSecundario = extras.getString("nomPlatoSecundario");
                    nombrePlatoPostre = extras.getString("nomPlatoPostre");
                    nombreBebida = extras.getString("nomBebida");
                    fechaDelPlato = extras.getString("fechaAlimento");
                    listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
                }
            } else {
                nombrePlatoPrincipal = (String) savedInstanceState.getSerializable("nomPlatoPrincipal");
                nombrePlatoSecundario = (String) savedInstanceState.getSerializable("nomPlatoSecundario");
                nombrePlatoPostre = (String) savedInstanceState.getSerializable("nomPlatoPostre");
                nombreBebida = (String) savedInstanceState.getSerializable("nomBebida");
                fechaDelPlato = (String) savedInstanceState.getSerializable("fechaAlimento");
                listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
            }


            titulo.setText(nombrePlatoPrincipal);
            subTitulo.setText("Almuerzo");

            enunciadoPrincipal.setText(fechaDelPlato);
            String aditivosJuntos = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosJuntos = aditivosJuntos + listaAditivos.get(i).getNombre()+"\n";
            }

            if (nombreBebida.equals("") || nombreBebida == ""){
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoPrincipal + "\n * "+
                        nombrePlatoSecundario + "\n * " +
                        nombrePlatoPostre + "\n");
            }else{
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoPrincipal + "\n * "+
                        nombrePlatoSecundario + "\n * " +
                        nombrePlatoPostre + "\n * " +
                        nombreBebida + "\n");
            }

            String aditivosLista = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosLista = aditivosLista + " * " + listaAditivos.get(i).getNombre() + "\n";
            }

            if (listaAditivos.size() > 0){
                descripcionAditivos.setText("Los aditivos que tiene este plato son: " + "\n\n" + aditivosLista);
            }else{
                descripcionAditivos.setText("Este plato no tiene aditivos");
            }

        }

        if (tipo.equals("nueves") || tipo=="nueves" ){

            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    nombrePlatoNueves = extras.getString("nomPlatoNueves");
                    nombreBebidaNueves = extras.getString("nomBebidaNueves");
                    fechaDelPlato = extras.getString("fechaAlimento");
                    listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
                }
            } else {
                nombrePlatoNueves = (String) savedInstanceState.getSerializable("nomPlatoNueves");
                nombreBebidaNueves = (String) savedInstanceState.getSerializable("nomBebidaNueves");
                fechaDelPlato = (String) savedInstanceState.getSerializable("fechaAlimento");
                listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
            }


            titulo.setText(nombrePlatoNueves);
            subTitulo.setText("Nueves");

            enunciadoPrincipal.setText(fechaDelPlato);
            String aditivosJuntos = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosJuntos = aditivosJuntos + listaAditivos.get(i).getNombre()+"\n";
            }

            if (nombreBebidaNueves.equals("") || nombreBebidaNueves == ""){
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoNueves + "\n");
            }else{
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoNueves + "\n * "+
                        nombreBebidaNueves + "\n");
            }

            String aditivosLista = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosLista = aditivosLista + " * " + listaAditivos.get(i).getNombre() + "\n";
            }

            if (listaAditivos.size() > 0){
                descripcionAditivos.setText("Los aditivos que tiene este plato son: " + "\n\n" + aditivosLista);
            }else{
                descripcionAditivos.setText("Este plato no tiene aditivos");
            }



        }


        if (tipo.equals("onces") || tipo=="onces" ){

            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    nombrePlatoOnces = extras.getString("nomPlatoOnces");
                    nombreBebidaOnces = extras.getString("nomBebidaOnces");
                    fechaDelPlato = extras.getString("fechaAlimento");
                    listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
                }
            } else {
                nombrePlatoOnces = (String) savedInstanceState.getSerializable("nomPlatoOnces");
                nombreBebidaOnces = (String) savedInstanceState.getSerializable("nomBebidaOnces");
                fechaDelPlato = (String) savedInstanceState.getSerializable("fechaAlimento");
                listaAditivos = (ArrayList<Aditivos>) getIntent().getSerializableExtra("listaDeAditivos");
            }


            titulo.setText(nombrePlatoOnces);
            subTitulo.setText("Onces");

            enunciadoPrincipal.setText(fechaDelPlato);
            String aditivosJuntos = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosJuntos = aditivosJuntos + listaAditivos.get(i).getNombre()+"\n";
            }

            if (nombreBebidaOnces.equals("") || nombreBebidaOnces == ""){
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoOnces + "\n");
            }else{
                descripcion.setText("Este plato está compuesto por: \n\n * " +
                        nombrePlatoOnces + "\n * "+
                        nombreBebidaOnces + "\n");
            }

            String aditivosLista = "";

            for (int i = 0; i < listaAditivos.size() ; i++){
                aditivosLista = aditivosLista + " * " + listaAditivos.get(i).getNombre() + "\n";
            }

            if (listaAditivos.size() > 0){
                descripcionAditivos.setText("Los aditivos que tiene este plato son: " + "\n\n" + aditivosLista);
            }else{
                descripcionAditivos.setText("Este plato no tiene aditivos");
            }



        }


    }
}
