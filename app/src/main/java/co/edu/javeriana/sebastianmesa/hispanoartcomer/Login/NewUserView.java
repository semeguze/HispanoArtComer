package co.edu.javeriana.sebastianmesa.hispanoartcomer.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.InputStream;
import java.util.regex.Pattern;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.Logic.Entities.dbUser;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Logic.UserData;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class NewUserView extends AppCompatActivity {

    private Bitmap photoBitMap = null;
    private ImageView img;
    private EditText name, email, pass;
    private Pattern emailRegex;
    private FirebaseAuth mAuth;
    private FirebaseStorage bucket;
    private final int MAX_ATTEMPTS = 4;
    private int attempts = 0;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setStatusBarTranslucent(true);

        Log.i("LoginState",  "Estoy en" + this.getLocalClassName());

        setContentView(R.layout.activity_new_user_view);
        emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        mAuth = FirebaseAuth.getInstance();
        bucket = FirebaseStorage.getInstance();
        name = findViewById(R.id.newUserName);
        email = findViewById(R.id.newUserEmail);
        pass = findViewById(R.id.newUserPass);

        titulo = (TextView) findViewById(R.id.titulo);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Cheetah Kick.otf");
        titulo.setTypeface(type);
        titulo.setTextSize(60);

    }

    public void createUserBtn(View view){
//        if(photoBitMap==null){
//            Toast.makeText(this, "Debe seleccionar una foto de la galeria o tomar una nueva", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String emailTxt = email.getText().toString().trim();
        String passTxt = pass.getText().toString().trim();
        Boolean validMail = emailRegex.matcher(emailTxt).matches();
        if(validMail){
            mAuth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        updateInfo();
                    }else{
                        Toast.makeText(getBaseContext(), "Registro fallido por:" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Elementos invalidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateInfo(){
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest updater = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString())
                    .build();
            user.updateProfile(updater).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    FirebaseDatabase.getInstance().getReference(UserData.usersRoot+user.getUid()).setValue(new dbUser(user.getUid(), user.getDisplayName())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getBaseContext(), "Bienvenido!!! 😁", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getBaseContext(), FirstTimeView.class));
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewUserView.this, "Ocurrio un error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                    if(attempts<MAX_ATTEMPTS){
                        attempts++;
                        updateInfo();
                    }else{
                        Toast.makeText(NewUserView.this, "Maximo numero de intentos para actualizar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void cancel(View view){
        finish();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:{
                if(resultCode==RESULT_OK){
                    try{
                        final InputStream imageStream = getContentResolver().openInputStream(data.getData());
                        photoBitMap = BitmapFactory.decodeStream(imageStream);
                        img.setImageBitmap(photoBitMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 2:{
                if(resultCode==RESULT_OK){
                    Bundle extras = data.getExtras();
                    photoBitMap = (Bitmap) extras.get("data");
                    img.setImageBitmap(photoBitMap);
                }
                break;
            }
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
