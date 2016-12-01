package com.apps.ekmcoder.emergency108;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

//import com.apps.ekmcoder.emergency108.R;

/**
 * Created by alan on 11/12/2016.
 */
public class AlertAppWidgetProvider extends AppWidgetProvider {
    private static final String MyOnClick = "com.apps.ekmcoder.emergency108.myOnClickTag";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        Log.d("#### log ####", "------onUpdate--AlertAppWidgetProvider--- N" + N);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            Log.d("#### log ####", "------onUpdate----AlertAppWidgetProvider-- for");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.alert_appwidget);
            remoteViews.setOnClickPendingIntent(R.id.button, getPendingSelfIntent(context, MyOnClick));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Log.d("#### log ####", "--------getPendingSelfIntent--AlertAppWidgetProvider---");

        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("#### log ####", "----ONRCV--AlertAppWidgetProvider--");

        if (MyOnClick.equals(intent.getAction())) {
            //your onClick action is here
            Log.d("#### log ####", "-------MyOnClickMyOnClickMyOnClickMyOnClick---AlertAppWidgetProvider---");
            Intent serviceIntent = new Intent(context, BackgroundCall.class);
            context.startService(serviceIntent);


        } else {
            Log.d("#### log ####", intent.getAction().toString() + getClass().toString());

        }


        super.onReceive(context, intent);
    }
}
