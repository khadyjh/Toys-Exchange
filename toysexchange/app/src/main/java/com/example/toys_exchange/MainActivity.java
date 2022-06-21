package com.example.toys_exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener mClickprofile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mProfile.setText("profile");
            mProfile.setAllCaps(true);

            Intent startAllTasksIntent = new Intent(getApplicationContext(), profileActivity.class);
            startActivity(startAllTasksIntent);

        }
    };

    private TextView mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////*********             Profile Button                **********//////////////////



        Button btnProfile = findViewById(R.id.btn_profile);
        mProfile = findViewById(R.id.btn_profile);

        btnProfile.setOnClickListener(mClickprofile);

    }
}