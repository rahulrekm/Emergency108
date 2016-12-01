package com.apps.ekmcoder.emergency108;

/**
 * Created by alan on 11/24/2016.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.apps.ekmcoder.emergency108.R;


public class MedicalAlertFragment extends DialogFragment {

    private  Spinner injuredSpinner;
    private  Spinner typeSpinner;
    private  Spinner conditionSpinner;

    private  ArrayAdapter<String> injuredAdapter;
    private  ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> conditionAdapter;

    String [] noInjured = {"1","2","3","4","5","more"};
    String [] type = {"Accident","Heart Attack","Pregnant","Stroke"};
    String [] condition = {"Worse","Bad","Not Bad "};

    String countInjured;
    String typeOfEmergency;
    String personCondition;
    Location location;


    public static MedicalAlertFragment newInstance(int title){
        MedicalAlertFragment fragment = new MedicalAlertFragment();
        Bundle args = new Bundle();
        args.putInt("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.dialog_medical, container,false);

        injuredSpinner = (Spinner)view.findViewById(R.id.spinner_people);
        injuredAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,noInjured);
        injuredAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        injuredSpinner.setAdapter(injuredAdapter);

        injuredSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                countInjured = injuredSpinner.getSelectedItem().toString();
                Log.i("Selected Item is "," "+countInjured);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner = (Spinner)view.findViewById(R.id.spinner_type);
        typeAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,type);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfEmergency=typeSpinner.getSelectedItem().toString();
                Log.i("Selected Item is "," "+typeOfEmergency);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        conditionSpinner = (Spinner)view.findViewById(R.id.spinner_condition);
        conditionAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,condition);
        conditionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                personCondition=conditionSpinner.getSelectedItem().toString();
                Log.i("Selected Item is "," "+personCondition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText descriptionText = (EditText)view.findViewById(R.id.editText_description);

        Button callButton = (Button)view.findViewById(R.id.button_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Button clicked", "call .....");

                CallClass.callAlertEmergency(getActivity(), EmergencyNumber.callno);
                DefaultEmergencySms.defaultSms(getActivity());

                Log.i(" text"," ");
            }
        });

        Button smsButton = (Button)view.findViewById(R.id.button_sms);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String description = descriptionText.getText().toString();
                final String messageBody=countInjured+" "+typeOfEmergency+" "+personCondition+" "+description;

                Log.i("Button clicked", "sms .....");
                String address=null;
                try {
                    AlertLocationProvider alert = new AlertLocationProvider(getActivity());
                    Location location = alert.location;
                    Toast.makeText(getActivity(), "Sms sending", Toast.LENGTH_SHORT).show();
                    address = " http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude()+" ";

                    Log.d("#### log ####", "---Location---[" + location.getLatitude() + " , " + location.getLongitude() + " ] " + location.toString());
                    SmsClass.sendSMS(getActivity(),EmergencyNumber.number,messageBody+" "+address);
                    NotificationClass.notifyUser(getActivity(), "Sms default", messageBody+" "+address);

                } catch (Exception e) {
                    Log.d("### log ###", "---Location--xcptn---" + e.getMessage());
                    Toast.makeText(getActivity(), "GPS OFFLINE", Toast.LENGTH_SHORT).show();
                }
                Log.i(" text",""+messageBody);
            }
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);

        return dialog;


    }



}

