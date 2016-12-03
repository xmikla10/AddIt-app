package com.flatmate.flatmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.flatmate.flatmate.lib.ExceptionHandler;

/**
 * Created by Peter on 11/4/2016.
 */

public class BidPopUp extends Activity
{
    /*public static final int TIME_POPUP_FINISHED = 125;
    public static final String SELETECTED_TIME_KEY = "time_result";

    private TimePicker time_picker;
    private Button button_time;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set exception handler that prints info to log
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        setContentView(R.layout.popup_bid);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int) (width*.9),(int) (hight*.3));

        Button plus = (Button) findViewById(R.id.buttonplusBid);
        Button btnbid = (Button) findViewById(R.id.button_bid);
        Button minus = (Button) findViewById(R.id.buttonminusBid);
        CheckBox checkboxbid = (CheckBox) findViewById(R.id.checkBoxBid);

        //final EditText duraction = (EditText) findViewById(R.id.editTextDuraction);

        btnbid.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        plus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                EditText bid = (EditText) findViewById(R.id.editTextBid);
                String b =bid.getText().toString();
                int c = Integer.parseInt(b);
                if ( c < 10)
                    c = c + 1;
                else
                    c = c + 2;
                String a = Integer.toString(c);
                bid.setText(a);
            }
        });
        minus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                EditText bid = (EditText) findViewById(R.id.editTextBid);
                String b = bid.getText().toString();
                int c = Integer.parseInt(b);
                if ( c <= 10)
                    c = c - 1;
                else
                    c = c - 2;

                if ( c < 0)
                    c = 0;
                String a = Integer.toString(c);
                bid.setText(a);
            }
        });

        checkboxbid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    EditText bid = (EditText) findViewById(R.id.editTextBid);
                    bid.setText("");
                }
                else
                {
                    EditText bid = (EditText) findViewById(R.id.editTextBid);
                    bid.setText("0");
                }
            }
        });
       // showTime();
    }

    /*public void showTime()
    {
        time_picker = (TimePicker) findViewById(R.id.simpleTimePicker);
        button_time = (Button) findViewById(R.id.button_time);

        button_time.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        String selectedTime = time_picker.getCurrentHour() +" : " + String.format("%02d",time_picker.getCurrentMinute());
                        Intent intent = new Intent();
                        intent.putExtra(SELETECTED_TIME_KEY,selectedTime);
                        setResult(TIME_POPUP_FINISHED,intent);
                        finish();
                    }
                }
        );
    }*/
}
