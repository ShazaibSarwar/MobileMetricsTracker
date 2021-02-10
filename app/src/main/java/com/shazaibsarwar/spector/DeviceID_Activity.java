package com.shazaibsarwar.spector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceID_Activity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriberId;

    // Phone State Permission
    private static final int READ_PHONE_STATE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);


        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID info");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Android Device ID
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        // IMEI Number
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_PERMISSION);
            } else {    // Permissions was granted

                imei = tm.getDeviceId();
                simCardSerial = tm.getSimSerialNumber();
                simSubscriberId = tm.getSubscriberId();
            }
        } else { // System OS is< marshmallow, no need runtime Permissions

            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriberId = tm.getSubscriberId();
        }


        // IP Address
        String ipAddress = "Internet Connection Failed";
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null) {
                ipAddress = is3GEnabled + "";
            }
        }

        // Wifi Mac Address
        String wifiAddress = "No Wifi Connection";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        wifiAddress = getWifiIPAddress();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mMobile != null) {
                wifiAddress = is3GEnabled + "";
            }
        }


        // Bluetooth Mac Address
        String bt = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");

        titles = new String[]{"Android Device Id", "IMEI", "MEID or ESN", "Hardware Serial Number", "Sim Subscriber ID", "IP Adress", "Wi-Fi Mac Address", "Build Fingerprint"};

        // to make descrion ihave to make a lot of mess like IP Adress / Wifi Mac Address/ Bluetooth

        descriptions = new String[]{deviceId, imei, Build.SERIAL, simCardSerial, simSubscriberId, ipAddress, wifiAddress, bt, Build.FINGERPRINT};


        ListView listView = findViewById(R.id.devIdList);
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(adapter);
    }

    private String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    private String getMobileIPAddress() {

        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intfs : interfaces) {
                List<InetAddress> addresses = Collections.list(intfs.getInetAddresses());
                for (InetAddress addr : addresses) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }


        } catch (Exception e) {
        }

        return "";
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    recreate(); // restart activity on permission granted
                    imei = tm.getDeviceId();
                    simCardSerial = tm.getSimSerialNumber();
                    simSubscriberId = tm.getSubscriberId();
                } else {
                    Toast.makeText(this, "Enable READ_PHONE_STATE permission from Settings", Toast.LENGTH_SHORT).show();
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}