package com.example.networking;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //private String[] mountainNames = {"Matterhorn","Mont Blanc","Denali"};
    //private String[] mountainLocations = {"Alps","Alps","Alaska"};
    //private int[] mountainHeights ={4478,4808,6190};
    //Create an ArrayList
    //private ArrayList<String> listData=new ArrayList<>(Arrays.asList(mountainNames));
    private ArrayList<Mountains> mountainArrayList=new ArrayList<>();
    private ArrayAdapter<Mountains> adapter;
    private ListView my_list_view;
    //Get Data from JSON

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override

        //här gör vi vår parsing
        protected void onPostExecute(String json) {
            Log.d("TAG", json);
            Gson gson = new Gson();
            Mountains[] mountainss;
            mountainss = gson.fromJson(json,Mountains[].class);
            mountainArrayList.clear();
            for (int i = 0; i < mountainss.length; i++) {
                mountainArrayList.add(mountainss[i]);
                Log.d("Async ==>", "Added: " + mountainss[i]);
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Data from JSON
        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");
        // Add items to your list of mountains by parsing the json data

        //Get a reference to our ListView
        my_list_view = findViewById(R.id.main_list_item_textview);
        //Connect ArrayAdapter to ListView
        adapter=new ArrayAdapter<Mountains>(MainActivity.this,R.layout.list_item_textview,mountainArrayList);
        my_list_view.setAdapter(adapter);
        //Add an item click listener
        my_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), mountainArrayList.get(position).info(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}