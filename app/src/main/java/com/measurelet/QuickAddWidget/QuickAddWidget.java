package com.measurelet.QuickAddWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.example.hjorth.measurelet.R;
import com.measurelet.App;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Model.Intake;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class QuickAddWidget extends AppWidgetProvider {

    public static final String ACTION_TOAST = "actionToast";
    public static final String ACTION_REFRESH = "actionRefresh";
    public static final String EXTRA_ITEM_POSITION = "ITEM_POSITION";
    public static List<Intake> k;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            System.out.println(" - OnUpdate");
        }
    }


    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent refreshIntent = new Intent(context, QuickAddWidget.class);
        refreshIntent.setAction(ACTION_REFRESH);
        PendingIntent pendingRefreshIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, 0);


        Intent serviceIntent = new Intent(context, QuickAddWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_add_widget_4col);
        views.setRemoteAdapter(R.id.widget_selection_listview, serviceIntent);
        views.setOnClickPendingIntent(R.id.widget_refresh_btn, pendingRefreshIntent);

        //Creates broadcast to listen for a button click on the list view.
        Intent onClickGetPosition = new Intent(context, QuickAddWidget.class);
        onClickGetPosition.setAction(ACTION_TOAST);
        PendingIntent pendingonClickGetPosition = PendingIntent.getBroadcast(context, 1, onClickGetPosition, 0);
        views.setPendingIntentTemplate(R.id.widget_selection_listview, pendingonClickGetPosition);

        //retrieves the intakes for the widget
        new WidgetButtonAsyncTask().execute();

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
        if (ACTION_TOAST.equals(intent.getAction())) {
            int pos = intent.getIntExtra(EXTRA_ITEM_POSITION, 0);
            System.out.println("The position of the clicked item is: " + pos);
            //Swaps the order of the buttons
            Intake temp = k.get(pos);
            k.remove(pos);
            k.add(0, temp);

            IntakeFactory.InsertNewIntake(temp);
        }
        if (ACTION_REFRESH.equals(intent.getAction())) {
            System.out.println("The refresh button was clicked!");
            new WidgetButtonAsyncTask().execute();
        }

        super.onReceive(context, intent);
    }

    public static void UpdateButtons(ArrayList<Intake> registrations) {

        ArrayList<Intake> result = IntakeFactory.getIntakesListWithDefaults(registrations);

        k = result.subList(0, 4);
    }



    private static class WidgetButtonAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            while (App.currentUser == null) {
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

            QuickAddWidget.UpdateButtons(App.currentUser.getRegistrations());

            System.out.println(k.size() + " - Buttons created!");
        }
    }
}

