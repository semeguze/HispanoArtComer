package co.edu.javeriana.sebastianmesa.hispanoartcomer.Peso;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class PesoDetailsView extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, NumberPicker.OnValueChangeListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    private static final String CERO = "0";
    private static final String BARRA = "/";

    protected LineChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private TextView fecha;
    public String pesoInicial = null;
    public String fechaInicial = null;

    public ArrayList<Entry> yData;
    public ArrayList<ILineDataSet> dataSets;

    //Relacionado a la selección de fecha
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    EditText etPeso;
    ImageButton ibObtenerFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_details_view);

        //tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        //mSeekBarX = findViewById(R.id.seekBar1);
        //mSeekBarY = findViewById(R.id.seekBar2);

        fecha = (TextView) findViewById(R.id.fechaPesoLabel);
        fecha.setText(FORMATTER.format(CalendarDay.today().getDate()));

        mChart = findViewById(R.id.chart1);
        //mChart.setOnChartValueSelectedListener(this);

        //mChart.setDrawBarShadow(false);
        //mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(true);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextSize(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawLabels(false);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(12, 50);

        // setting data
        //mSeekBarY.setProgress(50);
        //mSeekBarX.setProgress(12);

        //mSeekBarY.setOnSeekBarChangeListener(this);
        //mSeekBarX.setOnSeekBarChangeListener(this);

        mChart.animateX(3000);
        mChart.animateY(3000);
        //mChart.animateXY(9000, 9000);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);

//        int mFillColor = Color.argb(150, 51, 181, 229);
//        mChart.setBackgroundColor(Color.WHITE);
//        mChart.setGridBackgroundColor(mFillColor);
//        mChart.setDrawGridBackground(true);


        etFecha = (EditText) findViewById(R.id.campoFechaPesoIngresarEditText);
        etPeso = (EditText) findViewById(R.id.campoPesoIngresarEditText);
        ibObtenerFecha = (ImageButton) findViewById(R.id.imgCalendario);

        ibObtenerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        mChart.setNoDataText("Cargando datos de la gráfica");
        mChart.setNoDataTextColor(Color.BLACK);


    }




    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(year  + BARRA + mesFormateado + BARRA + diaFormateado);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void setData(int count, float range ) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference();

        mChart = (LineChart) findViewById(R.id.chart1);
        dataSets = new ArrayList<ILineDataSet>();

        db.child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Calendar calender = Calendar.getInstance();

                yData = new ArrayList<>();

                pesoInicial = snapshot.child("Peso").getValue(String.class);
                fechaInicial = snapshot.child("Fecha").getValue(String.class);

                String[] date1 = fechaInicial.split("/");

                calender.set(Integer.parseInt(date1[0]),Integer.parseInt(date1[1]) - 1,Integer.parseInt(date1[2]));
                //calender.set(Integer.parseInt("2018"),Integer.parseInt("10") - 1,Integer.parseInt("10"));

                //int dayOfTheYear = calender.get(Calendar.DATE);
                int dayOfTheYear = calender.get(Calendar.DAY_OF_YEAR);

                Log.i("CheckDate" , "Fecha: " + dayOfTheYear);

                yData.add(new Entry(calender.get(Calendar.DAY_OF_YEAR),Float.parseFloat(pesoInicial)));

                //Recoger info del peso de los usuarios

                String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

//
//                yData.add(new BarEntry(calender.get(Calendar.DAY_OF_YEAR) , 20));
//                yData.add(new BarEntry(calender.get(Calendar.DAY_OF_YEAR)  + 2 , 15));




                LineDataSet set1;

                if (mChart.getData() != null &&
                        mChart.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet ) mChart.getLineData().getDataSetByIndex(0);
                    set1.setValues(yData);

                    mChart.getData().notifyDataChanged();
                    mChart.notifyDataSetChanged();
                }
                  else {

                    set1 = new LineDataSet(yData, "Año " + Calendar.getInstance().get(Calendar.YEAR));

                    set1.setDrawIcons(false);

                    int startColor1 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_light);
                    int startColor2 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_blue_light);
                    int startColor3 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_light);
                    int startColor4 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_green_light);
                    int startColor5 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light);
                    int endColor1 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_blue_dark);
                    int endColor2 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_purple);
                    int endColor3 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_green_dark);
                    int endColor4 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_dark);
                    int endColor5 = ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_dark);

                    List<GradientColor> gradientColors = new ArrayList<>();
                    gradientColors.add(new GradientColor(startColor1, endColor1));
                    gradientColors.add(new GradientColor(startColor2, endColor2));
                    gradientColors.add(new GradientColor(startColor3, endColor3));
                    gradientColors.add(new GradientColor(startColor4, endColor4));
                    gradientColors.add(new GradientColor(startColor5, endColor5));

                    set1.setColor(Color.BLACK);
                    set1.setDrawCircles(true);
                    set1.setLineWidth(2f);
                    set1.setCircleRadius(5f);
                    set1.setFillAlpha(100);
                    set1.setDrawFilled(true);
                    set1.setFillColor(startColor4);
                    set1.setHighLightColor(Color.RED);
                    set1.setDrawCircleHole(true);
                    //setGradientColors(gradientColors);


                    dataSets.add(set1);

                    LineData dataInterna = new LineData(dataSets);
                    dataInterna.setValueTextSize(20f);
