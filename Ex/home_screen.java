package com.example.curdmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.curdmanagementapp.manager.BlockChainManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class home_screen extends AppCompatActivity {

    Button pinning;
    Button directions;
    Button logouts;
    TextView driverNames;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    ProgressDialog proDialog;

    private final static int ALL_PERMISSIONS_RESULT = 101;

    String pass_longitiute = null;
    String pass_latitiue = null;
    Thread tracking;
    boolean thread_start = false;
    Handler handler = new Handler();

    // GPSTracker class
    GPSTracker gps;

    private BlockChainManager blockChainManager;
    private boolean isEncripted,isDescriptrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        blockChainManager = new BlockChainManager(2);


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        driverNames = findViewById(R.id.driverName);

        driverNames.setText(MainActivity.DRIVER_NAME);

        pinning =  findViewById(R.id.pinning_BTN);

        directions = findViewById(R.id.direction_BTN);

        logouts = findViewById(R.id.logouts);

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass_longitiute == null){
                    Toast.makeText(getApplicationContext(), "Please Start Tracking before start direction", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+pass_latitiue+","+pass_longitiute+"&daddr=6.928578,79.84508"));
                    startActivity(intent);
                }
            }
        });

        pinning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("blocks create start");
               // CreateBlocks("sample dataas");
                if(MainActivity.TRUCK_ID == "0"){
                    Toast.makeText(getApplicationContext(), "Sorry, Today you have any vehical assing", Toast.LENGTH_SHORT).show();
                }else {
                   // getCurrentLocation();
                    thread_start = true;
                    tracking = new Thread() {
                        @Override
                        public void run() {
                            while (!isInterrupted()) {
                                try {
                                    Thread.sleep(5000);  //1000ms = 1 sec
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(thread_start){
                                                getCurrentLocation();
                                            }

                                        }
                                    });

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };

                    tracking.start();
                }

            }
        });

        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do all logout screens.
                thread_start = false;
                tracking.interrupt();
                Intent i = new Intent(v.getContext(),Auth_screen.class);
                startActivity(i);
                home_screen.this.finish();
            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(home_screen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopListener();
    }


    public void getCurrentLocation(){
        // Create class object
        gps = new GPSTracker(home_screen.this);

        //gps = new LocationTrack(MainActivity.this);

        if (gps.canGetLocation()) {

            double longitude = gps.getLongitude();
            double latitude = gps.getLatitude();

            pass_latitiue = latitude+"";
            pass_longitiute = longitude+"";



            String text_data = "Longitude:" + Double.toString(longitude) + " / Latitude:" + Double.toString(latitude);

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            System.out.println("blocks create start");
            CreateBlocks(text_data);

            System.out.println("Block lst "+blockChainManager.getLastBlock());
            sendLocationPinning(latitude,longitude);

        } else {

            gps.showSettingsAlert();
        }


        // Check if GPS enabled
//        if(gps.canGetLocation()) {
//
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//
//            sendLocationPinning(latitude,longitude);
//            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//        } else {
//            // Can't get location.
//            // GPS or network is not enabled.
//            // Ask user to enable GPS/network in settings.
//            gps.showSettingsAlert();
//        }
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    public void sendLocationPinning(double latitude,double longitude){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String driver = "1";
        String vehicalID = "1";
        String longi = longitude+"";
        String lati = latitude+"";


        String postUrl = MainActivity.BASE_URL+"LocationPinning/LocationPinnings.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println(response);
                        //proDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        error.printStackTrace();
                        //proDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("driveraID",driver);
                params.put("truckID",vehicalID);
                params.put("latitute",lati);
                params.put("longitute",longi);
                params.put("block",blockChainManager.getLastBlock().toString());
               // params.put("currentDate",currentDate);
              //  params.put("currentTime",currentTime);
                return params;
            }
        };
        queue.add(postRequest);
        //proDialog = new ProgressDialog(this);
        //proDialog.setMessage("Loading...");
        //proDialog.show();
    }

    public void CreateBlocks(String text_data){
        new Thread(() -> runOnUiThread(()->{

            blockChainManager.addBlock(blockChainManager.newBlock(text_data));

            System.out.println("Blockchain valid ? " + blockChainManager.isBlocakValid());
            System.out.println(blockChainManager);
        })).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}