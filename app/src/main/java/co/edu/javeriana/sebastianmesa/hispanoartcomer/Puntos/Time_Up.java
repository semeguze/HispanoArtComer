package co.edu.javeriana.sebastianmesa.hispanoartcomer.Puntos;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.IndexView;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import info.hoang8f.widget.FButton;

public class Time_Up extends AppCompatActivity {
    FButton playAgainButton;
    TextView timeUpText, timeUpCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time__up);
        //Initialize
        playAgainButton = (FButton)findViewById(R.id.playAgainButton);
        timeUpText = (TextView)findViewById(R.id.timeUpText);
        timeUpCoins = (TextView)findViewById(R.id.timeout_coins);

        String newString = "0";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("stringCoins");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("stringCoins");
        }

        timeUpCoins.setText(newString);

        //play again button onclick listener
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Time_Up.this,MainGameActivity.class);
                startActivity(intent);
                finish();


            }
        });


        //Setting typefaces for textview and button - this will give stylish fonts on textview and button
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
//        timeUpText.setTypeface(typeface);
//        playAgainButton.setTypeface(typeface);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void volver(View view){
        Intent intent = new Intent(Time_Up.this,IndexView.class);
        startActivity(intent);
        finish();
    }
}
