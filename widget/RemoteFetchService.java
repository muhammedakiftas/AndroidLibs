package com.sinifdefterim.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sinifdefterim.network.RestClient;
import com.sinifdefterim.storage.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RemoteFetchService extends Service {

    private static final String TAG = "RmtFtchSrvce.class";
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public static ArrayList<ListItem> listItemList;
    public SaveSharedPreferences prefs;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("LOG", "RemoteFetchService");

        prefs = new SaveSharedPreferences(getApplicationContext());

        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);


		/*if (isOnline(getApplicationContext())){
            getSyllabus();
		} else {
			try {
				parseJSON(new JSONArray(prefs.getSyllabus().toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/
        getSyllabusDB();


        return super.onStartCommand(intent, flags, startId);
    }

    private void getSyllabusDB() {
        SQLiteDatabase db = openOrCreateDatabase("SINIFD", MODE_PRIVATE, null);

        listItemList = new ArrayList<ListItem>();

        String id = "", zaman = "", dersAdi = "", gun = "";

        //prefs.getSelectedDay() bu bize tıklanan butonunun gününü getiriyor, 1'den 7'ye kadar

        Log.e("LOG", "getSyllabus");

        if (listItemList.size() > 0) {
            listItemList.clear();
        }

        try {

            String selectQuery = "Select PlanOzelliklerId,BaslangicSaati,BitisSaati,DersAdi,Gun from seciliplanlar where Gun=" + prefs.getSelectedDay();//duzenlenecek

            Cursor c = db.rawQuery(selectQuery, null);
            Integer i = 1;
            while (c.moveToNext()) {//burasıda gelen database sütunlarına göre ayarlanacak
                id = i.toString();
                zaman = c.getString(1).split(":")[0] + ":" + c.getString(1).split(":")[1] + " - " + c.getString(2).split(":")[0] + ":" + c.getString(2).split(":")[1];
                dersAdi = c.getString(3);
                gun = c.getString(4);

                ListItem listItem = new ListItem();
                listItem.id = id;
                listItem.name = dersAdi;
                listItem.date = zaman;
                listItem.update_date = gun;

                Log.e("LOG", id);
                Log.e("LOG", dersAdi);
                Log.e("LOG", zaman);
                Log.e("LOG", gun);

                Log.e("LOG", "----");

                listItemList.add(listItem);
                i++;
            }
            c.close();


        } catch (Exception e) {
            Log.e("LOG", "" + e.getMessage());
        } finally {
            db.close();
        }

        Log.e("LOG", "loaded");

        populateWidget();//bu method widget'i güncelliyor

    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public void getSyllabus() {
        Log.e("LOG", "sıfır");


        RestClient.get(getApplicationContext(), "/all/latest", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.e(TAG, response.toString());
                try {

                    parseJSON(response);
                } catch (Exception e) {
                    Log.e(TAG, "Parse Control");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", "onFailure");
                try {
                    parseJSON(new JSONArray(prefs.getSyllabus().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseJSON(JSONArray array) throws JSONException {
        Log.e("LOG", "bir" + array.toString());

        listItemList = new ArrayList<ListItem>();
        Toast.makeText(this, "fer", Toast.LENGTH_LONG).show();

        for (int i = 0; i < array.length(); i++) {

            String id = "", name = "", date = "", update_date = "";

            JSONObject object = array.getJSONObject(i);

            Log.e("array[" + i + "] = ", object.getString("id") + "");

            try {
                id = object.getString("id");
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse attrib.");
            }
            try {
                name = object.getString("name");
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse attrib.");
            }
            try {
                date = object.getString("date");
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse attrib.");
            }
            try {
                update_date = object.getString("update_date");
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse attrib.");
            }

            ListItem listItem = new ListItem();
            listItem.id = id;
            listItem.name = name;
            listItem.date = date;
            listItem.update_date = update_date;

            Toast.makeText(this, update_date, Toast.LENGTH_LONG).show();

            listItemList.add(listItem);
        }

        populateWidget();
    }

    private void populateWidget() {
        Log.e("LOG", "populateWidget");
        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(WidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);

        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }
}