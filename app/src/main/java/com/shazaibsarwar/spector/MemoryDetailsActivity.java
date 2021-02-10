package com.shazaibsarwar.spector;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

public class MemoryDetailsActivity extends AppCompatActivity {

    // Declaring All Views
    TextView tvTotalRam , tvFreeRam , tvUsedRam;
    TextView tvTotalRom , tvFreeRom , tvUsedRom;
    TextView tvTotalHeap;
    TextView tvPecRam , tvPercRom;

    ProgressBar pbRAM , pbROM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_details);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory Details");

            // Setting Back Button in Action Bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        // Initialing All Views
        tvPecRam = findViewById(R.id.tv_percentage_ram);
        tvPercRom = findViewById(R.id.tv_percentage_rom);
        //Progress Bar
        pbRAM = findViewById(R.id.pbRAM);
        pbROM = findViewById(R.id.pbROM);

        //RAM
        tvTotalRam  = findViewById(R.id.total_ram);
        tvFreeRam  = findViewById(R.id.freeRam);
        tvUsedRam  = findViewById(R.id.usedRam);

        //ROM
        tvTotalRom  = findViewById(R.id.total_rom);
        tvFreeRom  = findViewById(R.id.freeRom);
        tvUsedRom  = findViewById(R.id.usedRom);

        // Heap
        tvTotalHeap = findViewById(R.id.totalHeap);



        // Getting Information of RAM
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        float totalMem =  memoryInfo.totalMem/(1024*1024);
        float FreeMem =  memoryInfo.availMem/(1024*1024);
        float usedMem = totalMem - FreeMem;

        //Percentage of free RAM
        float freeMemPrec  = FreeMem/totalMem*100;
        // Percentage of Used Apps
        float usedMemPerc = usedMem/totalMem*100;

        // Free RAM percentage decimal point conversion
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(1);
        numberFormat.setMaximumFractionDigits(1);
        String freeMemPercentage = numberFormat.format(freeMemPrec);

        // Used RAM percentage decimal point Conversion
        NumberFormat numberFormatUsed = NumberFormat.getNumberInstance();
        numberFormatUsed.setMinimumFractionDigits(1);
        numberFormatUsed.setMaximumFractionDigits(1);
        String usedMemPercentage = numberFormat.format(usedMemPerc);


        // Getting Information of ROM
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        float blockSize = statFs.getBlockSize();
        float totalBlock = statFs.getBlockCount();
        float availableBlock = statFs.getAvailableBlocksLong();

        float totalROM = (totalBlock*blockSize)/(1024*1024);
        float freeROM = (availableBlock*blockSize)/(1024*1024);
        float usedROM = totalROM - freeROM;

        //Percentage of free ROM
        float freeROMPrec  = (freeROM/totalROM)*100;
        // Percentage of Used Apps
        float usedROMPerc = (usedROM/totalROM)*100;

        // Free ROM  decimal point conversion
        NumberFormat numberFormatFreeROM = NumberFormat.getNumberInstance();
        numberFormatFreeROM.setMinimumFractionDigits(1);
        numberFormatFreeROM.setMaximumFractionDigits(1);
        String free_ROM_string = numberFormat.format(freeROM);

        // Used RAM  decimal point Conversion
        NumberFormat numberFormatUsedROM = NumberFormat.getNumberInstance();
        numberFormatUsedROM.setMinimumFractionDigits(1);
        numberFormatUsedROM.setMaximumFractionDigits(1);
        String used_ROM_string = numberFormat.format(usedROM);


//        // Free ROM Percentage decimal point conversion
//        NumberFormat numberFormatFreeROM_Perc = NumberFormat.getNumberInstance();
//        numberFormatFreeROM_Perc.setMinimumFractionDigits(1);
//        numberFormatFreeROM_Perc.setMaximumFractionDigits(1);
//        String freeROMPercentage = numberFormat.format(freeROM);
//
//        // Used RAM Percentage decimal point Conversion
//        NumberFormat numberFormatUsedROM_per = NumberFormat.getNumberInstance();
//        numberFormatUsedROM_per.setMinimumFractionDigits(1);
//        numberFormatUsedROM_per.setMaximumFractionDigits(1);
//        String usedROMPercentage = numberFormat.format(usedROM);

        // Free RAM percentage decimal point conversion
        NumberFormat numberFormat2 = NumberFormat.getNumberInstance();
        numberFormat2.setMinimumFractionDigits(1);
        numberFormat2.setMaximumFractionDigits(1);
        String freeROMPercentage = numberFormat.format(freeROMPrec);

        // Used RAM percentage decimal point Conversion
        NumberFormat numberFormatUsed3 = NumberFormat.getNumberInstance();
        numberFormatUsed3.setMinimumFractionDigits(1);
        numberFormatUsed3.setMaximumFractionDigits(1);
        String usedROMPercentage = numberFormat.format(usedROMPerc);

        NumberFormat numberFormatUsed4 = NumberFormat.getNumberInstance();
        numberFormatUsed4.setMinimumFractionDigits(1);
        numberFormatUsed4.setMaximumFractionDigits(1);
        String totalROMPercentage = numberFormat.format(totalROM);






        // Setting RAM info
        tvTotalRam.setText(" "+totalMem+ " MB");
        tvFreeRam.setText(" "+ FreeMem + " MB" + "   (" + freeMemPercentage + "%)");
        tvUsedRam.setText(" "+ usedMem + " MB" + "   (" + usedMemPercentage + "%)");

        // Setting ROM info Percentage
        tvTotalRom.setText(" "+totalROMPercentage+ " MB");
        tvFreeRom.setText(" "+ free_ROM_string + " MB" + "     (" + freeROMPercentage + "%)");
        tvUsedRom.setText(" "+ used_ROM_string + " MB" + "   (" + usedROMPercentage + "%)");

        // Getting JAVA HEAP
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        //Setting JAVA Heap
        tvTotalHeap.setText(" "+maxMemory/(1024*1024)+ " MB");

        // Setting RAM ino to Progress BAR
        tvPecRam.setText(usedMemPercentage+"% Used");
        pbRAM.setProgress((int)usedMemPerc);

        // Setting ROM ino to Progress BAR
        tvPercRom.setText(usedROMPercentage+"% Used");
        pbRAM.setProgress((int)(usedROM/totalROM)*100);


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