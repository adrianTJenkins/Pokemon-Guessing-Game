package com.example.whosthatpokemonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.whosthatpokemonapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            // JSON Request
            String url = "https://app.pokemon-api.xyz/pokemon/random";
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject nameObject = response.getJSONObject("name");
                                String name = nameObject.getString("english");
                                String img = response.getString("thumbnail");
                                pokemon.setName(name);
                                pokemon.setImgUrl(img);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });
            queue.add(jsonObjectRequest);

            handler.post(() -> {
                //UI Thread
                binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
                binding.optionOne.setOnClickListener(this);
                binding.optionTwo.setOnClickListener(this);
                binding.optionThree.setOnClickListener(this);
                binding.optionFour.setOnClickListener(this);
                Log.d("IMG",pokemon.imgUrl);
                Glide.with(this).load(pokemon.imgUrl).into(binding.pkmImage); //share pokemon state after request
            });
        });


    }

    @Override
    public void onClick(View v) {
        Log.d("STATE", "Hello");
    }
}