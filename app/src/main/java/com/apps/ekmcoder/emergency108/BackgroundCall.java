package com.apps.ekmcoder.emergency108;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by alan on 11/12/2016.
 */

public class BackgroundCall extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("###oncreate service###", "------oncreat---AlertAppWidgetProvider---");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String address = null;
        try {
            AlertLocationProvider alert = new AlertLocationProvider(this);
            Location location = alert.location;
            address = " http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude() + " ";

            Log.d("#### log ####", "---Location---[" + location.getLatitude() + " , " + location.getLongitude() + " ] " + location.toString());
        } catch (Exception e) {
            Log.d("### log ###", "---Location--xcptn---" + e.getMessage());
        }

        Log.d("### log ###", "---address-----" + address);

        final String finalAddress = address;
        new CountDownTimer(10000, 1000) {
            boolean flag = true;
            boolean doVibrate = true;

            public void onTick(long millisUntilFinished) {
                if (doVibrate) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(100);
                }

                long i = millisUntilFinished / 1000;
                KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                if (myKM.inKeyguardRestrictedInputMode()) {
                    if (i <= 1 && flag) {
                        Log.d(" xtra timerz ", " call emergency");
                        doVibrate = false;
                        CallClass.callAlertEmergency(getApplicationContext(), EmergencyNumber.callno);
                        SmsClass.sendSMS(getApplicationContext(),EmergencyNumber.number,"Sms default "+finalAddress);
                        NotificationClass.notifyUser(getApplicationContext(), "call bg sms default", finalAddress);
                    }
                } else {
                    if (flag) {
                        flag = false;
                        doVibrate = false;
                        Log.d(" xtra timerz tick if", "start actvity");
                        startAlertActivity();
                        NotificationClass.notifyUser(getApplicationContext(), "start activity", finalAddress);
                    }
                }
            }

            public void onFinish() {
                Log.d(" timerz finish", "finsied");

            }
        }.start();

            this.stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("#### log ####", "------onDestroy--BackgroundCall-----");
        super.onDestroy();
    }

    public void startAlertActivity() {

        Intent intent = new Intent(this, AlertTypeChooser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
