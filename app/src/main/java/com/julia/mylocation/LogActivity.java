package com.julia.mylocation;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends ListActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        FileInputStream fin;
        TextView textView = (TextView) findViewById(R.id.open_text);
        try {
            fin = openFileInput("primer.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            textView.setText(text);
        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}

