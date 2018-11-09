package co.edu.javeriana.sebastianmesa.hispanoartcomer.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.IndexView;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.PrefManager;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class CompleteInfoView extends AppCompatActivity {

    private EditText sexo, curso, edad, peso, estatura;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private PrefManager prefManager;

    private final String nameDB = "usuarios";

    private boolean laInfoEstaCompleta = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LoginState",  "Estoy en" + this.getLocalClassName());

        prefManager = new PrefManager(this);

        Log.d("ACKI", "isInfoComplete (onCreate en CompleteInfoView)-> " + prefManager.isInfoComplete());
        Log.d("ACKI", "isFisrComplete (onCreate en CompleteInfoView)-> " + prefManager.isFirstTimeLaunch());

//        if (!prefManager.isInfoComplete()) {
//            launchHomeScreenComplete();
//            finish();
//        }

        isAlreadyThere();

        setContentView(R.layout.activity_complete_info_view);
        mAuth = FirebaseAuth.getInstance();

        sexo = findViewById(R.id.campoSexo);
        curso = findViewById(R.id.campoCurso);
        edad = findViewById(R.id.campoEdad);
        peso = findViewById(R.id.campoPeso);
        estatura = findViewById(R.id.campoEstatura);
    }

    public void completeUserBtn(View view){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String sexoInfo = sexo.getText().toString().trim();
        String cursoInfo = curso.getText().toString().trim();
        String edadInfo = edad.getText().toString().trim();
        String pesoInfo = peso.getText().toString().trim();
        String estaturaInfo = estatura.getText().toString().trim();

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

        mDatabase.child(nameDB).child(mAuth.getUid()).child("Sexo").setValue(sexoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Curso").setValue(cursoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Edad").setValue(edadInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Peso").setValue(pesoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Estatura").setValue(estaturaInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Fecha").setValue(timeStamp);

        prefManager.setIsInfoComplete(false);

        startActivity(new Intent(getBaseContext(), IndexView.class));


    }

    private void launchHomeScreenComplete() {
        prefManager.setIsInfoComplete(false);
        Log.d("ACKI", "isInfoComplete (launchHomeScreenComplete en CompleteInfoView)-> " + prefManager.isInfoComplete());
        Log.d("ACKI", "isFisrComplete (launchHomeScreenComplete en CompleteInfoView)-> " + prefManager.isFirstTimeLaunch());
        startActivity(new Intent(CompleteInfoView.this, IndexView.class));
        finish();
    }

    private boolean isAlreadyThere (){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (uid == null){
            startActivity(new Intent(CompleteInfoView.this, LoginView.class));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference();

        db.child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String prueba = snapshot.child("Curso").getValue(String.class);

                Log.i("checkIsAlreadythere", ""+prueba);

                if (prueba != null) {
                    launchHomeScreenComplete();
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return false;

    }


}
