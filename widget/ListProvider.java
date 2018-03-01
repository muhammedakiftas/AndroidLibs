package com.sinifdefterim.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.sinifdefterim.R;

import java.util.ArrayList;

import static android.R.style.Widget;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ListProvider implements RemoteViewsFactory {

    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
	private Context context = null;
	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	public ListProvider(Context context, Intent intent) {
		Log.e("LOG","ListProvider");
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		populateListItem();
	}

	private void populateListItem() {
		if(RemoteFetchService.listItemList != null ) {
			listItemList = (ArrayList<ListItem>) RemoteFetchService.listItemList
					.clone();
			Log.e("LOG","NOT null");
            for (int i=0; i<listItemList.size(); i++){
                Log.e("LOG","["+i+"]="+listItemList.get(i));
            }
		} else{
			listItemList = new ArrayList<ListItem>();
			Log.e("LOG","null");
		}
	}

	@Override
	public int getCount() {
		return listItemList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);

		if (!listItemList.isEmpty()) {
			Log.e("LOG","position:"+position);

			ListItem listItem = listItemList.get(position);

			//Log.e("RemoteViews",""+listItem.code);

			remoteView.setTextViewText(R.id.tv_no2, listItem.id);
			remoteView.setTextViewText(R.id.tv_ders_adi2, listItem.name);
			remoteView.setTextViewText(R.id.tv_zaman2, listItem.date);


    /*
            Bundle extras = new Bundle();
            extras.putInt(WidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            // Make it possible to distinguish the individual on-click
            // action of a given item
            remoteView.setOnClickFillInIntent(R.id.list_widget_item, fillInIntent);
    */

			final Intent fillInIntent = new Intent();
			fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
			final Bundle bundle = new Bundle();
			bundle.putString(WidgetProvider.EXTRA_STRING,
					listItem.getName());
			fillInIntent.putExtras(bundle);
			remoteView.setOnClickFillInIntent(R.id.list_widget_item, fillInIntent);

		}

        return remoteView;
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
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
		Log.e("LOG","onCreate");
	}

	@Override
	public void onDataSetChanged() {
		Log.e("LOG","onDataSetChanged");
		populateListItem();
	}

	@Override
	public void onDestroy() {
		Log.e("LOG","onDestroy");
		//listItemList.clear();
	}

}