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

    private String[] mountainNames = {"Matterhorn","Mont Blanc","Denali"};
    private String[] mountainLocations = {"Alps","Alps","Alaska"};
    private int[] mountainHeights ={4478,4808,6190};
    //Create an ArrayList
    private ArrayList<String> listData=new ArrayList<>(Arrays.asList(mountainNames));
    private ArrayList<Mountain> mountainArrayList=new ArrayList<>();

    //Get Data from JSON
    private Mountains[] mountains;
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
            mountains = gson.fromJson(json,Mountains[].class);

//            for (int i = 0; i < mountains.length; i++) {
//                mountainArrayList.add(new Mountain(mountains[i].getName(), mountains[i].getLocation(), mountains[i].getMeters()));
//            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Data from JSON

        // >-- Nedanstående är utkommenterat --<
        // >-- Nedanstående är utkommenterat --<
        // >-- Nedanstående är utkommenterat --<

        // new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");
        // Add items to your list of mountains by parsing the json data
        // Hint: use `adapter.notifyDataSetChanged();`
        // from within `onPostExecute(String json)`
        // to notify the adapter that the content of the ArrayList has been updated.

        //Create an ArrayAdapter

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listData);

        mountainArrayList.add(new Mountain("Matterhorn","Alps",4478));
        mountainArrayList.add(new Mountain("Mont Blanc","Alps",4808));
        mountainArrayList.add(new Mountain("Denali","Alaska",6190));

        //Add JSONdata to ArrayAdapter



        ArrayAdapter<Mountain> adapter=new ArrayAdapter<Mountain>(this,R.layout.list_item_textview,R.id.list_item_textview,mountainArrayList);

        //Get a reference to our ListView
        final ListView my_list_view = findViewById(R.id.list_item_textview);
        //Connect ArrayAdapter to ListView
        my_list_view.setAdapter(arrayAdapter);
        //Add an item click listener
        my_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String clickedItem=(String) my_list_view.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this,clickedItem,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), mountainArrayList.get(position).info(), Toast.LENGTH_SHORT).show();
            }
        });

//        try{
//            InputStream is = getApplicationContext().getAssets().open("mountains.json");
//            String s = convertStreamToString(is);
//        }

    }
}