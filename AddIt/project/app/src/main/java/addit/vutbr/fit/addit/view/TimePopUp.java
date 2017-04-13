package addit.vutbr.fit.addit.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.lib.ExceptionHandler;

/**
 * Created by Peter on 11/4/2016.
 */

public class TimePopUp extends Activity
{
    // todo find out how to set this constant to proper value
    public static final int TIME_POPUP_FINISHED = 125;
    public static final String SELETECTED_TIME_KEY = "time_result";

    private TimePicker time_picker;
    private Button button_show_time;
    String time;
    Switch switch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set exception handler that prints info to log
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int) (width*.7),(int) (hight*.8));
        showTime();
    }

    public void showTime()
    {
        TextView text = (TextView) findViewById(R.id.textView2);
        time_picker = (TimePicker) findViewById(R.id.simpleTimePicker);
        button_show_time = (Button) findViewById(R.id.button_time);

        button_show_time.setOnClickListener(
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

    }
}