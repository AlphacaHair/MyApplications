package com.example.funnygames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funnygames.databinding.ActivityGame2Binding;

import java.util.ArrayList;
import java.util.Random;

public class Game2 extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnScrollListener {

    ActivityGame2Binding binding;
    //선택한 공의 번호를 저장할 ArrayList
    ArrayList<Integer> myBall = new ArrayList<>();
    ArrayList<Integer> robotBall = new ArrayList<>();

    //선택한 공을 표시할 텍스트뷰에 대한 ArrayList
    ArrayList<TextView> myBallTextView = new ArrayList<>();
    ArrayList<TextView> robotBallTextView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGame2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //버튼의 바인딩
        binding.btnAdd.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);
        binding.btnPlay.setOnClickListener(this);

        //numberPicker
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(45);
        Random r = new Random();
        binding.numberPicker.setValue(r.nextInt(45)+1);
        binding.numberPicker.setOnScrollListener(this);

        //TEXTVIEW
        myBallTextView.add(binding.myBall1);
        myBallTextView.add(binding.myBall2);
        myBallTextView.add(binding.myBall3);
        myBallTextView.add(binding.myBall4);
        myBallTextView.add(binding.myBall5);
        myBallTextView.add(binding.myBall6);

        robotBallTextView.add(binding.randomBall1);
        robotBallTextView.add(binding.randomBall2);
        robotBallTextView.add(binding.randomBall3);
        robotBallTextView.add(binding.randomBall4);
        robotBallTextView.add(binding.randomBall5);
        robotBallTextView.add(binding.randomBall6);

        for(int i = 0; i < 6; i++){
            myBallTextView.get(i).setVisibility(View.INVISIBLE);
            robotBallTextView.get(i).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_add://추가버튼
                addMyNumber();
                break;

            case R.id.btn_clear://삭제버튼
                for(int i = 0; i<myBall.size(); i++){
                    myBallTextView.get(i).setText("0");
                }
                myBall.clear();
                for(int i = 0; i<robotBall.size(); i++){
                    robotBallTextView.get(i).setText("0");
                }
                robotBall.clear();
                binding.numberPicker.setEnabled(true);
                break;

            case R.id.btnPlay:///로또번호기버튼
                addRobotNumber();
                winCount();
                binding.numberPicker.setEnabled(true);
                break;
        }



    }

    private void winCount() {

        if(myBall.size() != 6){
            return;
        }

//
//        int count = 0;
//        for(int i = 0; i < myBall.size(); i++){
//            if(myBall.get(i) == robotBall.get(i)){
//                count++;
//            }
//        }
//        binding.txtMessage.setText("맞춘 갯수 : " + count + "개");
//
        String same = "";
        int count = 0;
        for(Integer a: myBall){
            if(robotBall.contains(a)){
                count++;
                same += a + " ";
            }
        }
        binding.txtMessage.setText(count + "개의 문자가 일치합니다.\n" + same);
    }

    private void addRobotNumber() {

        if(robotBall.size()==6){
            robotBall.clear();
        }

        Random ran = new Random();
        while(robotBall.size() < 6) {
            Integer robotNum = ran.nextInt(45)+1;
            if(robotBall.contains(robotNum)){
                continue;
            }
            robotBallTextView.get(robotBall.size()).setText(robotNum.toString());
            robotBallTextView.get(robotBall.size()).setVisibility(View.VISIBLE);
            robotBall.add(robotNum);
        }

    }

    private void addMyNumber() {

        if(myBall.size() == 6){
            Toast.makeText(this, "이미 6개 모두 선택되었습니다.", Toast.LENGTH_SHORT).show();
            binding.numberPicker.setEnabled(false);
            return;
        }

        int myNum = binding.numberPicker.getValue();
        if(myBall.contains(myNum)){
            Toast.makeText(this, "이미 등록된 숫자입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        myBallTextView.get(myBall.size()).setText(String.valueOf(myNum));
        myBallTextView.get(myBall.size()).setVisibility(View.VISIBLE);
        myBall.add(myNum);
    }

    @Override

    public void onScrollStateChange(NumberPicker numberPicker, int i) {

        if(i != SCROLL_STATE_IDLE || binding.switch1.isChecked()==false ){
            return;
        }
        binding.btnAdd.performClick();

    }
}