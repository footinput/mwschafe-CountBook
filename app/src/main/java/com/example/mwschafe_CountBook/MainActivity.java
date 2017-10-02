package com.example.mwschafe_CountBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Filename. text file stores all the data of the counters
     */
    private String FILENAME = "counters.txt";
    /**
     * The Counter array list.
     */
    private ArrayList<Counter> counterArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counters);


        ListView counterList = (ListView) findViewById(R.id.listView);
        TextView numberOfCounters = (TextView) findViewById(R.id.numberOfCounters);
        Button newCounter = (Button) findViewById(R.id.newCounter);

        openFile();
        deleteCounter();


        numberOfCounters.setText(Integer.toString(counterArrayList.size()));


        ArrayList<String> counterNames = new ArrayList<>();

        //Loop gathers all the names of each counter in the list.
        for (int i = 0; i < counterArrayList.size(); i++) {
            String placeName = counterArrayList.get(i).getName();
            counterNames.add(placeName);
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, counterNames);
            counterList.setAdapter(nameAdapter);
        }


        final Intent viewCounter = new Intent(MainActivity.this, viewCounter.class);

        //opens a counter when it is tapped on the phone
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                String name = gson.toJson(counterArrayList.get(position));
                viewCounter.putExtra("name", name);
                viewCounter.putExtra("listPosition", position);
                startActivity(viewCounter);
                finish();
            }
        });

        //button for creating a new counter
        newCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCounter = new Intent(MainActivity.this, addCounter.class);
                startActivity(addCounter);
            }
        });


    }


    /**
     * Open file.
     */
    void openFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type list_type = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            counterArrayList = gson.fromJson(reader, list_type);
        } catch (FileNotFoundException e) {
            counterArrayList = new ArrayList<>();
        }
    }

    /**
     * Delete counter.
     */
    void deleteCounter() {
        Intent in = getIntent();
        String counterDelete = in.getStringExtra("removeCounter");
        if (counterDelete != null) {
            for (Counter counter : counterArrayList) {
                if (counter.getName().equals(counterDelete)) {
                    counterArrayList.remove(counter);
                    try {
                        FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                        Gson gson = new Gson();
                        gson.toJson(counterArrayList, writer);
                        writer.flush();
                        writer.close();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

    }


}

