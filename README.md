# Spector

## 1	General Information 
All from Build Class which is static
•	Build
SDK_INT : The SDK version of the software currently running on this hardware device.




## 2	Device ID
In device id Data is extracted from  these bellow classes
•	Settings.Secure (Link)  

java.lang.Object
   ↳	android.provider.Settings.NameValueTable
      ↳	android.provider.Settings.Secure  

Secure system settings, containing system preferences that applications can read
but are not allowed to write. These are for preferences that the user must explicitly
modify through the UI of a system app. Normal applications cannot modify the secure
settings database, either directly or by calling the "put" methods that this class contains.
•	TelephonyManager (Link) 

java.lang.Object
   
   ↳	android.telephony.TelephonyManager
   
Provides access to information about the telephony services on the device. Applications
can use the methods in this class to determine telephony services and states, as well as
to access some types of subscriber information. Applications can also register a listener
to receive notification of telephony state changes.
•	Also Iam using  ConnectivityManager , NetworkInfo , WifiManager 
These classes to get some data about connection , Network IP Adress Wifi Mac Adress etc





## 3.	Display Information
To get Display information I used 
•	DisplayMetrics (Link)

java.lang.Object
   ↳	android.util.DisplayMetrics
   
A structure describing general information about a display, such as its size, density, and font scaling. To access the DisplayMetrics members, retrieve display metrics.
•	Display (Link)

java.lang.Object
   ↳	android.view.Display
   
Provides information about the size and density of a logical display.





## 4.	Battery Information 
I used BatteryManager Class to get information about battery and charging states  
•	BatteryManager (Link)

java.lang.Object
   ↳	android.os.BatteryManager
   
The BatteryManager class contains strings and constants used for values in the Intent,
and provides a method for querying battery and charging properties.




## 5.	User Application Installed in Phone
To get all the user Installed application I used 
•	PackageInfo (Link)

java.lang.Object
   ↳	android.content.pm.PackageInfo
   
Overall information about the contents of a package. This corresponds to all of the information collected from AndroidManifest.xml.
•	ApplicationInfo (Link)

java.lang.Object
   ↳	android.content.pm.PackageItemInfo
 	   ↳	android.content.pm.ApplicationInfo
     
Information you can retrieve about a particular application. This corresponds to information collected from the AndroidManifest.xml's <application> tag.




## 6.	System Information
I used the same steps as pervious (User Apps) step but with minor checks and changes.



## 7.	Memory Information 
To get the memory information about ROM and RAM I used two class to get data which are
•	ActivityManager (Link)

java.lang.Object
   ↳	android.app.ActivityManager
   
This class gives information about, and interacts with, activities, services, and the containing process.
•	Environment (Link)

java.lang.Object
      ↳android.os.Environment
             
Provides access to environment variables.
•	StatFs (Link)

java.lang.Object
     ↳  android.os.StatFs
          
Retrieve overall information about the space on a filesystem. This is a wrapper for Unix statvfs().




## 8.	CPU Information 
I’m using ProcessBuilder class to get the Processors and CPU information in this Activity
I’m just reading information that is stored at {"/system/bin/cat"    ,    "/proc/cpuinfo"};




## 9.	 Sensors Information
These two classes I used to get all the available sensors which are
•	SensorManager (Link)
java.lang.Object
   ↳	android.hardware.SensorManager
SensorManager lets you access the device's sensors.
•	Sensor (Link)
java.lang.Object
   ↳	android.hardware.Sensor
Class representing a sensor. Use SensorManager#getSensorList to get the list of available sensors.




## 10.	SIM information 
In this activity I used same TelephonyManger class to get SIM information 

