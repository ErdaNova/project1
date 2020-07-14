package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

public class CallOrMsgSelect extends Activity {

    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_or_msg);

        number =  getIntent().getStringExtra("number");

        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.call_or_msg);
        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                finish();
                return false;
            }
        });

        //전화 걸기
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)) ;
                startActivity(intent);
                finish();
            }

        });

        // 메세지 보내기
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri smsUri = Uri.parse("tel:" + number);
                Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                intent.putExtra("address", number);
                intent.putExtra("sms_body", "");
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);
                finish();
            }
        });

    }

}
