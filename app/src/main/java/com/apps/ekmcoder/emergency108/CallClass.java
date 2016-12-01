package com.apps.ekmcoder.emergency108;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by alan on 11/24/2016.
 */

public class CallClass {
    static void callAlertEmergency(Context callContext,String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        //callIntent.setData(Uri.parse("tel:+919895670679"));
        callIntent.setData(Uri.parse("tel:"+number));

        callIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        callIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        try {
            callContext.startActivity(callIntent);
            Log.d("#### log ####", "------try call---BackgroundCall--");
        } catch (Exception e) {
            Log.d("#### log ####", "------catch call---BackgroundCall--" + e.getMessage().toString());
        }
    }
}
