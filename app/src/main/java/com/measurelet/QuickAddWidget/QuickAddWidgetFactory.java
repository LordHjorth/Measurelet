package com.measurelet.QuickAddWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.hjorth.measurelet.R;

public class QuickAddWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    int appWidgetID;

    public QuickAddWidgetFactory(Context context, Intent intent){
        this.context = context;
        this.appWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        //connect to data source

    }

    @Override
    public void onDataSetChanged() {

        System.out.println("OnDateSetChanged!!!!");

    }

    @Override
    public void onDestroy() {
        //disconnect to data source
    }

    @Override
    public int getCount() {
        return QuickAddWidget.k.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        System.out.println("Get view at: " + position);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

        views.setTextViewText(R.id.widget_itemmaengde, QuickAddWidget.k.get(position).getSize() + " ml");
        views.setTextViewText(R.id.widget_item_label, QuickAddWidget.k.get(position).getType());
        views.setImageViewResource(R.id.widget_item_picture, QuickAddWidget.k.get(position).getThumbnail());

        Intent posIntent = new Intent();
        posIntent.putExtra(QuickAddWidget.EXTRA_ITEM_POSITION, position);
        views.setOnClickFillInIntent(R.id.widget_list_item, posIntent);

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
