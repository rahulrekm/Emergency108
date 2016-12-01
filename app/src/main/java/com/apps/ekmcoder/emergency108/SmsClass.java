package com.apps.ekmcoder.emergency108;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by alan on 11/24/2016.
 */

public class SmsClass {
    static void sendSMS(Context smsContext, String phoneNumber, String message) {

        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(smsContext, 0,
                new Intent(smsContext, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(smsContext, 0,
                new Intent(smsContext, SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(smsContext, "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }
}
