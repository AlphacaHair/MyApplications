package com.example.mybookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mybookmark.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding; //멤버 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //뷰페이저가 프레그먼트 3개와 연결하고자 함.
        //binding.viewPager.
        System.out.print("예스");
    }
}