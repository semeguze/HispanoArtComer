package co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.Aditivos;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.AlimentosMenu;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos.AlimentosRestauranteAdapter;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.decorators.EventDecorator;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.decorators.HighlightWeekendsDecorator;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.decorators.OneDayDecorator;

import org.json.*;

public class RestauranteDetailsView extends AppCompatActivity implements OnDateSelectedListener,
        OnDateLongClickListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private RecyclerView recyclerView;
    private TextView informeSobreCantidadPlatos;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog pd;

    private AlimentosRestauranteAdapter adapterAlmuerzo;
    private ArrayList<AlimentosMenu> listaMenuPorDiasAlmuerzo;
    private ArrayList<AlimentosMenu> listaCompletaMenu = new ArrayList<AlimentosMenu>();

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.parent)
    ViewGroup parent;

    @BindView(R.id.textViewRestaurante)
    TextView textViewRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_details_view);

        ButterKnife.bind(this);
        listaMenuPorDiasAlmuerzo = new ArrayList<>();
        LocalDate hoy = CalendarDay.today().getDate();


        inicializarMenuTotal();

        widget.addDecorators(new HighlightWeekendsDecorator(), oneDayDecorator);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        final LocalDate instance = LocalDate.now();
        widget.setSelectedDate(instance);

        final LocalDate min = LocalDate.of(instance.getYear(), Month.JANUARY, 1);
        final LocalDate max = LocalDate.of(instance.getYear(), Month.DECEMBER, 31);

        widget.state().edit().setMinimumDate(min).setMaximumDate(max).commit();
        widget.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();


        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

        widget.setOnDateChangedListener(this);
        widget.setOnDateLongClickListener(this);

        //Setup initial text
        textViewRes.setText(FORMATTER.format(CalendarDay.today().getDate()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_restaurante);


        adapterAlmuerzo = new AlimentosRestauranteAdapter(this, listaMenuPorDiasAlmuerzo, listaCompletaMenu);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RestauranteDetailsView.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterAlmuerzo);


        prepareAlbums(hoy);

    }

    public void clear() {
        final int size = listaMenuPorDiasAlmuerzo.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listaMenuPorDiasAlmuerzo.remove(0);
            }

            adapterAlmuerzo.notifyItemRangeRemoved(0, size);
        }
    }

    public void inicializarMenuTotal(){

        //Retrieve JSON from storage HispanoArtcomer

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://hispanoartcomer.appspot.com");

        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("Restaurante/infoMenu.json");

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

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
        textViewRes.setText(selected ? FORMATTER.format(date.getDate()) : "No Selection");
        LocalDate seleccionado = date.getDate();
        clear();
        prepareAlbums(seleccionado);
    }

    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        final String text = String.format("%s is available", FORMATTER.format(date.getDate()));
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

