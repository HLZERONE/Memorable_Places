package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> lats = new ArrayList<>();
        ArrayList<String> lons = new ArrayList<>();
        try{
            Log.i("MainActivity", "run");
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            lats = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            lons = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(places.size() == lats.size() && places.size() == lons.size() && places.size() != 0){
            Log.i("MainActivity", "size equal");
            for(int i=0; i<places.size(); i++){
                locations.add(new LatLng(Double.parseDouble(lats.get(i)), Double.parseDouble(lons.get(i))));
            }
        }
        else{
            places.add("Add a new place...");
            locations.add(new LatLng(0,0));
        }


        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("NumItem", i);
                startActivity(intent);
            }
        });
    }
}