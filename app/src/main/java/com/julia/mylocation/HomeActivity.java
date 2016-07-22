package com.julia.mylocation;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.app.ProgressDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.julia.mylocation.JSONParser;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {
    private LocationService service;
    JSONParser jsonParser = new JSONParser();

    public static String url_send_mes = "http://trackgps.oblgaz.com/app/send_mess.php";
    public static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        service = new LocationService(this);  // запускаем Location service
        Location location =  new LocationService(getApplicationContext()).getLocation();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CheckBox checkBox = (CheckBox) findViewById(R.id.enable_service);
        greet(checkBox.isChecked());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BroadcastLocationService.enabled = isChecked;  // запуск фоновой службы
                greet(isChecked);
                if (isChecked) {
                    service.getLocation();

                } else {
                    service.cancelUpdates();
                }

            }
        });
    }

    public void greet(boolean isChecked)
    {


        if (isChecked) {
            String welcomeText = "Сервис обнаружения локации запущен.";
            Toast.makeText(this, welcomeText, Toast.LENGTH_LONG).show();


        }
        else
        {
            Toast.makeText(this, "Служба остановлена. Сообщения отправляться не будут", Toast.LENGTH_LONG).show();
        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // меню
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            // New activity for numbers.
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        /*else if(id == R.id.action_edit_numbers) {
            // New activity for numbers.
            Intent intent = new Intent(this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_log) {
            // New activity for numbers.
            Intent intent = new Intent(this, LogActivity.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


}
