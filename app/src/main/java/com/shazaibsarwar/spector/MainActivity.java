package com.shazaibsarwar.spector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    TextView mManufacturerTv, mAndroidVersionTv;

    // For Navigation Drawer
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Navigation View
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("");


        //Collapsing Toolbar
        initCollapsingToolbar();

        // RecyclerView
        mRecyclerView = findViewById(R.id.myRecyclerView);
        // Setting its Properties
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Grid View with 2 item each in coulmn
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Adapter
        myAdapter = new MyAdapter(this, getModels());
        mRecyclerView.setAdapter(myAdapter);

        // get Device Manufacturer name
        String mManufacturer = Build.MANUFACTURER; // For Example Samsung, Oppo , Infinix, Vivo etc
        // getting device Model
        String model = Build.MODEL;
        // Getting Device Android Version
        String version = Build.VERSION.RELEASE; // For example 8, 9 , 10 , 11
        // Getting android Version NAME
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();  // for Example Lollipop , Kitkat, Marshmallow etc

        // Setting information to Views
        mManufacturerTv = findViewById(R.id.name_model);
        mAndroidVersionTv = findViewById(R.id.android_version);


        mManufacturerTv.setText(mManufacturer.toUpperCase() + "" + model);
        mAndroidVersionTv.setText(version.toUpperCase() + "" + verName);


        // Display android version logo/icon
        try {
            // Jelly Bean
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
                Glide.with(this).load(R.drawable.android30).into((ImageView) findViewById(R.id.backdrop));
            }


            // Jelly Bean
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Glide.with(this).load(R.drawable.android30).into((ImageView) findViewById(R.id.backdrop));
            }

            // Jelly Bean
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Glide.with(this).load(R.drawable.android30).into((ImageView) findViewById(R.id.backdrop));
            }

            // Kitkat
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                Glide.with(this).load(R.drawable.android40).into((ImageView) findViewById(R.id.backdrop));
            }

            // Lolipop
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }
            // Lolipop
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backdrop));
            }
            // Lolipop
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Glide.with(this).load(R.drawable.android60).into((ImageView) findViewById(R.id.backdrop));
            }
            // Nougat
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backdrop));
            }
            // Oreo
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                Glide.with(this).load(R.drawable.infinix).into((ImageView) findViewById(R.id.backdrop));
            }
            // Oreo
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
                Glide.with(this).load(R.drawable.infinix).into((ImageView) findViewById(R.id.backdrop));
            }            // Oreo
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
                Glide.with(this).load(R.drawable.android90).into((ImageView) findViewById(R.id.backdrop));
            }


        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_ToolBar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // Set Collapsing Tool Bar Title
        collapsingToolbarLayout.setTitle(" Device Spector");
    }

    // Add models to ArrayList
    private ArrayList<Model> getModels() {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        Model model = new Model();

        model = new Model();
        model.setName("General");
        model.setImg(R.drawable.ic_genral);
        modelArrayList.add(model);

        model = new Model();
        model.setName("Device Id");
        model.setImg(R.drawable.ic_fingerprints);
        modelArrayList.add(model);

        model = new Model();
        model.setName("Display");
        model.setImg(R.drawable.ic_display);
        modelArrayList.add(model);

        model = new Model();
        model.setName("Battery");
        model.setImg(R.drawable.ic_battery);
        modelArrayList.add(model);


        model = new Model();
        model.setName("User Apps");
        model.setImg(R.drawable.ic_userapps);
        modelArrayList.add(model);


        model = new Model();
        model.setName("System Apps");
        model.setImg(R.drawable.ic_systemapps);
        modelArrayList.add(model);


        model = new Model();
        model.setName("Memory");
        model.setImg(R.drawable.ic_memory);
        modelArrayList.add(model);


        model = new Model();
        model.setName("CPU");
        model.setImg(R.drawable.ic_cpus);
        modelArrayList.add(model);


        model = new Model();
        model.setName("Sensors");
        model.setImg(R.drawable.ic_sensors);
        modelArrayList.add(model);


        model = new Model();
        model.setName("SIM");
        model.setImg(R.drawable.ic_sim);
        modelArrayList.add(model);

        return modelArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This function will called when search button in keyboard is pressed
                myAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // This function will be called whenever you typed in search view
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        //Handel other menu Click Here

//        if (id== R.id.action_setting) {
//            Toast.makeText(this,"Settings", Toast.LENGTH_SHORT).show();
//        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.genInfo:
                //Scroll view pager and tab layout to position 1
                Intent intent = new Intent(MainActivity.this, GeneralActivity.class);
                startActivity(intent);
                break;
            case R.id.deviceInfo:
                //Scroll view pager and tab layout to position 2
                Intent intent2 = new Intent(MainActivity.this, DeviceID_Activity.class);
                startActivity(intent2);
                break;
            case R.id.displayInfo:
                //Scroll view pager and tab layout to position 3
                Intent intent3 = new Intent(MainActivity.this, DisplayAvtivity.class);
                startActivity(intent3);
                break;
            case R.id.batteryInfo:
                //Scroll view pager and tab layout to position 4
                Intent intent4 = new Intent(MainActivity.this, BatteryActivity.class);
                startActivity(intent4);
                break;
            case R.id.userAppInfo:
                //Scroll view pager and tab layout to position 5
                Intent intent5 = new Intent(MainActivity.this, UserAppsActivity.class);
                startActivity(intent5);
                break;
            case R.id.systemAppInfo:
                //Scroll view pager and tab layout to position 6
                Intent intent6 = new Intent(MainActivity.this, SystemAppsActivity.class);
                startActivity(intent6);
                break;
            case R.id.memoryInfo:
                //Scroll view pager and tab layout to position 7
                Intent intent7 = new Intent(MainActivity.this, MemoryDetailsActivity.class);
                startActivity(intent7);
                break;
            case R.id.cpuInfo:
                //Scroll view pager and tab layout to position 8
                Intent intent8 = new Intent(MainActivity.this, CPU_Activity.class);
                startActivity(intent8);
                break;
            case R.id.sensorsInfo:
                //Scroll view pager and tab layout to position 9
                Intent intent9 = new Intent(MainActivity.this, SensorsActivity.class);
                startActivity(intent9);
                break;
            case R.id.simInfo:
                //Scroll view pager and tab layout to position 10
                Intent intent10 = new Intent(MainActivity.this, SIM_Activity.class);
                startActivity(intent10);
                break;
            case R.id.exitApp:
                //Scroll view pager and tab layout to position 11
                finish();
                System.exit(0);
                break;











        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
