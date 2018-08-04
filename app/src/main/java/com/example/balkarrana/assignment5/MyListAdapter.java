package com.example.balkarrana.assignment5;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

// my list adapter
public class MyListAdapter extends ArrayAdapter<String>{
    Activity act;
    ArrayList<String> quake;

    // create new constructor
    public MyListAdapter(Activity a, ArrayList<String> item){
        super(a, R.layout.earthquake, item);
        act = a;
        quake = item;
    }

    // get override get view method
    public View getView(int pos, View view, ViewGroup group){

        // System.out.println("Im pos: " + pos);

        // inflate row so we can access the variables
        View row = act.getLayoutInflater().inflate(R.layout.earthquake, null, true);

        // get data from the item passed to us - split by & as we set that when parsing json data
        String[] quakeData = quake.get(pos).split("&");

        // get the two text views - quakeURL is hidden so we needed a 2nd to do that
        TextView quakeRow = row.findViewById(R.id.quakeTitle);
        TextView quakeURL = row.findViewById(R.id.quakeURL);
        String date = new Date(Long.parseLong(quakeData[1])).toString();


        quakeRow.setText(quakeData[0] + "\n" + date);
        quakeURL.setText(quakeData[2]);

        // set background colour when pos of list item can be divided by 2 with 0 remainder to green
        if(pos % 2 == 0){
            quakeRow.setBackgroundColor(Color.parseColor("#00FF00"));
            quakeURL.setBackgroundColor(Color.parseColor("#00FF00"));
        }

        return row;
    }
}
