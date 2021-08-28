package com.example.whosthatpokemonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.whosthatpokemonapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.optionOne.setOnClickListener(this);
        binding.optionTwo.setOnClickListener(this);
        binding.optionThree.setOnClickListener(this);
        binding.optionFour.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("STATE", "Hello");
    }
}