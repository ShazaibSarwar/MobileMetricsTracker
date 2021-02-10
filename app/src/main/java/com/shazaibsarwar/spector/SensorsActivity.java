package com.shazaibsarwar.spector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.hardware.Sensor;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setTitle("Sensors information");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        // ListView
        listView = findViewById(R.id.sensorsList);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Setting Adapter to ListView
        listView.setAdapter(new MySensorAdapter(this,R.layout.sensors_custom_row,sensors));

        //Displaying Total Number of Sensors in device
        String total = listView.getCount()+"";
        TextView totalSensors = findViewById(R.id.countSensors);
        totalSensors.setText("Total Sensor in this Device are : " + total);
        Toast.makeText(this, total+" : Sensors Detected", Toast.LENGTH_SHORT).show();
    }

    // I write Manually Few Sensors in it
    public static  String sensorTypeToString(int sensorType) {
        switch (sensorType) {


            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_TEMPERATURE:
                return "Temperature";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geometric Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Typing Gravity";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_HEART_BEAT:
                return "Heart Moniter";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Orientation";
            case Sensor.TYPE_PRESSURE:
                return "Pressure";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            case Sensor.TYPE_MOTION_DETECT:
                return "Motion Detect";
            default:
                return "Unknown";



        }
    }

    // Creating Custom Sensor Adapter
    private static class MySensorAdapter extends ArrayAdapter<Sensor>{

        private int textViewRecourceID;

        private static class ViewHolder{
            TextView itemView;
        }

        // Constructor
        MySensorAdapter(Context context, int textViewRecourceID, List<Sensor> items)
        {
            super(context,textViewRecourceID,items);
            this.textViewRecourceID = textViewRecourceID;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView  = LayoutInflater.from(this.getContext()).inflate(textViewRecourceID,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.itemView = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            Sensor items = getItem(position);


            // Getting All Sensors List
            if (items != null )
            {
                viewHolder.itemView.setText(
                        "Name          : " + items.getName()
                     +"\nInt Type             : "+ items.getType()
                     +"\nString Type        : "+ sensorTypeToString(items.getType())
                     +"\nVendor               : "+ items.getVendor()
                     +"\nResolution         : "+items.getResolution()
                     +"\nPower                : "+items.getPower()+" mAh"
                     +"\nMaximum Range   : "+ items.getMaximumRange());
            }


            return convertView;
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