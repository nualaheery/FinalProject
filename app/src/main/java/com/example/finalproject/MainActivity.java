package com.example.finalproject;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView teacherImg;
    ImageView pupilImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        teacherImg = (ImageView)findViewById(R.id.teacherImg);
        pupilImg = (ImageView)findViewById(R.id.pupilImg);


    }

    public void goToTeacherSite(View v) {
      /*  Intent intent = new Intent(this, TeacherHome.class);
        startActivity(intent); */

      TeacherSiteEntryPin teacherSiteEntryPin = new TeacherSiteEntryPin();
        teacherSiteEntryPin.show(getSupportFragmentManager(),null);
    }

    public void goToPupilSite(View v) {
        Intent intent = new Intent(this,PupilHome.class);
        startActivity(intent);
    }

}
