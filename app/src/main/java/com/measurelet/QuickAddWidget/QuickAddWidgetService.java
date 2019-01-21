package com.measurelet.QuickAddWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class QuickAddWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new QuickAddWidgetFactory(getApplicationContext(),intent);
    }
}
