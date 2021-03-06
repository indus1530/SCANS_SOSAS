package edu.aku.hassannaqvi.uen_scans_sosas.core;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.aku.hassannaqvi.uen_scans_sosas.R;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.AppInfo;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.ChildContract;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.DeceasedChildContract;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.FormsContract;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.MotherContract;
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.ProblemContract;
import edu.aku.hassannaqvi.uen_scans_sosas.databinding.AlertDialogLayoutBinding;
import edu.aku.hassannaqvi.uen_scans_sosas.databinding.CountAlertDialogLayoutBinding;
import edu.aku.hassannaqvi.uen_scans_sosas.ui.other.EndingActivity;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class MainApp extends Application {

    /*F38158 TEST SERVER*/
    public static final String _IP = "https://vcoe1.aku.edu";// .LIVE server
    //    public static final String _IP = "http://f38158";// .TEST server
    public static final String _HOST_URL = MainApp._IP + "/scans/api/";// .TEST server;
    public static final String _SERVER_URL = "sync.php";
    public static final String _UPDATE_URL = MainApp._IP + "/scans/app/sosas/";
    public static final String _PHOTO_UPLOAD_URL = MainApp._IP + "/scans/api/uploads.php";

    public static final Integer MONTHS_LIMIT = 11;
    public static final Integer DAYS_LIMIT = 29;
    //public static final long MILLISECONDS_IN_5YEAR = (MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR);
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final int TWENTY_MINUTES = 1000 * 60 * 20;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final long MILLIS_IN_SECOND = 1000;
    private static final long SECONDS_IN_MINUTE = 60;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long HOURS_IN_DAY = 24;
    public static final long MILLISECONDS_IN_DAY = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;
    private static final long DAYS_IN_YEAR = 365;
    public static final long MILLISECONDS_IN_YEAR = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR;
    private static final long DAYS_IN_5_YEAR = 365 * 5;
    public static final long MILLISECONDS_IN_5Years = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_5_YEAR;
    private static final long DAYS_IN_MONTH = 30;
    public static final long MILLISECONDS_IN_MONTH = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_MONTH;

    private static final long DAYS_IN_2_YEAR = 365 * 2;
    public static final long MILLISECONDS_IN_2Years = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_2_YEAR;
    //public static final long MILLISECONDS_IN_100_YEAR = MILLISECONDS_IN_YEAR * 100;
    public static String deviceId;
    public static OnItemClick itemClick;
    public static OnItemClick countItemClick;
    public static AppInfo appInfo;

    public static Boolean admin = false;
    public static FormsContract fc;
    public static DeceasedChildContract dcc;
    public static MotherContract mc;
    public static ChildContract cc;
    public static String userName = "0000";
    public static Boolean wrapperFlag = false;
    public static String DIST_ID;

    public static void setItemClick(OnItemClick itemClick) {
        MainApp.itemClick = itemClick;
    }

    public static void setCountItemClick(OnItemClick itemClick) {
        MainApp.countItemClick = itemClick;
    }

    public static int CounterDeceasedChild = 0;
    public static int counter = 0;
    public static int mm = 1;
    public static int imsCount = 1;
    public static int totalImsCount = 0;
    public static int sixMonthsCount = 0;
    public static int positionIm = 0;
    public static boolean flag = true;
    public static int versionCode;
    public static String versionName;
    public static String hh01txt = "0000";
    public static String hh02txt;
    public static int hh03txt = 1;
    public static String DeviceURL = "devices.php";
    public static String IMEI;
    public static String formtype;
    public static final String motherInfo = "motherInfo";
    public static FamilyMembersContract motherData;
    public static FamilyMembersContract childData;
    public static ProblemContract pc;

    public static SharedPreferences sharedPref;

    //    public static Map<String, FamilyMembersContract> childsMap = new HashMap<>();
    public static ArrayList<String> lstChild = new ArrayList<>();

    public static int ageRdo = 0;

    public static ArrayList<String> childList = new ArrayList<>();
    public static ArrayList<String> motherList = new ArrayList<>();
    public static ArrayList<Integer> motherSerial = new ArrayList<>();
    public static HashMap<String, String> motherMap = new HashMap<>();
    public static ArrayList<String> fatherList = new ArrayList<>();
    public static ArrayList<Integer> fatherSerial = new ArrayList<>();
    public static HashMap<String, String> fatherMap = new HashMap<>();


    //    Ali
    //    public static List<FamilyMembersContract> familyMembersList;
//    public static FamilyMembersContract fmc;
//    public static DeceasedMotherContract dcM;
//    public static DeceasedChildContract dcC;
    //    public static SectionIIMContract ims;
    public static String TAG = "AppMain";

    public static int memFlag = 0;

    public static int selectedPos = -1;
    public static int randID = 1;
    public static Boolean isRsvp = false;
    public static Boolean isHead = false;
    public static int ucCode = 0;
    public static int talukaCode = 0;
    public static int areaCode = 0;
    public static String cluster = "";
    public static String hhno = "";
    public static int BLRandomSize;
    public static int childCount = 0;
    public static int problemType = 0;
    public static int problemCount = 0;
    public static List<Integer> extLst;

    /*
     problem type
     1 = eyes
     2 = ears
     3 = face
     4 = neck
     5 = head
     6 = chest
     7 = back
     8 = abdomen
     9 = buttocks/groin/genitalia
     10 = fingers extremities
     11 = thumb/hand extremities
     12 = lower arm extremities
     13 = upper arm extremities
     14 = foot extremities
     15 = lower leg extremities
     16 = upper leg extremities
     */

    public static String lhwCode;
    public static String lhwName;
    public static String hh04txt;
    public static String villageName;
    public static String villageCode;
    protected static LocationManager locationManager;

    public static void setGPS(Activity activity) {
        SharedPreferences GPSPref = activity.getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

//        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String dt = GPSPref.getString("Time", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(activity, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            MainApp.fc.setGpsLat(GPSPref.getString("Latitude", "0"));
            MainApp.fc.setGpsLng(GPSPref.getString("Longitude", "0"));
            MainApp.fc.setGpsAcc(GPSPref.getString("Accuracy", "0"));
//            AppMain.fc.setGpsTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above
            MainApp.fc.setGpsDT(date); // Timestamp is converted to date above

            Toast.makeText(activity, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("GPS", "setGPS: " + e.getMessage());
        }


    }

    public static void finishActivity(final Context context, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                activity.finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void endActivity(final Context context, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                activity.finish();
                                Intent end_intent = new Intent(context, EndingActivity.class);
                                end_intent.putExtra("check", false);
                                context.startActivity(end_intent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void openCountDialog(Context context, int count) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.count_alert_dialog_layout, null);
        CountAlertDialogLayoutBinding bi = DataBindingUtil.bind(view.getRootView());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        bi.mainHeading.setText(context.getString(R.string.countAlertMsg) + ("(No:" + String.format("%2d", count) + ")"));

        bi.continueBtn.setOnClickListener(v -> {
            countItemClick.itemClick();
            dialog.dismiss();
        });

        bi.noBtn.setOnClickListener(v -> dialog.dismiss());
    }

    public static void openDialog(Context context, FamilyMembersContract item, boolean isMother) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null);
        AlertDialogLayoutBinding bi = DataBindingUtil.bind(view.getRootView());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        bi.itemLayout.mainHeading.setText("You Have Selected " + item.getName());
        bi.itemLayout.genderImage.setImageResource(isMother ? R.drawable.mother_1 : R.drawable.maternity);
        bi.itemLayout.hhNo.setText(item.getHhno());
        bi.itemLayout.clusterNo.setText(item.getClusterno());
        bi.itemLayout.dob.setText(item.getAge() + " Years");
        bi.itemLayout.name.setText(item.getName());
        bi.itemLayout.id.setText(item.getSerialno());

        bi.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick();

                dialog.dismiss();
            }
        });

        bi.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    public interface OnItemClick {
        void itemClick();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/MBLateefi.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        // Requires Permission for GPS -- android.permission.ACCESS_FINE_LOCATION
        // Requires Additional permission for 5.0 -- android.hardware.location.gps
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener() // Implement this class from code

        );

        sharedPref = getSharedPreferences("PSUCodes", Context.MODE_PRIVATE);

        //Initialize InfoClass
        MainApp.appInfo = new AppInfo(this);
        MainApp.versionCode = MainApp.appInfo.getVersionCode();
        MainApp.versionName = MainApp.appInfo.getVersionName();

    }

    protected void showCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            //Toast.makeText(getApplicationContext(), message,
            //Toast.LENGTH_SHORT).show();
        }

    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;

            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {

            SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            String dt = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(sharedPref.getString("Time", "0"))).toString();

            Location bestLocation = new Location("storedProvider");
            bestLocation.setAccuracy(Float.parseFloat(sharedPref.getString("Accuracy", "0")));
            bestLocation.setTime(Long.parseLong(sharedPref.getString(dt, "0")));
            bestLocation.setLatitude(Float.parseFloat(sharedPref.getString("Latitude", "0")));
            bestLocation.setLongitude(Float.parseFloat(sharedPref.getString("Longitude", "0")));

            if (isBetterLocation(location, bestLocation)) {
                editor.putString("Longitude", String.valueOf(location.getLongitude()));
                editor.putString("Latitude", String.valueOf(location.getLatitude()));
                editor.putString("Accuracy", String.valueOf(location.getAccuracy()));
                editor.putString("Time", String.valueOf(location.getTime()));
                String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(String.valueOf(location.getTime()))).toString();
//                Toast.makeText(getApplicationContext(),
//                        "GPS Commit! LAT: " + String.valueOf(location.getLongitude()) +
//                                " LNG: " + String.valueOf(location.getLatitude()) +
//                                " Accuracy: " + String.valueOf(location.getAccuracy()) +
//                                " Time: " + date,
//                        Toast.LENGTH_SHORT).show();

                editor.apply();
            }
        }


        public void onStatusChanged(String s, int i, Bundle b) {
            showCurrentLocation();
        }

        public void onProviderDisabled(String s) {

        }

        public void onProviderEnabled(String s) {

        }
    }

}