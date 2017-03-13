package com.linjiawei.databindingtestdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linjiawei.databindingtestdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button callPhoneBtn, readSDCardBtn;
    ActivityMainBinding mainBinding;
    Student student;

    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        student = new Student("小明","男");
        mainBinding.setStuInfo(student);

        callPhoneBtn = (Button) findViewById(R.id.callPhoneBtn);
        readSDCardBtn = (Button) findViewById(R.id.readCard);
        callPhoneBtn.setOnClickListener(this);
        readSDCardBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == callPhoneBtn.getId()) {
            callPhoneCheck();
            student.setName("小明"+count);
            count++;
            mainBinding.setStuInfo(student);
        } else if (v.getId() == readSDCardBtn.getId()) {
            writeSDCardCheck();
        }
    }

    private void callPhoneCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            //已经有权限
            callPhone();
        }
    }

    private void callPhone() {
        Toast.makeText(this, "打电话", Toast.LENGTH_SHORT).show();
    }


    private void writeSDCardCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //还没开启权限
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            writeSDCade();
        }
    }

    private void writeSDCade() {
        Toast.makeText(this, "写入数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    Toast.makeText(this, "打电话权限没开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeSDCade();
                } else {
                    Toast.makeText(this, "写入权限没开启", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
