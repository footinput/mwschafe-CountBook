package com.example.mwschafe_CountBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

// editCounter class will allow you to edit the data of a counter and rewrite to the file.

/**
 * The type Edit counter.
 */
public class editCounter extends AppCompatActivity {

    /**
     * The Filename.
     */
    private String FILENAME = "counters.txt";
    /**
     * The Counter array list.
     */
    private ArrayList<Counter> counterArrayList = new ArrayList<>();
    /**
     * The Name.
     */
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        openFile();

        final Button saveButton = (Button) findViewById(R.id.addCounterButton);
        final EditText nameNew = (EditText) findViewById(R.id.nameNew);
        final EditText dateNew = (EditText) findViewById(R.id.date);
        final EditText currentValueNew = (EditText) findViewById(R.id.currentValue);
        final EditText initialValueNew = (EditText) findViewById(R.id.initialValue);
        final EditText commentNew = (EditText) findViewById(R.id.comment);

        Intent in = getIntent();
        name = in.getStringExtra("editCounter");
        final int listPosition = in.getIntExtra("listPosition", 0);
        Gson gson = new Gson();
        final Counter fixCounter = gson.fromJson(name, Counter.class);

        nameNew.setText(fixCounter.getName());
        dateNew.setText(fixCounter.getDate());
        currentValueNew.setText(String.valueOf(fixCounter.getCurrentValue()));
        initialValueNew.setText(String.valueOf(fixCounter.getInitialValue()));
        commentNew.setText(fixCounter.getComment());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Counter newCounter = new Counter();
                newCounter.setName(nameNew.getText().toString());
                newCounter.setDate(dateNew.getText().toString());
                int currentValue = Integer.parseInt(currentValueNew.getText().toString());
                int initialValue = Integer.parseInt(initialValueNew.getText().toString());

                newCounter.setComment(commentNew.getText().toString());
                newCounter.setCurrentValue(currentValue);
                newCounter.setInitialValue(initialValue);

                if (nameNew.getText().toString().isEmpty()) {
                    Toast.makeText(editCounter.this, "Name cannot be left empty!", Toast.LENGTH_SHORT).show();
                }
                try {
                    counterArrayList.add(listPosition, newCounter);
                    counterArrayList.remove(listPosition + 1);
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
                } finally {
                    Intent in = new Intent(editCounter.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }

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

