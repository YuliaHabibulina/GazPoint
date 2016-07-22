package com.julia.mylocation;


import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.SystemClock;
import android.widget.GridView;
import android.widget.TextView;
import android.telephony.TelephonyManager;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import org.apache.http.*;

import android.util.Log;
import android.widget.Toast;

import com.julia.mylocation.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.TimeZone;

public class BroadcastLocationService extends IntentService {
    public static boolean enabled = false;

    JSONParser jsonParser = new JSONParser();
    public static String url_create_product = "http://trackgps.oblgaz.com/app/create_product.php";

    private static final String TAG_SUCCESS = "success";

    public BroadcastLocationService() {
        super("BroadcastLocationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendMessage(getApplicationContext(), new LocationService(getApplicationContext()).getLocation());
    }

    private void sendMessage(Context mContext, Location location) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String devIMEI = telephonyManager.getDeviceId();

        location = new LocationService(getApplicationContext()).getLocation();
        String lat = Double.toString(location.getLatitude());
        String lon = Double.toString(location.getLongitude());
        String speed=Double.toString(location.getSpeed());
        String curs=Double.toString(location.getBearing());

        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat curTime = new SimpleDateFormat(format);
        curTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String mytime = curTime.format(new Date());

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("devIMEI",devIMEI));
        params.add(new BasicNameValuePair("lon", lon));
        params.add(new BasicNameValuePair("lat", lat));
        params.add(new BasicNameValuePair("speed", speed));
        params.add(new BasicNameValuePair("curs", curs));
        params.add(new BasicNameValuePair("mytime", mytime));
        //
        JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

        Log.d("Create Response", json.toString());

        try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        SystemClock.sleep(60000);
    }
}
