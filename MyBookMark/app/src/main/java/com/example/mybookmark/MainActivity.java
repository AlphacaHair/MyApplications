package com.example.mybookmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.View;

import com.example.mybookmark.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding; //멤버 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //뷰페이저가 프레그먼트 3개와 연결하고자 함.
        binding.viewPager.setAdapter(new ViewPageAdapter(this));

        //탭과 연결해보자
        TabLayoutMediator.TabConfigurationStrategy strategy = null;
        strategy = new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position){
                if(position==0){
                    tab.setText("월요일");
                } else if(position==1){
                    tab.setText("화요일");
                } else if(position==2){
                    tab.setText("환경설정");
                }
            }
        };

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, strategy).attach();

    }
}