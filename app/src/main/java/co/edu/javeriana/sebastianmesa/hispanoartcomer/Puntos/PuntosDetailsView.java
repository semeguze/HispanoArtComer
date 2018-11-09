package co.edu.javeriana.sebastianmesa.hispanoartcomer.Puntos;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import info.hoang8f.widget.FButton;

public class PuntosDetailsView extends AppCompatActivity {

    FButton playGame,quit;
    TextView tQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_details_view);

        playGame =(FButton)findViewById(R.id.playGame);
        quit = (FButton) findViewById(R.id.quit);
        tQ = (TextView)findViewById(R.id.tQ);
        //PlayGame button - it will take you to the MainGameActivity
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuntosDetailsView.this, MainGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Quit button - This will quit the game
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Typeface - this is for fonts style
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
//        playGame.setTypeface(typeface);
//        quit.setTypeface(typeface);
//        tQ.setTypeface(typeface);


    }


}
