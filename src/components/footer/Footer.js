import React from 'react';
import { Button, Typography, Grid, Box } from '@mui/material';

const Footer = (props) =>{
    return(
        <div>
            <Grid container
                direction="column"
                justifyContent="center"
                alignItems="center">
                <Grid item xs={6} md={8}>
                    <Box>
                        <Typography variant="caption" display="block"
                        color="textSecondary"
                        variant="caption"
                        align="center">
                        An open source project made in Sri Lanka 
                        --contributed and used by University Students.<br />
                        Original Repository by <a sx={{color: 'text.secondary'}} 
                        href="https://github.com/geethmaka">geethmaka</a>
                        </Typography>
                    </Box>
                </Grid>
            </Grid>
        </div>
    )
}

export default Footer;
package com.example.curdmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Auth_screen extends AppCompatActivity {

    Button signin;
    EditText username_input;
    EditText password_input;
    ProgressDialog proDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_screen);
        signin = findViewById(R.id.btn_signIN);
        username_input = findViewById(R.id.username);
        password_input = findViewById(R.id.password);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirctForHome();
            }
        });
    }

    public void redirctForHome(){

        String uname = username_input.getText().toString();
        String pass = password_input.getText().toString();



        if(!(uname.isEmpty()) && !(pass.isEmpty())){
           // loadNextScreen();
            AuthFromServer(uname,pass);
//            Intent i = new Intent(this,home_screen.class);
//            startActivity(i);
//            Auth_screen.this.finish();
        }else {
            Toast t = Toast.makeText(this,"Plase enter your username and password",Toast.LENGTH_SHORT);
            t.show();
        }


    }

    public void loadNextScreen(){
        Intent i = new Intent(this,home_screen.class);
        startActivity(i);
        Auth_screen.this.finish();
    }

    public void showErrorMsg(int code){
        if(code == 400){
            Toast ts = Toast.makeText(this,"Sorry we couldn't find your info, please try again",Toast.LENGTH_SHORT);
            ts.show();
        }else {
            Toast tv = Toast.makeText(this,"Something went wrong, Please try again later",Toast.LENGTH_SHORT);
            tv.show();
        }
    }

    public void AuthFromServer(String username, String pass) {

        String postUrl = MainActivity.BASE_URL+"Auth/getAuth.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println(response);
                        try {
                            JSONObject json = new JSONObject(response);

                            if(((JSONObject) json.get("status")).get("code").equals("200")){
                                //successfully log
                                //save local driver and truck id
                                MainActivity.DRIVER_ID = ((JSONObject) json.get("driverInfo")).get("DriverID").toString();
                                MainActivity.TRUCK_ID =  ((JSONObject) json.get("driverInfo")).get("TruckID").toString(); //DriverName
                                MainActivity.DRIVER_NAME = ((JSONObject) json.get("driverInfo")).get("DriverName").toString();
                                proDialog.dismiss();
                                loadNextScreen();

                            }else if(((JSONObject) json.get("status")).get("code").equals("205")){
                                //successfully log
                                //save local driver
                                // no truck assing and disbale tracking
                                MainActivity.DRIVER_ID = ((JSONObject) json.get("driverInfo")).get("DriverID").toString();
                                MainActivity.DRIVER_NAME = ((JSONObject) json.get("driverInfo")).get("DriverName").toString();
                                proDialog.dismiss();
                                loadNextScreen();

                            }else if(((JSONObject) json.get("status")).get("code").equals("400")){
                                // no user found faild to login
                                proDialog.dismiss();
                                showErrorMsg(400);
                            }else {
                                // server errors
                                proDialog.dismiss();
                                showErrorMsg(500);
                            }
                            proDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        error.printStackTrace();
                        proDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",pass);

                return params;
            }
        };
        queue.add(postRequest);
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("Loading...");
        proDialog.show();
    }
}
