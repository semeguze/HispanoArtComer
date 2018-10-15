package co.edu.javeriana.sebastianmesa.hispanoartcomer.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.IndexView;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.PrefManager;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class CompleteInfoView extends AppCompatActivity {

    private EditText sexo, curso, edad, peso, estatura;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private PrefManager prefManager;

    private final String nameDB = "usuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);

        Log.d("ACKI", "isInfoComplete (onCreate en CompleteInfoView)-> " + prefManager.isInfoComplete());
        Log.d("ACKI", "isFisrComplete (onCreate en CompleteInfoView)-> " + prefManager.isFirstTimeLaunch());

        if (!prefManager.isInfoComplete()) {
            launchHomeScreenComplete();
            finish();
        }

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

        mDatabase.child(nameDB).child(mAuth.getUid()).child("Sexo").setValue(sexoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Curso").setValue(cursoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Edad").setValue(edadInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Peso").setValue(pesoInfo);
        mDatabase.child(nameDB).child(mAuth.getUid()).child("Estatura").setValue(estaturaInfo);

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

}
