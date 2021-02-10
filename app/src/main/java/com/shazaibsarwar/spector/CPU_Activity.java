package com.shazaibsarwar.spector;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class CPU_Activity extends AppCompatActivity {

    ProcessBuilder processBuilder;
    String holder= "";
    String[] DATA = {"/system/bin/cat" , "/proc/cpuinfo"};
    InputStream inputStream;
    Process process;
    byte[] bytesArray;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setTitle("CPU information");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        // ListView
        listView = findViewById(R.id.cpuList);

        // Getting Information of CPU
        bytesArray = new byte[1024];
        try {
            processBuilder = new ProcessBuilder(DATA);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            while (inputStream.read(bytesArray) != -1)
            {
                holder = holder + new String(bytesArray); 
            }
            // Closing Input Stream
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        // Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Collections.singletonList(holder));

        // Setting this Adapter to ListView
        listView.setAdapter(adapter);


    }

    //Back Navigation
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();           // When Back Button is Pressed
        return true;
    }
    // Hiding Search icon for This Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}