package com.measurelet.QuickAddWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.measurelet.Registration_standard_frag;
import com.measurelet.VaeskeKnap;

import java.util.ArrayList;
import java.util.List;

public class QuickAddWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    int appWidgetID;
    List<VaeskeKnap> options;

    public QuickAddWidgetFactory(Context context, Intent intent){
        this.context = context;
        this.appWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        //this.options = options;

        options = new ArrayList<>();
        options.add(0, new VaeskeKnap("Juice", 125, R.drawable.ic_orange_juice));
        options.add(1, new VaeskeKnap("Vand", 250, R.drawable.ic_glass_of_water));
        options.add(2, new VaeskeKnap("Kaffe", 250, R.drawable.ic_coffee_cup));
        options.add(3, new VaeskeKnap("Sodavand", 500, R.drawable.ic_soda));

        for(VaeskeKnap vk : options){
            System.out.println("Option: " + vk.getType());
        }
    }

    @Override
    public void onCreate() {
        //connect to data source

    }

    @Override
    public void onDataSetChanged() {


    }

    @Override
    public void onDestroy() {
        //disconnect to data source
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        System.out.println("Get view at: " + position);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

        views.setTextViewText(R.id.widget_itemmaengde, options.get(position).getMængde() + " ml");
        views.setTextViewText(R.id.widget_item_label, options.get(position).getType());
        views.setImageViewResource(R.id.widget_item_picture, options.get(position).getThumbnail());

        Intent fillIntent = new Intent();
        fillIntent.putExtra(QuickAddWidget.EXTRA_ITEM_POSITION, position);
        views.setOnClickFillInIntent(R.id.widget_list_item, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
