package com.example.mwschafe_CountBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

// viewCounter class gathers the data from the counter that was tapped on and outputs the data of that counter

/**
 * The type View counter.
 */
public class viewCounter extends AppCompatActivity {

    /**
     * The Name.
     */
    private String name;
    /**
     * The Counter.
     */
    private Counter counter;

    /**
     * The Filename.
     */
    private String FILENAME = "counters.txt";

    /**
     * The Counter array list.
     */
    private ArrayList<Counter> counterArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counter);

        openFile();

        Intent in = getIntent();
        name = in.getStringExtra("name");
        final int listPosition = in.getIntExtra("listPosition", 0);
        Gson gson = new Gson();
        counter = gson.fromJson(name, Counter.class);

        Button deleteCounterButton = (Button) findViewById(R.id.deleteCounterButton);
        Button editCounterButton = (Button) findViewById(R.id.editCounter);
        Button backButton = (Button) findViewById(R.id.backButton);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button subtractButton = (Button) findViewById(R.id.subtractButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        final TextView nameF = (TextView) findViewById(R.id.nameNew);
        final TextView dateF = (TextView) findViewById(R.id.dateF);
        final TextView currentValueF = (TextView) findViewById(R.id.CurrentValue);
        final TextView initialValueF = (TextView) findViewById(R.id.InitialValue);
        final TextView commentF = (TextView) findViewById(R.id.commentF);
        nameF.setText(counter.getName());
        dateF.setText(counter.getDate());
        currentValueF.setText(String.valueOf(counter.getCurrentValue()));
        initialValueF.setText(String.valueOf(counter.getInitialValue()));
        commentF.setText(counter.getComment());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.addCurrentValue();
                try {
                    counterArrayList.set(listPosition,counter);
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                    BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(fos)));
                    Gson gson = new Gson();
                    gson.toJson(counterArrayList, writer);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentValueF.setText(String.valueOf(counter.getCurrentValue()));
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.subCurrentValue();
                try {
                    counterArrayList.set(listPosition,counter);
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                    BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(fos)));
                    Gson gson = new Gson();
                    gson.toJson(counterArrayList, writer);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentValueF.setText(String.valueOf(counter.getCurrentValue()));
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setCurrentValue(counter.getInitialValue());
                try {
                    counterArrayList.set(listPosition,counter);
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                    BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(fos)));
                    Gson gson = new Gson();
                    gson.toJson(counterArrayList, writer);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentValueF.setText(String.valueOf(counter.getCurrentValue()));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(viewCounter.this, MainActivity.class);
                startActivity(back);
            }
        });

        deleteCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteRelative = new Intent(viewCounter.this, MainActivity.class);
                deleteRelative.putExtra("removeCounter", counter.getName().toString());
                startActivity(deleteRelative);
                finish();
            }
        });

        editCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRelative = new Intent(viewCounter.this, editCounter.class);
                editRelative.putExtra("editCounter", name);
                editRelative.putExtra("listPosition", listPosition);
                startActivity(editRelative);
                finish();
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
}
