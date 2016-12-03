package com.flatmate.flatmate.Firebase;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.flatmate.flatmate.CustomAdapter;
import com.flatmate.flatmate.R;
import com.flatmate.flatmate.Tab_TODO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.flatmate.flatmate.Firebase.NewWork;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class FirebaseHelper
{
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<NewWork> a =new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }
    //WRITE
    public Boolean save(NewWork newWork)
    {
        if(newWork == null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("todo").push().setValue(newWork);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }
    //READ
    public ArrayList<NewWork> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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

        return a;
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        a.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            NewWork newWork = ds.getValue(NewWork.class);
            a.add(newWork);

        }
    }

}