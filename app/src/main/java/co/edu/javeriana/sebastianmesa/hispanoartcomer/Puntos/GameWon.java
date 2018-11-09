package co.edu.javeriana.sebastianmesa.hispanoartcomer.Puntos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.IndexView;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

public class GameWon extends Activity {

    TextView timeUpCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won);

        timeUpCoins = (TextView)findViewById(R.id.label_coins_won);

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

    }

    //This is onclick listener for button
    //it will navigate from this activity to MainGameActivity
    public void PlayAgain(View view) {
        Intent intent = new Intent(GameWon.this, MainGameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void volver(View view){
        Intent intent = new Intent(GameWon.this,IndexView.class);
        startActivity(intent);
        finish();
    }
}
