package com.example.funnygames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class Game1 extends AppCompatActivity implements View.OnClickListener {
    //공통 변수 선언
    ImageButton btn_scissors, btn_rock, btn_paper;
    ImageView img_user_select, img_robot_select;
    TextView score_user, score_robot;
    TextView message, remainCnt;

    int img_hands[] = {R.drawable.gif_scissors, R.drawable.gif_rock, R.drawable.gif_paper};

    //0, 1, 2 유저가 앞, 로봇이 뒤
    int WIN[][] = {{0, 2}, {1, 0}, {2, 1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        btn_scissors = findViewById(R.id.btn_scissors);
        btn_rock = findViewById(R.id.btn_rock);
        btn_paper = findViewById(R.id.btn_paper);
        img_user_select = findViewById(R.id.img_user_select);
        img_robot_select = findViewById(R.id.img_robot_select);
        score_user = findViewById(R.id.score_user);
        score_robot = findViewById(R.id.score_robot);
        message = findViewById(R.id.message);
        remainCnt = findViewById(R.id.remainCnt);

        btn_paper.setOnClickListener(this);
        btn_scissors.setOnClickListener(this);
        btn_rock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //기회 차감하기

        int valuser = 0, valrobot = 0;

        //가위 바위 보를 구별해준다. //0 1 2
        if(view.getId()==R.id.btn_scissors)
            valuser = 0;
        else if(view.getId()==R.id.btn_rock)
            valuser = 1;
        else if(view.getId()==R.id.btn_paper)
            valuser = 2;
        img_user_select.setImageResource(img_hands[valuser]);


        //로봇이 랜덤으로 가위 바위 보를 선택한다. //0 1 2
        Random r = new Random();
        valrobot = r.nextInt(3);
        img_robot_select.setImageResource(img_hands[valrobot]);

        //승부를 가린다.
        //같은 경우
        if(valuser == valrobot){
            try {
                shakeGifImage(img_user_select, img_hands[valuser]);
                shakeGifImage(img_robot_select, img_hands[valrobot]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //유저가 승리했을 경우
        } else if(valuser == WIN[0][0] && valrobot == WIN[0][1] ||
                  valuser == WIN[1][0] && valrobot == WIN[1][1] ||
                  valuser == WIN[2][0] && valrobot == WIN[2][1]){
            try {
                shakeGifImage(img_user_select, img_hands[valuser]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //스코어 올리기
            Integer score = Integer.parseInt(score_user.getText().toString());
            score += 1;
            score_user.setText(score.toString());
        }else{
            try {
                shakeGifImage(img_robot_select, img_hands[valrobot]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //스코어 올리기
            Integer score = Integer.parseInt(score_robot.getText().toString());
            score += 1;
            score_robot.setText(score.toString());
        }
        //기회 차감하기
        isGameOver();
    }

    private boolean isGameOver() {
        Integer cnt = Integer.parseInt(remainCnt.getText().toString()) - 1;
        remainCnt.setText(cnt.toString());
        String msg = "";

        if(cnt==0){
            int userScore = Integer.parseInt(score_user.getText().toString());
            int robotScore = Integer.parseInt(score_robot.getText().toString());

            if(userScore > robotScore){//승
                msg = "축하합니다.";
            }else if(userScore < robotScore){//패
                msg = "다음에는 꼭...";
            }else{//무승부
                msg = "비겼습니다.";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("게임오버");
            builder.setMessage(msg);
            builder.setPositiveButton("새 게임", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    remainCnt.setText("10");
                    score_robot.setText("0");
                    score_user.setText("0");
                    img_user_select.setImageResource(R.drawable.img_empty);
                    img_robot_select.setImageResource(R.drawable.img_empty);
                }
            });

            builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
        }
        //다이얼로그 띄우기

        return false;
    }

    private void shakeGifImage(ImageView imgView, int img_res) throws IOException {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            Drawable decodedImg = ImageDecoder.decodeDrawable(ImageDecoder.createSource(getResources(), img_res));

            imgView.setImageDrawable(decodedImg);

            AnimatedImageDrawable ani = (AnimatedImageDrawable) decodedImg;
            ani.setRepeatCount(1);
            ani.start();
        }

    }
}