//

                    mChart.setData(dataInterna);
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();

                }


//                final LineDataSet lineDataSet = new LineDataSet(yData,"Tu peso");
//                LineData data = new LineData(lineDataSet);
//                data.setValueTextSize(20f);
//
//                mChart.setData(data);
//                mChart.notifyDataSetChanged();
//                mChart.invalidate();

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference db = database.getReference();

                db.child("usuarios").child(uid).child("RegistroPeso").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Calendar calender = Calendar.getInstance();
                        yData = new ArrayList<>();

                        String fechaRegistro = snapshot.getKey();
                        String[] date1 = fechaRegistro.split("-");

                        //calender.set(Integer.parseInt(date1[0]),Integer.parseInt(date1[1]) - 1,Integer.parseInt(date1[2]));
                        calender.set(Integer.parseInt("2018"),Integer.parseInt("11") - 1,Integer.parseInt("11"));

                        Log.i("InfoRegistro","Cantidad[peso]: " + snapshot.getChildrenCount());

                        //LineDataSet set1 = new LineDataSet(yData, "Año " + Calendar.getInstance().get(Calendar.YEAR));
                        //dataSets.get(0).addEntry(new Entry(calender.get(Calendar.DAY_OF_YEAR),Float.parseFloat("50")));
                        //dataSets.get(0).addEntry(new Entry(calender.get(Calendar.DAY_OF_YEAR)+2,Float.parseFloat("60")));//add(set1);


//

                        ArrayList<RegistroPesos> listaRegistroPesos = new ArrayList<RegistroPesos>();

                        for (DataSnapshot dsp : snapshot.getChildren()){
                            Log.i("InfoRegistro","Valores: " + dsp.child("Peso").getValue(Float.class));
                            RegistroPesos aAgregar = new RegistroPesos(dsp.getKey(), dsp.child("Peso").getValue(Float.class) );
                            listaRegistroPesos.add(aAgregar);
                        }

                        Log.i("InfoRegistro","Tam Lista: " + listaRegistroPesos.size());

//                        for(RegistroPesos toSort : listaRegistroPesos){
//                            Log.e("sortList Sin: ", toSort.getPeso().toString());
//                        }

                        Collections.sort(listaRegistroPesos, new Comparator<RegistroPesos>(){
                            public int compare(RegistroPesos obj1, RegistroPesos obj2) {
                                // ## Ascending order
                                return obj1.getFecha().compareToIgnoreCase(obj2.getFecha()); // To compare string values
                                //return Long.valueOf(obj1.getPeso()).compareTo(obj2.getPeso()); // To compare integer values

                                // ## Descending order
                                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
                            }
                        });

//
//                        for(RegistroPesos toSort : listaRegistroPesos){
//                            Log.e("sortList Con: ", toSort.getPeso().toString());
//                        }


                        for (int i = 0; i < listaRegistroPesos.size() ; i++) {
                            String[] fechaIncorporacion = listaRegistroPesos.get(i).getFecha().split("-");

                            Calendar cal = Calendar.getInstance();
                            cal.set(Integer.parseInt(fechaIncorporacion[0]),Integer.parseInt(fechaIncorporacion[1]) - 1,Integer.parseInt(fechaIncorporacion[2]));

                            dataSets.get(0).addEntry(new Entry(cal.get(Calendar.DAY_OF_YEAR), listaRegistroPesos.get(i).getPeso()) );

                        }

                        //dataSets.get(0).addEntry(new Entry(calender.get(Calendar.DAY_OF_YEAR),Float.parseFloat("50")));



                        LineData dataInterna = new LineData(dataSets);
                        dataInterna.setValueTextSize(20f);

                        mChart.setData(dataInterna);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();

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

    public void agregarFechaYPeso(View view){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference();

        etFecha = (EditText) findViewById(R.id.campoFechaPesoIngresarEditText);
        etPeso = (EditText) findViewById(R.id.campoPesoIngresarEditText);

        String rs = etFecha.getText().toString().replace("/","-");

        Log.i("FechaPrueba", "La fecha es: " + rs);

        db.child("usuarios").child(uid).child("RegistroPeso").child(rs).child("Peso").setValue(Float.parseFloat(etPeso.getText().toString()));

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 2));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1 , mSeekBarY.getProgress());
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}