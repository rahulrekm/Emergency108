package com.apps.ekmcoder.emergency108;

/**
 * Created by alan on 11/24/2016.
 */

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.apps.ekmcoder.emergency108.R;

public class AlertTypeChooser extends AppCompatActivity {

    private Button medical_button;
    private Button police_button;
    private Button fire_button;

    FragmentManager manager = getSupportFragmentManager();
    MedicalAlertFragment medicalAlertFragment = new MedicalAlertFragment();
    PoliceAlertFragment policeAlertFragment=new PoliceAlertFragment();
    FireAlertFragment fireAlertFragment=new FireAlertFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        medical_button = (Button)findViewById(R.id.button_medical);
        police_button = (Button)findViewById(R.id.button_police);
        fire_button  = (Button)findViewById(R.id.button_fire);

        medical_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), " Medical Emergency button clicked", Toast.LENGTH_LONG).show();
                showMedicalDialogBox();

            }
        });

        police_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), " Police Button clicked", Toast.LENGTH_LONG).show();
                showPoliceDialogBox();
            }
        });

        fire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), " Fire Emergency button clicked", Toast.LENGTH_LONG).show();
                showFireDialogBox();
            }
        });

    }

    private void showMedicalDialogBox() {
        DialogFragment fragment = medicalAlertFragment.newInstance(R.string.app_name);
        fragment.show(getFragmentManager(),"dialog");

    }
    private void showPoliceDialogBox() {
        DialogFragment fragment = policeAlertFragment.newInstance(R.string.app_name);
        fragment.show(getFragmentManager(),"dialog");

    }
    private void showFireDialogBox() {
        DialogFragment fragment = fireAlertFragment.newInstance(R.string.app_name);
        fragment.show(getFragmentManager(),"dialog");

    }
}