package co.edu.javeriana.sebastianmesa.hispanoartcomer.AboutUs;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import java.util.Calendar;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import mehdi.sakout.aboutpage.Element;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        Element desarrollador = new Element();
        Element grupo = new Element();
        Element empty = new Element();
        Element emoji = new Element();

        adsElement.setTitle("Contactanos");
        desarrollador.setTitle("Desarrollado por: Sebastian Mesa");
        grupo.setTitle("Un proyecto de: Laura Gasca y Valentina Calderón");
        empty.setTitle("");
        emoji.setTitle("👾");

        View aboutPage = new AboutPage(this)
                .setDescription("Hispano Artcomer\n\n" +
                        "¿Hay una forma de mejorar los habitos alimenticios?\n" +
                        "Nosotros creemos que si, y esta app te ayudará a conseguirlo.")
                .isRTL(false)
                .setImage(R.drawable.ic_leon)
                .addItem(new Element().setTitle("Version 0.8"))
                .addItem(grupo)
                .addItem(desarrollador)
                .addItem(empty)
                .addGroup("Algunos links de interés")
                .addEmail("semeguze@hotmail.com", "Contactanos por correo!")
                .addWebsite("http://hispanoamericano.edu.co/", "Conoce más en la página del Hispano!")
                .addTwitter("hispanoconde","Siguienos en Twitter!")
                .addFacebook("colegiohispanobogota","Siguienos en Facebook!")
                .addInstagram("hispanoconde","Siguienos en Instagram!")
                .addItem(empty)
                .addGroup("Quieres contactar con el desarrollador?")
                .addEmail("semeguze@hotmail.com", "Mandale un correo!")
                .addTwitter("semeguze","Siguelo en Twitter!")
                .addItem(empty)
                .addItem(emoji)
                .addItem(empty)
                .create();

        setContentView(aboutPage);
    }


    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
