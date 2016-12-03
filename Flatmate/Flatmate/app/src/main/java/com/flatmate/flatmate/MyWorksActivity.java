package com.flatmate.flatmate;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * Created by xmikla10 on 29.10.2016.
 */

public class MyWorksActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works_layout);

        ToggleButton btn = (ToggleButton) findViewById(R.id.toggleButton2);
        btn.setBackgroundColor(Color.parseColor("#ffffbb33"));
        btn.setTextColor(Color.parseColor("#000000"));

        ToggleButton btn1 = (ToggleButton) findViewById(R.id.toggleButton3);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent intent = new Intent(MyWorksActivity.this, CompletedActivity.class);
                startActivity(intent);
                overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });

        ToggleButton btn2 = (ToggleButton) findViewById(R.id.toggleButton);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent intent = new Intent(MyWorksActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition  (R.anim.left_slide_in, R.anim.left_slide_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
}
