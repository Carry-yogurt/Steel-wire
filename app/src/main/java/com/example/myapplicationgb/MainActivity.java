package com.example.myapplicationgb;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private ImageButton tabmingxi;
    private ImageButton tabadd;
    private ImageButton tabjizhang;
    private ImageButton tabUser;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        checkPermission();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //将主页添加到第一个fragment中，即默认fragment
        Fragment fragment_index = new FirstFragment();
        transaction.add(R.id.fragment_container,fragment_index);
        transaction.commit();
    }

    private void checkPermission() {

        int readExStoragePermissionRest = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExStoragePermissionRest!= PackageManager.PERMISSION_GRANTED){
            //没有权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

        }
    }

    private void initView() {
        tabmingxi = (ImageButton)this.findViewById(R.id.txt_mingxi);
        tabadd = (ImageButton)this.findViewById(R.id.txt_add);
        tabjizhang= (ImageButton)this.findViewById(R.id.txt_jizhang1);
        tabUser=this.findViewById(R.id.txt_user);

        tabmingxi.setOnClickListener(this);
        tabadd.setOnClickListener(this);
        tabjizhang.setOnClickListener(this);
        tabUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        resetImgs();
        switch (v.getId()){
            //通过switch判断用哪个fragment替换当前的fragment
            case R.id.txt_mingxi:
                Fragment firstfragment = new FirstFragment();
                transaction.replace(R.id.fragment_container,firstfragment);
                tabmingxi.setImageResource(R.mipmap.mingxi1);
                //tabmingxi.setImageResource(R.mipmap.mingxi);
                break;
            case R.id.txt_add:
                Fragment erfragment = new erFragment();
                transaction.replace(R.id.fragment_container,erfragment);
                tabadd.setImageResource(R.mipmap.tianjia1);
                break;
            case R.id.txt_jizhang1:
                Fragment sanfragment = new sanFragment();
                transaction.replace(R.id.fragment_container,sanfragment);
                tabjizhang.setImageResource(R.mipmap.a_tubiao);
                break;
            case R.id.txt_user:
                Fragment sifragment = new siFragment();
                transaction.replace(R.id.fragment_container,sifragment);
                tabUser.setImageResource(R.mipmap.women1);
                break;
        }
        transaction.commit();
    }
    private void resetImgs() {
        tabmingxi.setImageResource(R.mipmap.mingxi);
        tabadd.setImageResource(R.mipmap.tianjia);
        tabjizhang.setImageResource(R.mipmap.a_tubiao);
        tabUser.setImageResource(R.mipmap.women);
    }
}
