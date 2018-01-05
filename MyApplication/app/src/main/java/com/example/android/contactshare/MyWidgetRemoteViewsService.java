package com.example.android.contactshare;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by ROHAN on 03-01-2018.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private ArrayList<CustomClassForStudent> mydata;
        private Context context;
        private int mAppWidgetId;
        StudentMainActivity obj = new StudentMainActivity();

        public MyWidgetRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            mydata = new ArrayList<>();
            if (obj.myUpdatedList != null)
                for (int i = 0; i < obj.myUpdatedList.size(); i++) {
                    mydata.add(new CustomClassForStudent(obj.myUpdatedList.get(i).getRoll(), obj.myUpdatedList.get(i).getName(), obj.myUpdatedList.get(i).getBool()));
                }
            //mydata.add(new CustomClassForStudent(obj.abc[0]));
            //mydata.add(new CustomDataType(obj.abc[1]));
            //mydata.add(new CustomDataType(obj.array.get(1).getresname()));


        }

        @Override
        public void onCreate() {
            int a=mydata.size();
            for (int i = 0; i < a; i++) {
                mydata.add(new CustomClassForStudent(i));
            }
        }

        @Override
        public void onDataSetChanged() {


        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mydata != null)
                return mydata.size();
            else
                return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widjet_list);
            rv.setTextViewText(R.id.widgetRoll, mydata.get(position).getRoll());
            rv.setTextViewText(R.id.widgetName, mydata.get(position).getName());
            rv.setTextViewText(R.id.widgetboolean, mydata.get(position).getBool());
            // rv.setTextViewText(R.id.widgetItemTaskNameLabel,obj.array.get(position).getresname());
            return rv;
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
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
