package com.example.myapplication.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;



import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;



import java.util.ArrayList;
import androidx.core.content.ContextCompat;



import android.Manifest;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements itemClickListener{

    RecyclerView folderRecycler;
    TextView empty;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);

        String temp = "";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_CONTACTS + " ";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " "; }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.ACCESS_COARSE_LOCATION + " ";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.ACCESS_FINE_LOCATION + " ";
        }

        if (TextUtils.isEmpty(temp) == false) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);

        }else {
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
            ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
            fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

            TabLayout tabLayout = findViewById(R.id.tab_layout);
            viewPager.setAdapter(fragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);



        }







    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
            ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
            fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

            TabLayout tabLayout = findViewById(R.id.tab_layout);
            viewPager.setAdapter(fragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    /**
     * Each time an item in the RecyclerView is clicked this method from the implementation of the transitListerner
     * in this activity is executed, this is possible because this class is passed as a parameter in the creation
     * of the RecyclerView's Adapter, see the adapter class to understand better what is happening here
     * @param pictureFolderPath a String corresponding to a folder path on the device external storage
     */
    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {

        /*Intent move = new Intent(MainActivity.this, ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);
        startActivity(move);*/

        ImageDisplay imageDisplay = new ImageDisplay();
        getSupportFragmentManager().beginTransaction().add(R.id.frag_gallery, imageDisplay).addToBackStack(null).commit();
        Bundle bundle = new Bundle();
        bundle.putString("folderPath", pictureFolderPath);
        bundle.putString("folderName", folderName);
        imageDisplay.setArguments(bundle);
    }
}