package com.sinifdefterim.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sinifdefterim.R;
import com.sinifdefterim.activity.MainActivity;
import com.sinifdefterim.storage.SaveSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WidgetProvider extends AppWidgetProvider {

	// String to be sent on Broadcast as soon as Data is Fetched
    // should be included on WidgetProvider manifest intent action
	// to be recognized by this WidgetProvider to receive broadcast
    public static final String DATA_FETCHED = "com.sinifdefterimpro.DATA_FETCHED";

    public static final String ACTION_TOAST = "com.sinifdefterimpro.ACTION_TOAST";
    public static final String EXTRA_STRING = "com.sinifdefterimpro.EXTRA_STRING";

    private static final String MyOnClick1 = "myOnClickTag1";
	private static final String MyOnClick2 = "myOnClickTag2";
	private static final String MyOnClick3 = "myOnClickTag3";
	private static final String MyOnClick4 = "myOnClickTag4";
	private static final String MyOnClick5 = "myOnClickTag5";
	private static final String MyOnClick6 = "myOnClickTag6";
	private static final String MyOnClick7 = "myOnClickTag7";
	private static final String MyOnClick8 = "myOnClickTag8";

	private int APPWIDGETID = 0;

	SaveSharedPreferences prefs;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		prefs = new SaveSharedPreferences(context);

		Log.e("LOG","WP onUpdate");
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			Log.e("LOG","APPWIDGETID["+i+"]="+appWidgetIds[i]);
			APPWIDGETID = appWidgetIds[i];

			/*Intent serviceIntent = new Intent(context, RemoteFetchService.class);
			serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			context.startService(serviceIntent);


			remoteViews.setViewVisibility(R.id.widget_update, View.INVISIBLE);
			remoteViews.setViewVisibility(R.id.progressBar2, View.VISIBLE);
*/
			//RemoteViews remoteViews = updateWidgetListView(context, APPWIDGETID);

            //RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            RemoteViews remoteViews = updateWidgetListView(context, APPWIDGETID);

			remoteViews.setOnClickPendingIntent(R.id.d1, getPendingSelfIntent(context, MyOnClick1));
			remoteViews.setOnClickPendingIntent(R.id.d2, getPendingSelfIntent(context, MyOnClick2));
			remoteViews.setOnClickPendingIntent(R.id.d3, getPendingSelfIntent(context, MyOnClick3));
			remoteViews.setOnClickPendingIntent(R.id.d4, getPendingSelfIntent(context, MyOnClick4));
			remoteViews.setOnClickPendingIntent(R.id.d5, getPendingSelfIntent(context, MyOnClick5));
			remoteViews.setOnClickPendingIntent(R.id.d6, getPendingSelfIntent(context, MyOnClick6));
			remoteViews.setOnClickPendingIntent(R.id.d7, getPendingSelfIntent(context, MyOnClick7));
			//remoteViews.setOnClickPendingIntent(R.id.widget_update, getPendingSelfIntent(context, MyOnClick8));


            final Intent onItemClick = new Intent(context, WidgetProvider.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listViewWidget,
                    onClickPendingIntent);



            appWidgetManager.updateAppWidget(APPWIDGETID, remoteViews);

			Calendar calendar = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_WEEK);

			switch (day) {
				case Calendar.MONDAY:{
                    prefs.setSelectedDay(1);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_orange),context.getString(R.string.d1));
					break;
				}
				case Calendar.TUESDAY:{
                    prefs.setSelectedDay(2);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_peter_river),context.getString(R.string.d2));
					break;
				}
				case Calendar.WEDNESDAY:{
                    prefs.setSelectedDay(3);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_pumpkin),context.getString(R.string.d3));
					break;
				}
				case Calendar.THURSDAY:{
                    prefs.setSelectedDay(4);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_sun_flower),context.getString(R.string.d4));
					break;
				}
				case Calendar.FRIDAY:{
                    prefs.setSelectedDay(5);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_wisteria),context.getString(R.string.d5));
					break;
				}
				case Calendar.SATURDAY:{
                    prefs.setSelectedDay(6);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_concrete),context.getString(R.string.d6));
					break;
				}
				case Calendar.SUNDAY:{
                    prefs.setSelectedDay(7);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_clouds),context.getString(R.string.d7));
					break;
				}default:
                    prefs.setSelectedDay(1);
					changeColor(context, APPWIDGETID, context.getResources().getColor(R.color.flatui_orange),context.getString(R.string.d1));
			}

		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, WidgetProvider.class);
		intent.setAction(action);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, APPWIDGETID);
		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
		// which layout to show on widget
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);

		//context.stopService(new Intent(context, WidgetService.class));
		//context.stopService(new Intent(context, ListProvider.class));

		// RemoteViews Service needed to provide adapter for ListView
		Intent svcIntent = new Intent(context, WidgetService.class);
		// passing app widget id to that RemoteViews Service
		svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		// setting a unique Uri to the intent
		// don't know its purpose to me right now
		svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
		// setting adapter to listview of the widget
		remoteViews.setRemoteAdapter(R.id.listViewWidget,svcIntent);
		//setRemoteAdapter(appWidgetId, R.id.listViewWidget,svcIntent);

		// setting an empty view in case of no data
		remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
		return remoteViews;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("LOG","WP onReceive");

		prefs = new SaveSharedPreferences(context);

		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

        if (intent.getAction().equals(ACTION_TOAST)) {
            String itemId = intent.getExtras().getString(EXTRA_STRING);
            //Toast.makeText(context, item, Toast.LENGTH_LONG).show();

            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("clickDersId",itemId);
			i.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        super.onReceive(context, intent);

        if (intent.getAction().equals(DATA_FETCHED)) {
			Log.e("LOG","WP DATA_FETCHED");

			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

			//RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);

			int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);
            //appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
/*
			remoteViews.setViewVisibility(R.id.progressBar2, View.INVISIBLE);
			remoteViews.setViewVisibility(R.id.widget_update, View.VISIBLE);

			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM HH:mm");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			remoteViews.setTextViewText(R.id.tv_current, "Güncel "+dateString);
*/

		}else if (intent.getAction().equals(MyOnClick1)) {
			prefs.setSelectedDay(1);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_orange2),context.getString(R.string.d1));
		}else if (intent.getAction().equals(MyOnClick2)) {
			prefs.setSelectedDay(2);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_peter_river2),context.getString(R.string.d2));
		}else if (intent.getAction().equals(MyOnClick3)) {
			prefs.setSelectedDay(3);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_pumpkin2),context.getString(R.string.d3));
		}else if (intent.getAction().equals(MyOnClick4)) {
			prefs.setSelectedDay(4);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_sun_flower2),context.getString(R.string.d4));
		}else if (intent.getAction().equals(MyOnClick5)) {
			prefs.setSelectedDay(5);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_wisteria2),context.getString(R.string.d5));
		}else if (intent.getAction().equals(MyOnClick6)) {
			prefs.setSelectedDay(6);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_concrete2),context.getString(R.string.d6));
		}else if (intent.getAction().equals(MyOnClick7)) {
			prefs.setSelectedDay(7);
			changeColor(context, appWidgetId, context.getResources().getColor(R.color.flatui_clouds2),context.getString(R.string.d7));
		}else if (intent.getAction().equals(MyOnClick8)) {
			prefs.setSelectedDay(8);

/*			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

			RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);

			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM HH:mm");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			remoteViews.setTextViewText(R.id.tv_current, "Güncel "+dateString);

			remoteViews.setViewVisibility(R.id.widget_update, View.INVISIBLE);
			remoteViews.setViewVisibility(R.id.progressBar2, View.VISIBLE);

 			//remoteViews.setProgressBar(R.id.progressBar2, 100, 50, false);

			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
*/

			Intent serviceIntent = new Intent(context, RemoteFetchService.class);
			serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			context.startService(serviceIntent);
		}

	}

	public void changeColor(Context context, int appWidgetId, int c, String day){
		//Log.e("LOG", "WP I:"+appWidgetId);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

		RemoteViews remoteViews;

		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		remoteViews.setInt(R.id.main_widget,"setBackgroundColor", c);
		remoteViews.setTextViewText(R.id.tv_day, ""+day);
		/*
		remoteViews.setViewVisibility(R.id.widget_update, View.INVISIBLE);
		remoteViews.setViewVisibility(R.id.progressBar2, View.VISIBLE);
		*/
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

		Intent serviceIntent = new Intent(context, RemoteFetchService.class);
		serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		context.startService(serviceIntent);
	}

}