package co.edu.javeriana.sebastianmesa.hispanoartcomer.Cafeteria;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.Aditivos;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.AlimentosMenu;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.RestauranteDetailsView;

public class CafeteriaDetailsView extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    RecyclerView recyclerView;
    SelectableAdapter adapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog pd;
    private ArrayList<AlimentosCafeteria> listaCompletaCafeteria = new ArrayList<AlimentosCafeteria>();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria_details_view);

        inicializarAlimentos();

    }


    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<AlimentosCafeteria> selectedItems = adapter.getSelectedItems();
        Snackbar.make(recyclerView,"Selected item is "+selectableItem.getNombreAlimento()+
                ", Totally  selectem item count is "+selectedItems.size(),Snackbar.LENGTH_LONG).show();
    }

    public void inicializarAlimentos(){

        //Retrieve JSON from storage HispanoArtcomer

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://hispanoartcomer.appspot.com");

        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("Cafeteria/infoCafeteria.json");

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Log.e("firebaseJSON","BIEEEEN!");
                new JsonTask().execute(uri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("firebaseJSON","MAL :(");
            }
        });


    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(CafeteriaDetailsView.this);
            pd.setMessage("Comprobando información de Cafetería nueva");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            LocalDate fechaJSON = null;

            String nombreAlimentoJSON = null;
            String precioAlimentoJSON = null;

            int thumbnailJSON = 0;
            ArrayList<Aditivos> aditivosJSON    = new ArrayList<Aditivos>();

            ArrayList<AlimentosCafeteria> alimentosJSON = new ArrayList<AlimentosCafeteria>();

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                String JSONComplete = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("ResponseJSON: ", "> " + line);
                    JSONComplete = JSONComplete + line;
                }

                try {

                    JSONObject mainJsonObject = new JSONObject(JSONComplete);
                    JSONArray arregloAlimentos = mainJsonObject.getJSONArray("alimentos");
                    //Log.i("ResponseJSON: ", " Arreglo dias size: " + arregloDias.length());

                    int numeroMes = mainJsonObject.getInt("monthDay");
                    int numeroYear = mainJsonObject.getInt ("year");

                    for (int i = 0; i < arregloAlimentos.length(); i++) {

                        JSONObject jsonObject = arregloAlimentos.getJSONObject(i);

                        //int numeroDia = jsonObject.getInt("day");

                        nombreAlimentoJSON = jsonObject.getString("nombre");
                        precioAlimentoJSON = jsonObject.getString("precio");

                        listaCompletaCafeteria.add(new AlimentosCafeteria(nombreAlimentoJSON, precioAlimentoJSON));

                    }


                    //generarLsita();

                }catch (Exception e){
                    Log.e("ResponseJSON: ", "Error generando el JSON" + e);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView = (RecyclerView) findViewById(R.id.selection_list);
            recyclerView.setLayoutManager(layoutManager);
            List<AlimentosCafeteria> selectableItems = listaCompletaCafeteria;
            adapter = new SelectableAdapter(CafeteriaDetailsView.this,selectableItems,true);
            recyclerView.setAdapter(adapter);

        }
    }

}