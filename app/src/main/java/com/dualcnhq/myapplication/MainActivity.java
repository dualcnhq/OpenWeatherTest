package com.dualcnhq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dualcnhq.myapplication.api.JSONObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;

    private static final String TAG = MainActivity.class.getSimpleName();

    private String URL = "http://api.openweathermap.org/data/2.5/weather?q=Los%20Ba%C3%B1os,Philippines&APPID=73caff86339604776803ac447986a99c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        getData();
    }

    private void getData(){
        JSONObjectRequest authRequest = new JSONObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error: " + error);

            }
        }, getApplicationContext());
        authRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(authRequest);
    }

    private void parseData(JSONObject response){
        try{
            Log.d(TAG, response.toString());

            JSONArray jArray = response.getJSONArray("weather");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                Log.d(TAG, "id: " + json_data.getString("id"));
                Log.d(TAG, "main: " + json_data.getString("main"));
                Log.d(TAG, "description: " + json_data.getString("description"));
                Log.d(TAG, "icon: " + json_data.getString("icon"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
