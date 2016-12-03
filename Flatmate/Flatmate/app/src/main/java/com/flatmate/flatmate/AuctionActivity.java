package com.flatmate.flatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.flatmate.flatmate.R;

/**
 * Created by xmikla10 on 29.10.2016.
 */

public class AuctionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_layout);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String work_name = extras.getString("work_name");
            String status = extras.getString("status");
            String duration = extras.getString("duration");
            String deadline = extras.getString("deadline");
            String time = extras.getString("time");

            TextView work_name1 = (TextView) findViewById(R.id.auctionWorkName);
            TextView status1 = (TextView) findViewById(R.id.auctionStatus);
            TextView duration1 = (TextView) findViewById(R.id.auctionDuration);
            TextView deadline1 = (TextView) findViewById(R.id.auctionDeadline);
            TextView time1 = (TextView) findViewById(R.id.auctionTime);

            work_name1.setText(work_name);
            status1.setText(status);
            duration1.setText(duration);
            deadline1.setText(deadline);
            time1.setText(time);
            // and get whatever type user account id is
        }
    }

    public void showAddBid(View view) {
        Intent intent = new Intent(this, BidPopUp.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
