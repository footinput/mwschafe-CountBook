package com.example.mwschafe_sizebook;

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
     * The Filename. text file stores all the data of the relatives
     */
    String FILENAME = "relatives.txt";
    /**
     * The Relative array list.
     */
    ArrayList<Relative> relativeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sizes);


        ListView relativesList = (ListView) findViewById(R.id.listView);
        TextView numberOfRelatives = (TextView) findViewById(R.id.numberOfRelatives);
        Button newRelative = (Button) findViewById(R.id.newRelative);

        openFile();
        deleteRelative();


        numberOfRelatives.setText(Integer.toString(relativeArrayList.size()));


        ArrayList<String> relativeNames = new ArrayList<>();

        //Loop gathers all the names of each relative in the list.
        for (int i = 0; i < relativeArrayList.size(); i++) {
            String placeName = relativeArrayList.get(i).getName();
            relativeNames.add(placeName);
            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, relativeNames);
            relativesList.setAdapter(nameAdapter);
        }


        final Intent viewRelativeSizes = new Intent(MainActivity.this, viewRelative.class);

        //opens a relative when it is tapped on the phone
        relativesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                String name = gson.toJson(relativeArrayList.get(position));
                viewRelativeSizes.putExtra("name", name);
                viewRelativeSizes.putExtra("listPosition", position);
                startActivity(viewRelativeSizes);
                finish();
            }
        });

        //button for creating a new relative
        newRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRelative = new Intent(MainActivity.this, com.example.mwschafe_sizebook.addRelative.class);
                startActivity(addRelative);
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
            Type list_type = new TypeToken<ArrayList<Relative>>() {
            }.getType();
            relativeArrayList = gson.fromJson(reader, list_type);
        } catch (FileNotFoundException e) {
            relativeArrayList = new ArrayList<>();
        }
    }

    /**
     * Delete relative.
     */
    void deleteRelative() {
        Intent in = getIntent();
        String relativeDelete = in.getStringExtra("removeRelative");
        if (relativeDelete != null) {
            for (Relative relative : relativeArrayList) {
                if (relative.getName().equals(relativeDelete)) {
                    relativeArrayList.remove(relative);
                    try {
                        FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                        Gson gson = new Gson();
                        gson.toJson(relativeArrayList, writer);
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

