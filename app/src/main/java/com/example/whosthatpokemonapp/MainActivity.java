package com.example.whosthatpokemonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.whosthatpokemonapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private ArrayList<String> pokemonNames = new ArrayList<>();
    private String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.appHeader.setText("Who's that Pokemon?");
        binding.pkmImage.setColorFilter(null);


        String pokeApiUrl = "https://pokeapi.co/api/v2/pokemon/?limit=898";
        RequestQueue initialQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest namesRequest = new JsonObjectRequest
                (Request.Method.GET, pokeApiUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray nameArray = response.getJSONArray("results");
                            for(int i = 0; i < nameArray.length(); i++) {
                                pokemonNames.add(nameArray.getJSONObject(i).getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Error", "Error Response");
                    }
                });
        initialQueue.add(namesRequest);

        String url = "https://app.pokemon-api.xyz/pokemon/random";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest pkmRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject nameObject = response.getJSONObject("name");
                            name = nameObject.getString("english");
                            String img = response.getString("thumbnail");
                            randomizeButtons(name);
                            Glide.with(MainActivity.this).load(img).into(binding.pkmImage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Error", "Error Response");
                    }
                });
        queue.add(pkmRequest);

    }

    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().toString().equals(name)) {
            binding.appHeader.setText("Correct!");
        }
        else {
            binding.appHeader.setText("Incorrect!");
        }
        binding.pkmImage.setColorFilter(Color.argb(0, 0, 0, 0));
    }

    public void randomizeButtons(String n) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(0);
        nums.add(1);
        nums.add(2);
        nums.add(3);

        Collections.shuffle(nums);

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(binding.optionOne);
        buttons.add(binding.optionTwo);
        buttons.add(binding.optionThree);
        buttons.add(binding.optionFour);

        for (int i = 0; i < nums.size(); i++) {
            if(i == 0) {
                buttons.get(i).setText(n);
            }
            else {
                buttons.get(i).setText(pokemonNames.get((int)(Math.random()*898)));
            }
        }
    }
}