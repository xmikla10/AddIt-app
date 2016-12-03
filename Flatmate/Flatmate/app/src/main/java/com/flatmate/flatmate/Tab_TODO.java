package com.flatmate.flatmate;

/**
 * Created by xmikla10 on 16.11.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flatmate.flatmate.Firebase.NewWork;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.flatmate.flatmate.Firebase.FirebaseHelper;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab_TODO extends Fragment
{
    private ListView listView1;
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    NewWork newWork;
    ListView lv;
    ArrayList<NewWork> a =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.activity_to_do, container, false);
        newWork = new NewWork();
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        helper.save(newWork);

        adapter = new CustomAdapter(getContext(), helper.retrieve());
        lv = (ListView) rootView.findViewById(R.id.listView1);

        db.addChildEventListener(new ChildEventListener() {

            ProgressBar mProgress= (ProgressBar) rootView.findViewById(R.id.loadingProgressBar);
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mProgress.setVisibility(View.GONE);
                adapter = new CustomAdapter(getContext(), helper.retrieve());
                lv.setAdapter(adapter);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProgress.setVisibility(View.GONE);
                adapter = new CustomAdapter(getContext(), helper.retrieve());
                lv.setAdapter(adapter);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final NewWork s= (NewWork) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), AuctionActivity.class);
                intent.putExtra("work_name", s.get_work_name());
                intent.putExtra("status", s.get_status());
                intent.putExtra("duration", s.get_duration());
                intent.putExtra("deadline", s.get_deadline());
                intent.putExtra("time", ( s.get_time() +"  "+s.get_date()));
                startActivity(intent);
            }

        });

        return rootView;
    }

}