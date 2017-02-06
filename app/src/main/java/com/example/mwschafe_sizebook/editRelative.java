package com.example.mwschafe_sizebook;

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

// editRelative class will allow you to edit the data of a relative and rewrite to the file.

/**
 * The type Edit relative.
 */
public class editRelative extends AppCompatActivity {

    /**
     * The Filename.
     */
    String FILENAME = "relatives.txt";
    /**
     * The Relative array list.
     */
    ArrayList<Relative> relativeArrayList = new ArrayList<>();
    /**
     * The Name.
     */
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative);

        openFile();

        final Button saveButton = (Button) findViewById(R.id.addRelativeButton);
        final EditText nameNew = (EditText) findViewById(R.id.nameNew);
        final EditText dateNew = (EditText) findViewById(R.id.date);
        final EditText neckNew = (EditText) findViewById(R.id.neck);
        final EditText bustNew = (EditText) findViewById(R.id.bust);
        final EditText chestNew = (EditText) findViewById(R.id.chest);
        final EditText waistNew = (EditText) findViewById(R.id.waist);
        final EditText hipNew = (EditText) findViewById(R.id.hip);
        final EditText inseamNew = (EditText) findViewById(R.id.inseam);
        final EditText commentNew = (EditText) findViewById(R.id.comment);

        Intent in = getIntent();
        name = in.getStringExtra("editRelative");
        final int listPosition = in.getIntExtra("listPosition", 0);
        Gson gson = new Gson();
        final Relative fixRelative = gson.fromJson(name, Relative.class);

        nameNew.setText(fixRelative.getName());
        dateNew.setText(fixRelative.getDate());
        neckNew.setText(fixRelative.getNeck());
        bustNew.setText(fixRelative.getBust());
        chestNew.setText(fixRelative.getChest());
        waistNew.setText(fixRelative.getWaist());
        hipNew.setText(fixRelative.getHip());
        inseamNew.setText(fixRelative.getInseam());
        commentNew.setText(fixRelative.getComment());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relative newRelative = new Relative();
                newRelative.setName(nameNew.getText().toString());
                newRelative.setDate(dateNew.getText().toString());
                String neck = (neckNew.getText().toString());
                String bust = (bustNew.getText().toString());
                String chest = (chestNew.getText().toString());
                String waist = (waistNew.getText().toString());
                String hip = (hipNew.getText().toString());
                String inseam = (inseamNew.getText().toString());
                newRelative.setComment(commentNew.getText().toString());
                newRelative.setNeck(neck);
                newRelative.setBust(bust);
                newRelative.setChest(chest);
                newRelative.setWaist(waist);
                newRelative.setHip(hip);
                newRelative.setInseam(inseam);

                if (nameNew.getText().toString().isEmpty()) {
                    Toast.makeText(editRelative.this, "Name cannot be left empty!", Toast.LENGTH_SHORT).show();
                }
                try {
                    relativeArrayList.add(listPosition, newRelative);
                    relativeArrayList.remove(listPosition + 1);
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), FILENAME));
                    BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(fos)));
                    Gson gson = new Gson();
                    gson.toJson(relativeArrayList, writer);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Intent in = new Intent(editRelative.this, MainActivity.class);
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
            Type list_type = new TypeToken<ArrayList<Relative>>() {
            }.getType();
            relativeArrayList = gson.fromJson(reader, list_type);
        } catch (FileNotFoundException e) {
            relativeArrayList = new ArrayList<>();
        }
    }
}

