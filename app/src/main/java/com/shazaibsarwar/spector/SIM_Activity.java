package com.shazaibsarwar.spector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SIM_Activity extends AppCompatActivity {

    // For Runtime Permissions
    private static final int READ_PHONE_STATE_CODE = 1;

    private String titles[];
    private String descriptions[];

    // ListView
    ListView listView;


    // Class to Get SIM information
    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);


        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("SIM information");

            // Setting Back Button in Action Bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        // Intializing List View
        listView = findViewById(R.id.simList);

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                // This will called When permission was not Allowed
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                // Show Popup for Runtime Permission
                requestPermissions(permissions, READ_PHONE_STATE_CODE);
            } else {    // Permissions was granted
                getPhoneInfo();
            }
        } else { // System OS is< marshmallow, no need runtime Permissions
            getPhoneInfo();
        }

    }

    // Function to get Sim information
    private void getPhoneInfo() {
        // Sim State      e.g = Ready
        int ss = tm.getSimState();
        String simState = "";

        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "Network Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO Error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "PERM Disabled";
                break;
        }

        // Service Provider e.g Jazz, Telenor Zong etc
        String serviceProvider = tm.getSimOperatorName();

        // Mobile Operator Name
        String mobOprName = tm.getNetworkOperatorName();

        // Getting Integrated circuit card identifier (ICCID)
        String simID = tm.getSimSerialNumber();

        // Unique Device ID (IMEI)
        String imei = tm.getDeviceId();

        // International Subscriber Mobile Id (IMSI)
        String tmSubscriberID = tm.getSubscriberId();

        //Device Software Version
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String softVersion = tm.getDeviceSoftwareVersion();

        //Mobile Country Code (MCC)
        String  country = tm.getNetworkCountryIso()  ;

        // Mobile Country Code (MCC) + Mobile Network Code (MNC)
        String mcc_mnc = tm.getSimOperator();

        // Voic Mail Tag
        String voiceMailTag = tm.getVoiceMailAlphaTag();

        // Roaming
        boolean roamingStatus = tm.isNetworkRoaming();

        // Adding this Info to Arrays
        titles = new String[]{"SIM State" ,
                "Service Provider" ,
                "Mobile Operator Name" ,
                "Integrated circuit card identifier (ICCID)" ,
                "Unique Device ID (IMEI)" ,
                "International Subscriber Mobile Id (IMSI)" ,
                "Device Software Version" ,
                "Mobile Country Code (MCC)"  ,
                "Mobile Country Code (MCC) + Mobile Network Code (MNC)" ,
                "Voic Mail" ,
                "Roaming"};


        descriptions = new String[]{
                ""+simState,
                ""+serviceProvider,
                ""+mobOprName,
                ""+simID,
                ""+imei,
                ""+tmSubscriberID,
                ""+softVersion,
                ""+country,
                ""+mcc_mnc,
                ""+voiceMailTag,
                ""+roamingStatus,
        };

        // Setting Addapter
        listView.setAdapter(new MyAdapter(this, titles , descriptions));
    }


    // My Custom Adapter
    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitles[];
        String myDesCription[];

        // constructor
        MyAdapter(Context context, String[] titles, String[] descriptions) {
            super(context, R.layout.two_row, R.id.title, titles);
            this.context = context;
            this.myTitles = titles;
            this.myDesCription = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.two_row, parent, false);

            // Views in two_row.xml
            TextView myTitle = row.findViewById(R.id.titleTV);
            TextView myDescription = row.findViewById(R.id.descTV);

            // Setting Text to View
            myTitle.setText(titles[position]);
            myDescription.setText(descriptions[position]);

            return row;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getPhoneInfo();

                } else {
                    Toast.makeText(this, "Enable READ_PHONE_STATE permission from Settings", Toast.LENGTH_SHORT).show();
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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