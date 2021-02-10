package com.shazaibsarwar.spector;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    // View Here
    TextView textView1, textView2, batteryPercentage, mTextViewPercentage;

    double batterCapacity;
    ProgressBar mProgressBar;
    int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Battery information");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Getting  Reference from XML
        batteryPercentage = findViewById(R.id.battery_percentage);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mProgressBar = findViewById(R.id.pb);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";


        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            batterCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile, "battery.capacity");

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Get Application Context
        android.content.Context context = getApplicationContext();
        // Intializing a new IntentFilter
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        // Register the BroadCast Receiver
        context.registerReceiver(mBroadCastReceiver, iFilter);


    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "", battery_condition = "", power_source = "Unplugged";

            // Getting Battery Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            // Getting Battery Condition
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (health == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                battery_condition = "Dead";
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_condition = "Good";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_condition = "Over Heat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_condition = "Over Voltage";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                battery_condition = "Unspecified Failure to Determine";
            }






            // Get Batter temperature in celcius
            int temperature_c = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            //  Getting Temperature in Fahrenheit
            int temperature_f = (int) (temperature_c * 1.8 + 32);







            //Getting Battery Power Source
            int chargingPlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (chargingPlug == BatteryManager.BATTERY_PLUGGED_USB) {
                power_source = "USB";
            }
            if (chargingPlug == BatteryManager.BATTERY_PLUGGED_AC) {
                power_source = "AC Adapter";
            }
            if (chargingPlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                power_source = "WireLess";
            }





            // Getting Status of Battery for Example Charging or not
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                charging_status = "Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charging_status = "Discharging";
            }
            if (status == BatteryManager.BATTERY_STATUS_FULL) {
                charging_status = "Battery is Full";
            }
            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                charging_status = "Not Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                charging_status = "Unknown";
            }


            // Getting Battery Technology
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            // Getting Battery Voltage
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);


            // Display all Battery Status
            batteryPercentage.setText("Battery Percentage : " + level + "%");
            textView1.setText("Condition             :\n" +
                    "Temperature       :\n" +
                    "Power Source     :\n" +
                    "Charging Status :\n" +
                    "Type                     :\n" +
                    "Voltage                :\n" +
                    "Capacity              :\n");

            textView2.setText(battery_condition + "\n" +
                    "" + temperature_c + "" + (char) 0x00B0 + "C/" + temperature_f + "" + (char) 0x00B0 + "F\n" +
                    "" + power_source + "\n" +
                    "" + charging_status + "\n" +
                    "" + technology + "\n" +
                    "" + voltage + "\n" +
                    "" + batterCapacity + "mAh\n");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float percentage = level / (float) scale;

            // Updating the Prograss Bar to display the Current Battery Charged Percentage
            mProgressStatus = (int) ((mProgressStatus) * 100);
            mTextViewPercentage.setText("" + level + "%");

            mProgressBar.setProgress(mProgressStatus);
        }
    };


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