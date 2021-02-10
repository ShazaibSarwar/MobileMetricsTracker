package com.shazaibsarwar.spector;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SystemAppsActivity extends AppCompatActivity {


    List<AppList> installedApps;
    AppAdapter installedAppAdapter;

    // ListView
    ListView userInstalledAppLV;

    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_apps);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setTitle("System Apps List");

            // Setting Back Button in Action Bar

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Getting Refrence of ListView
        userInstalledAppLV = findViewById(R.id.system_app_list);

        // call Method to get all Installed Apps
        installedApps = getInstalledApps();

        // Initializing our Adapter
        installedAppAdapter = new AppAdapter(SystemAppsActivity.this , installedApps);

        // Setting Data to Adapter
        userInstalledAppLV.setAdapter(installedAppAdapter);

        // On List Item Click Listener     We Will Show Dialogue Here
        userInstalledAppLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] options = {"Open App" , "App Info" };

                //Alert Dialogue Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(SystemAppsActivity.this);

                // Setting Title on it Choose Option
                builder.setTitle("Choose Action");

                // Setting Option
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // It means index 0 is Called which is "Open App"
                        if (which == 0 ) {
                            Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(position).packages);

                            if (intent != null) {
                                startActivity(intent);
                            }else {
                                Toast.makeText(SystemAppsActivity.this, "This is Disable by Default", Toast.LENGTH_SHORT).show();
                            }

                        }

                        // It means index 1 is Called which is "Apps Info"
                        if (which == 1 ) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+installedApps.get(position).packages));

                            // Showing package Name in Toast
                            Toast.makeText(SystemAppsActivity.this, installedApps.get(position).packages, Toast.LENGTH_SHORT).show();
                            startActivity(intent);


                        }


                    }
                });

                // Showing Dialogue
                builder.show();
            }
        });


        // Getting Total Number of Installed Apps / List size
        String size = userInstalledAppLV.getCount()+"";

        // Now We Will show the size of Apps to top Text View
        TextView countApps = findViewById(R.id.countAps);
        countApps.setText("Total Installed Apps are :" + size);


    }

    // Apps List Class
    public class AppList{
        String name;
        Drawable icon;
        String packages;
        String version;

        // Constructor
        AppList(String name, Drawable icon, String packages, String version)
        {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }

        public String getVersion() {
            return version;
        }
    }

    // Adapter Class For Apps
    class AppAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        List<AppList> listStorage;

        //Constructor of Adapter Class
        AppAdapter(Context context, List<AppList> customizedListView)
        {
            // Layout Inflater
            layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;

            if (convertView == null)
            {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.modelapps_row_design , parent, false);

                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView = convertView.findViewById(R.id.app_packge);
                listViewHolder.versionInListView = convertView.findViewById(R.id.version);


                convertView.setTag(listViewHolder);

            }
            else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            // Set Data to our View
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());
            listViewHolder.versionInListView.setText(listStorage.get(position).getVersion());

            return convertView;  // returning the whole View
        }

        class ViewHolder{

            // Our Views From our Custom row Design
            TextView textInListView;
            TextView packageInListView;
            TextView versionInListView;
            ImageView imageInListView;

        }


    }

    // Getting Applications Information
    private List<AppList> getInstalledApps() {
        apps = new ArrayList<AppList>();
        packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0;  i<packs.size();  i++)
        {
            PackageInfo p = packs.get(i);

            // Validate if is not System Apps      Because we will Display Sys Apps in another
            if (isSystemPackage(p))
            {
                // Getting Application Name
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();

                // Getting Application Icon
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());

                // Getting Application Package Name
                String packages = p.applicationInfo.packageName;

                // Getting Application Version
                String version = p.versionName;


                // Adding data to our List
                apps.add(new AppList(appName , icon , packages , version));
            }
        }

        return apps;
    }

    //Validation if is not System App
    private boolean isSystemPackage(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
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


