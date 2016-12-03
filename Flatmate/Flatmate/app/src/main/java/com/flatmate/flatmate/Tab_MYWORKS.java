package com.flatmate.flatmate;

/**
 * Created by xmikla10 on 16.11.2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab_MYWORKS extends Fragment {

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.content_my_works_layout, container, false);
    }
}