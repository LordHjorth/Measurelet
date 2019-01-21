package com.measurelet.QuickAddWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;

import androidx.annotation.NonNull;

/**
 * Implementation of App Widget functionality.
 */
public class QuickAddWidget extends AppWidgetProvider {

    public static final String ACTION_TOAST = "actionToast";
    public static final String EXTRA_ITEM_POSITION = "ITEM_POSITION";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            System.out.println(" - OnUpdate");
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent serviceIntent = new Intent(context, QuickAddWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_add_widget_4col);
        views.setRemoteAdapter(R.id.selection_listview, serviceIntent);


        Intent onClickIntent = new Intent(context, QuickAddWidget.class);
        onClickIntent.setAction(ACTION_TOAST);
        PendingIntent pendingOnClickIntent = PendingIntent.getBroadcast(context, 0, onClickIntent, 0);

        views.setPendingIntentTemplate(R.id.selection_listview, pendingOnClickIntent);


        /*new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {

                while(App.currentUser == null){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Waiting for current user");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                App.patientRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                System.out.println(App.currentUser.getName() + " - update app widget");


            }
        }.execute();
        */

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        System.out.println(" - onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        System.out.println(" - onDisabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_TOAST.equals(intent.getAction())){
            int pos = intent.getIntExtra(EXTRA_ITEM_POSITION, 0);
            System.out.println("The position of the clicked item is: " + pos);
        }


        super.onReceive(context, intent);
    }
}

