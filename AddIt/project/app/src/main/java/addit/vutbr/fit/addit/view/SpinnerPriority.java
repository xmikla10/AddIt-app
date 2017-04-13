package addit.vutbr.fit.addit.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;

import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.lib.ExceptionHandler;

/**
 * Created by Peter on 11/4/2016.
 */

public class SpinnerPriority extends Activity
{
    public static final int SPINNER_FINISHED = 127;
    public static final String SELECTED_SPINNER_KEY = "spinner_result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window3);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;
        getWindow().setLayout((int) (width*.7),(int) (hight*.5));
    }

    public void PriorityClicked(View v)
    {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        Intent returnIntent = new Intent();

        if( checkbox1.isChecked())
        {
            returnIntent.putExtra(SELECTED_SPINNER_KEY ,"1");
        }
        else if( checkbox2.isChecked())
        {
            returnIntent.putExtra(SELECTED_SPINNER_KEY ,"2");
        }
        else if( checkbox3.isChecked())
        {
            returnIntent.putExtra(SELECTED_SPINNER_KEY ,"3");
        }
        else
        {
            returnIntent.putExtra(SELECTED_SPINNER_KEY ,"4");
        }
        setResult(SPINNER_FINISHED, returnIntent);
        finish();
    }

    public void  checkIt(View v)
    {
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkbox4.setChecked(true);
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
        PriorityClicked(v);
    }

    public void  checkIt1(View v)
    {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkbox1.setChecked(true);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);
        PriorityClicked(v);
    }
    public void  checkIt2(View v)
    {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkbox2.setChecked(true);
        checkbox1.setChecked(false);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);
        PriorityClicked(v);
    }
    public void  checkIt3(View v)
    {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkbox3.setChecked(true);
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox4.setChecked(false);
        PriorityClicked(v);
    }
}
