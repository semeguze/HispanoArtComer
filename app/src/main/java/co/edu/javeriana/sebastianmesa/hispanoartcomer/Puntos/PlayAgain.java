package co.edu.javeriana.sebastianmesa.hispanoartcomer.Puntos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.IndexView;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;


public class PlayAgain extends Activity {

    Button playAgain;
    TextView wrongAnsText;
    TextView timeUpCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_again);
       //Initialize
        playAgain = (Button) findViewById(R.id.playAgainButton);
        wrongAnsText = (TextView)findViewById(R.id.wrongAns);
        timeUpCoins = (TextView)findViewById(R.id.label_coins);

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
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayAgain.this, MainGameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Setting typefaces for textview and button - this will give stylish fonts on textview and button
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
//        playAgain.setTypeface(typeface);
//        wrongAnsText.setTypeface(typeface);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void volver(View view){
        Intent intent = new Intent(PlayAgain.this,IndexView.class);
        startActivity(intent);
        finish();
    }
}