//    @OnCheckedChanged(R.id.calendar_mode)
//    void onCalendarModeChanged(boolean checked) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            TransitionManager.beginDelayedTransition(parent);
//        }
//        final CalendarMode mode = checked ? CalendarMode.MONTHS : CalendarMode.WEEKS;
//        widget.state().edit().setCalendarDisplayMode(mode).commit();
//    }


    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums(LocalDate hoy) {
        int[] covers = new int[]{
                R.drawable.ic_food,
                R.drawable.ic_food,
                R.drawable.ic_food,
                R.drawable.ic_food};
        int cantidadPlatosPorDia = 0;
        informeSobreCantidadPlatos = (TextView) findViewById(R.id.label_Rec_View_Rest);

        for (int i = 0; i< listaCompletaMenu.size(); i++){
            if (textViewRes.getText().equals(FORMATTER.format(listaCompletaMenu.get(i).getFecha()))){
                listaMenuPorDiasAlmuerzo.add(listaCompletaMenu.get(i));

                cantidadPlatosPorDia++;
            }
        }


        if (cantidadPlatosPorDia == 0 ){
            informeSobreCantidadPlatos.setText("No hay platos para hoy");
        }else{
            informeSobreCantidadPlatos.setText("");
        }

        adapterAlmuerzo.notifyDataSetChanged();
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(RestauranteDetailsView.this);
            pd.setMessage("Comprobando información de menú nueva");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            LocalDate fechaJSON = null;

            //Almuerzo
            String platoPrincipalJSON = null;
            String platoSegundarioJSON = null;
            String platoPostreJSON = null;
            String bebidaJSON = null;

            //Nueves
            String platoNuevesJSON  = null;
            String bebidaNuevesJSON = null;

            //Onces
            String platoOncesJSON  = null;
            String bebidaOncesJSON = null;

            int thumbnailJSON = 0;
            ArrayList<Aditivos> aditivosJSON    = new ArrayList<Aditivos>();

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
                    JSONArray arregloDias = mainJsonObject.getJSONArray("days");
                    //Log.i("ResponseJSON: ", " Arreglo dias size: " + arregloDias.length());

                    int numeroMes = mainJsonObject.getInt("monthDay");
                    int numeroYear = mainJsonObject.getInt ("year");

                    for (int i = 0; i < arregloDias.length(); i++) {

                        JSONObject jsonObject = arregloDias.getJSONObject(i);
                        JSONArray arregloOpcionesMenuEnDias = jsonObject.getJSONArray("menu-options");

                        int numeroDia = jsonObject.getInt("day");

                        fechaJSON = CalendarDay.from(numeroYear,numeroMes,numeroDia).getDate();

                        // PREPARE THE CARDS ABOUT: Almuerzo

                        for (int j = 0; j < arregloOpcionesMenuEnDias.length(); j++) {

                            JSONObject jsonObjectAlmuerzo = arregloOpcionesMenuEnDias.getJSONObject(j);
                            JSONObject almuerzoObj = jsonObjectAlmuerzo.getJSONObject("almuerzo") ;

                            platoPrincipalJSON  = almuerzoObj.getString("platoPrincipal");
                            platoSegundarioJSON = almuerzoObj.getString("platoSegundario");
                            platoPostreJSON     = almuerzoObj.getString("platoPostre");
                            bebidaJSON          = almuerzoObj.getString("bebida");

                            JSONArray jsonAditivos = almuerzoObj.getJSONArray("aditivos");

                            //Log.i("ResponseJSON: ", " Arreglo jsonAditivos size: " + jsonAditivos.length());

                            for (int k = 0; k < jsonAditivos.length(); k++) {

                                JSONObject jsonAditivosDetails = jsonAditivos.getJSONObject(k);

                                String aditivoNombre   = jsonAditivosDetails.getString("nombre") ;

                                Aditivos aditivo = new Aditivos(aditivoNombre);
                                aditivosJSON.add(aditivo);

                            }

                            listaCompletaMenu.add(new AlimentosMenu("almuerzo", platoPrincipalJSON,platoSegundarioJSON,platoPostreJSON,bebidaJSON,thumbnailJSON,fechaJSON, aditivosJSON));
                            aditivosJSON = new ArrayList<Aditivos>();

                        }

                        // PREPARE THE CARDS ABOUT: Nueves

                        for (int j = 0; j < arregloOpcionesMenuEnDias.length(); j++) {

                            JSONObject jsonObjectAlmuerzo = arregloOpcionesMenuEnDias.getJSONObject(j);
                            JSONObject almuerzoObj = jsonObjectAlmuerzo.getJSONObject("nueves") ;

                            platoNuevesJSON   = almuerzoObj.getString("plato");
                            bebidaNuevesJSON  = almuerzoObj.getString("bebida");

                            JSONArray jsonAditivos = almuerzoObj.getJSONArray("aditivos");

                            //Log.i("ResponseJSON: ", " Arreglo jsonAditivos size: " + jsonAditivos.length());

                            for (int k = 0; k < jsonAditivos.length(); k++) {

                                JSONObject jsonAditivosDetails = jsonAditivos.getJSONObject(k);

                                String aditivoNombre   = jsonAditivosDetails.getString("nombre") ;

                                Aditivos aditivo = new Aditivos(aditivoNombre);
                                aditivosJSON.add(aditivo);

                            }

                            listaCompletaMenu.add(new AlimentosMenu("nueves",platoNuevesJSON,bebidaNuevesJSON,thumbnailJSON,fechaJSON, aditivosJSON));
                            aditivosJSON = new ArrayList<Aditivos>();

                        }

                        // PREPARE THE CARDS ABOUT: Onces

                        for (int j = 0; j < arregloOpcionesMenuEnDias.length(); j++) {

                            JSONObject jsonObjectAlmuerzo = arregloOpcionesMenuEnDias.getJSONObject(j);
                            JSONObject almuerzoObj = jsonObjectAlmuerzo.getJSONObject("onces") ;

                            platoOncesJSON   = almuerzoObj.getString("plato");
                            bebidaOncesJSON  = almuerzoObj.getString("bebida");

                            JSONArray jsonAditivos = almuerzoObj.getJSONArray("aditivos");

                            //Log.i("ResponseJSON: ", " Arreglo jsonAditivos size: " + jsonAditivos.length());

                            for (int k = 0; k < jsonAditivos.length(); k++) {

                                JSONObject jsonAditivosDetails = jsonAditivos.getJSONObject(k);

                                String aditivoNombre   = jsonAditivosDetails.getString("nombre") ;

                                Aditivos aditivo = new Aditivos(aditivoNombre);
                                aditivosJSON.add(aditivo);

                            }

                            listaCompletaMenu.add(new AlimentosMenu("onces",platoOncesJSON,bebidaOncesJSON,thumbnailJSON,fechaJSON, aditivosJSON));
                            aditivosJSON = new ArrayList<Aditivos>();

                        }


                    }

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

            //cosas para hacer con el JSon

            //txtJson.setText(result);
        }
    }
}




