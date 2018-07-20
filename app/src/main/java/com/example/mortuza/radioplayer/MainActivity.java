package com.example.mortuza.radioplayer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mortuza.radioplayer.adapters.MyRecyclerAdapter;
import com.example.mortuza.radioplayer.model.DataModel;
import com.example.mortuza.radioplayer.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;
    List<DataModel> myDataModels = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainRecyclerView);
        swipeRefreshLayout = findViewById(R.id.srl);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerAdapter = new MyRecyclerAdapter(this, myDataModels);
        recyclerView.setAdapter(myRecyclerAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                // TODO: Load Data
            }
        });

        // TODO: Onclick Activation And Send Data Using Intent to Another Activity

        //parseJsonData();
    }

    private void parseJsonData() {
        String url = "https://script.googleusercontent.com/macros/echo?user_content_key=nA_hR3hsJTyPa6ZRG3ffKqxX-pjyOg_bjEYOYA2qDTj38bhpFppGUHU_XVnCml67qE2EwovH0BwdW751dCtECm0XKHCm025zOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa5V7SzAZj2xBfFDRtNxpfsmuqfjnOYLBpWrI3G8IWJh29l4LSossvEa_fiNHZ0znxEBErwHi9mmicUM0HMtnaiwUei0F3ImvuoJxMJwepHIEVubryYo1CKzm1pljnAdCSEpIEmuIt9ZewqJbeTowOR_fVIgxkfofiQ&lib=M7OO09pfGNQD9igEAo4bouJoiE_6Oxspk";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("data", "" + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("records");
                    Log.d("data", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("data", "" + jsonObject.getString("Name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", "Error In Parsing.");
            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onRefresh() {
        // TODO: Code to refress to get new data from internet.
    }
}