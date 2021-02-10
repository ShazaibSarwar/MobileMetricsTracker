package com.shazaibsarwar.spector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayAvtivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_avtivity);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setTitle("Display information");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Screen Size in pixels , Width x Height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String resolution  = width+ " x "+ height + " pixel";


        // Physical Size in inch
        double x = Math.pow(width/dm.xdpi , 2);
        double y = Math.pow(height/dm.ydpi, 2);
        double screenInches = Math.sqrt(x+y);   // this will return a floating Value
        NumberFormat form = NumberFormat.getNumberInstance();   // limit number of values after fraction
        form.setMinimumFractionDigits(2);
        form.setMaximumFractionDigits(2);
        String screenInchesOutput = form.format(screenInches);

        // Refresh Rate
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);

        String outputRefreshRate = format.format(refreshRating);


        titles = new String[]{"Resolution" , "Density" , "Physical Size" , "Refresh Rate" , "Orientation" };
        descriptions = new String[]{resolution , DisplayMetrics.DENSITY_XHIGH+"dpi (xhdpi)" , screenInchesOutput+" Hz" ,outputRefreshRate, this.getResources().getConfiguration().orientation+"" };

        // this.getResources().getConfiguration().orientation will return 1 or 2 means Portrait / Landscape



        ListView listView = findViewById(R.id.displayList);
        MyAdapter myAdapter = new MyAdapter(this , titles , descriptions);
          listView.setAdapter(myAdapter);

    }

    // Custom Adapter
    public class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String myTitles[];
        String myDesCription[];

        MyAdapter(Context context , String[] titles , String[] descriptions)
        {
            super(context , R.layout.two_row, R.id.title , titles);
            this.context = context;
            this.myTitles = titles;
            this.myDesCription = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.two_row, parent,false);

            // Views in two_row.xml
            TextView myTitle       = row.findViewById(R.id.titleTV);
            TextView myDescription =  row.findViewById(R.id.descTV);

            // Setting Text to View
            myTitle.setText(titles[position]);
            myDescription.setText(descriptions[position]);

            return row;
        }
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