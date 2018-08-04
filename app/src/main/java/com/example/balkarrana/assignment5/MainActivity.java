package com.example.balkarrana.assignment5;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2018-01-01&minmagnitude=6&limit=20&orderby=time";

    ListView listView;

    class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        EarthquakeAsyncTask(){};

        // must be String... to satisfy implementing the abstract method
        public ArrayList<String> doInBackground(String... url) {
            return JSONUtils.getEarthquakeJSONData(url[0]);
        }

        public void onPostExecute(ArrayList<String> postExecuteResult){
            MyListAdapter earthquakeList = new MyListAdapter(MainActivity.this, postExecuteResult);
            listView.setAdapter(earthquakeList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get list view
        listView = findViewById(R.id.quakes);
        listView.setOnItemClickListener(new QuakeListener());

        // create new async task
        new EarthquakeAsyncTask().execute(url);

    }

    class QuakeListener implements AdapterView.OnItemClickListener {
        QuakeListener(){}

        // long is included to make implementation happy
        public void onItemClick(AdapterView<?> adaptView, View view, int i, long l){
            // we cast as TextView so we can access the getText value -> url.
            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((TextView) view.findViewById(R.id.quakeURL)).getText().toString())));
        }
    }
}

