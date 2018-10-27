package com.example.anis.widget_habbit;


import android.content.Intent;
import android.widget.RemoteViewsService;
import java.util.ArrayList;


public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this);

    }



}
