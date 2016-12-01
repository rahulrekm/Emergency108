package com.apps.ekmcoder.emergency108;

import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Created by alan on 11/25/2016.
 */

public class DefaultEmergencySms {
    public static void defaultSms(Context context){
        String address=null;
        try {
            AlertLocationProvider alert = new AlertLocationProvider(context);
            Location location = alert.location;
            address = " http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude()+" ";

            Log.d("#### log ####", "---Location---[" + location.getLatitude() + " , " + location.getLongitude() + " ] " + location.toString());
            //SmsClass.sendSMS(context,EmergencyNumber.number,"default sms "+address);
            NotificationClass.notifyUser(context, "Sms default", address);

        } catch (Exception e) {
            Log.d("### log ###", "---Location--xcptn---" + e.getMessage());
        }
    }
}
