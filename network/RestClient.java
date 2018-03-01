package com.sinifdefterimpro.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sinifdefterimpro.storage.SaveSharedPreferences;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RestClient {

    private static final String BASE_URL = "http://134.255.199.150:90/service/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void addHeader(String header, String value) {
        client.addHeader(header, value);
    }

    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (params == null) {
            params = new RequestParams();
        }
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, "application/json",
                responseHandler);
    }

    public static void getIp(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.get(context, url, entity, "application/json", responseHandler);
    }

    public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    public static void put(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        fetchUserCredentials(context);

        if (params == null) {
            params = new RequestParams();
        }
        addHeader("Token", TOKEN);
        Log.e("TOKEN =", "" + TOKEN);

        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String TOKEN = "";
    private static void fetchUserCredentials(Context ctx) {
        SaveSharedPreferences prefs = new SaveSharedPreferences(ctx);
        if (prefs.getUserToken() != null) {
            try {
                TOKEN = "Token " + prefs.getUserToken();
            } catch (Exception e) {
                TOKEN = "";
            }
        }
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
