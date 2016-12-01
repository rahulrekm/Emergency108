package com.apps.ekmcoder.emergency108;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

/*import com.apps.ekmcoder.emergency108.R;*/

/**
 * Created by alan on 11/24/2016.
 */

public class NotificationClass {
    public static void notifyUser(Context notiContext,String title, String content) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(notiContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setAutoCancel(false)
                        .setContentText(content);
        Intent resultIntent = new Intent(notiContext, FirstActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(notiContext);
        stackBuilder.addParentStack(FirstActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) notiContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
