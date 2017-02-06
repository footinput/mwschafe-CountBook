package com.example.mwschafe_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

// viewRelative class gathers the data from the relative that was tapped on and outputs the data of that relative

/**
 * The type View relative.
 */
public class viewRelative extends AppCompatActivity {

    /**
     * The Name.
     */
    String name;
    /**
     * The Relative.
     */
    Relative relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_relative);

        Intent in = getIntent();
        name = in.getStringExtra("name");
        final int listPosition = in.getIntExtra("listPosition", 0);
        Gson gson = new Gson();
        relative = gson.fromJson(name, Relative.class);

        Button deleteRelativeButton = (Button) findViewById(R.id.deleteRelativeButton);
        Button editRelativeButton = (Button) findViewById(R.id.editRelative);
        TextView nameF = (TextView) findViewById(R.id.nameNew);
        TextView dateF = (TextView) findViewById(R.id.dateF);
        TextView neckF = (TextView) findViewById(R.id.neckF);
        TextView bustF = (TextView) findViewById(R.id.bustF);
        TextView chestF = (TextView) findViewById(R.id.chestF);
        TextView waistF = (TextView) findViewById(R.id.waistF);
        TextView hipF = (TextView) findViewById(R.id.hipF);
        TextView inseamF = (TextView) findViewById(R.id.inseamF);
        TextView commentF = (TextView) findViewById(R.id.commentF);
        nameF.setText(relative.getName());
        dateF.setText(relative.getDate());
        neckF.setText(relative.getNeck().toString());
        bustF.setText(relative.getBust().toString());
        chestF.setText(relative.getChest().toString());
        waistF.setText(relative.getWaist().toString());
        hipF.setText(relative.getHip().toString());
        inseamF.setText(relative.getInseam().toString());
        commentF.setText(relative.getComment());

        deleteRelativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteRelative = new Intent(viewRelative.this, MainActivity.class);
                deleteRelative.putExtra("removeRelative", relative.getName().toString());
                startActivity(deleteRelative);
                finish();
            }
        });

        editRelativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRelative = new Intent(viewRelative.this, editRelative.class);
                editRelative.putExtra("editRelative", name);
                editRelative.putExtra("listPosition", listPosition);
                startActivity(editRelative);
                finish();
            }
        });


    }
}
