package com.example.anis.widget_habbit;


import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.drawableText.DataSource;
import com.example.yacinebenkaidali.habit_tracker.DataEntryDialog;

import DB.Datasrc;

public class CollectionWidget extends AppWidgetProvider {
    public static int nbr=2;
    public static final String ACTION_TOAST = "ACTION_TOAST";
    public static final String EXTRA_STRING = "EXTRA_STRING";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.content_appel_widget);
            views.setOnClickPendingIntent(R.id.linearwidget, buildButtonPendingIntent(context));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                Intent intent = new Intent(context, WidgetService.class);
                views.setRemoteAdapter(R.id.gridViewWidget, intent);
                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

                final Intent onItemClick = new Intent(context, CollectionWidget.class);
                onItemClick.setAction(ACTION_TOAST);
                onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
                final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0, onItemClick,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                views.setPendingIntentTemplate(R.id.gridViewWidget,
                        onClickPendingIntent);
//-----------------------------------------------------------------
                Intent inte = new Intent(Intent.ACTION_MAIN, null);
                inte.addCategory(Intent.CATEGORY_LAUNCHER);
                // first param is app package name, second is package.class of the main activity
                ComponentName cn = new ComponentName("com.example.android.habittracker", "com.example.android.habittracker.HabitActivity");
                inte.setComponent(cn);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent myPI = PendingIntent.getActivity(context, 0, inte, 0);


            } else {
                views.setRemoteAdapter(0, R.id.gridViewWidget,
                        new Intent(context, WidgetService.class));
            }
            // Instruct the widget manager to update the widget
            pushWidgetUpdate(context, views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, CollectionWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.gridViewWidget);
        }
        if (intent.getAction().equals(ACTION_TOAST)) {
            int ID = intent.getExtras().getInt(EXTRA_STRING);
            int nbr = intent.getExtras().getInt("Count");

                Toast.makeText(context,"your ID "+ID, Toast.LENGTH_LONG).show();
            Datasrc datasrc = new Datasrc(context);
            datasrc.IncrementHabit(context, ID,nbr);

        }
        super.onReceive(context, intent);
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        Intent intent = new Intent();
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, CollectionWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

}
