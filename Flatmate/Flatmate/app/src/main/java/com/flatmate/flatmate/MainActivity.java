package com.flatmate.flatmate;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.flatmate.flatmate.Firebase.NewWork;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import android.text.TextUtils;
import android.util.Log;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import com.flatmate.flatmate.Firebase.FirebaseHelper;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    TextView testView;
    //This is our tablayout
    private TabLayout tabLayout;
    private LinearLayout newTask;
    //This is our viewPager
    private ViewPager viewPager;

    public String nameR;
    public String durationR;
    public String deadlineR;
    public String timeR;
    public String dateR;

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter2;
    ListView lv;
    EditText workN, durationN, deadlineN, timeN, dateN;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        init();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new Pager(getSupportFragmentManager(),getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

    }

    public void showAuction(View view)
    {
        Intent intent = new Intent(this, AuctionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void showZaire(View view) {
        Intent intent = new Intent(this, AddNewWorkActivity.class);
        startActivityForResult(new Intent(this, AddNewWorkActivity.class), AddNewWorkActivity.ADD_FINISHED);
        //startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void notifPref(View view)
    {
        Intent intent = new Intent(this, AppPreferences.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_settings)
        {
            Intent intent6 = new Intent(this, AppPreferences.class);
            startActivity(intent6);
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        }

        return super.onOptionsItemSelected(item);
    }

    public void init()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                if (id == R.id.nav_to_do)
                {
                    Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else if (id == R.id.nav_activity_graph) {
                    Intent intent4 = new Intent(MainActivity.this, ActivityGraphActivity.class);
                    startActivity(intent4);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                } else if (id == R.id.nav_account) {
                    Intent intent5 = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent5);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                } else if (id == R.id.nav_settings) {
                    Intent intent6 = new Intent(MainActivity.this, AppPreferences.class);
                    startActivity(intent6);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                } else if (id == R.id.nav_about_flatmate) {
                    Intent intent7 = new Intent(MainActivity.this, AboutFlatmateActivity.class);
                    startActivity(intent7);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                } else if (id == R.id.nav_log_out) {
                    //Intent intent7 = new Intent(MainActivity.this, AboutFlatmateActivity.class);
                    //startActivity(intent7);
                    //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AddNewWorkActivity.ADD_FINISHED:
                if ( data == null)
                    return;
                String[] split = data.getStringExtra(AddNewWorkActivity.SELECTED_ADD_KEY).split("-");
                String str = Arrays.toString(split).replace("[", "").replace("]", "");
                dataresultTostrings(str);

                db = FirebaseDatabase.getInstance().getReference();
                helper = new FirebaseHelper(db);
                NewWork newWork = new NewWork();
                newWork.set_work_name(nameR);
                newWork.set_duration(durationR + " min");
                newWork.set_deadline(deadlineR);
                newWork.set_date(dateR);
                newWork.set_time(timeR);
                newWork.set_status("Status : auctioning");
                helper.save(newWork);
                CustomAdapter adapter = new CustomAdapter(this, helper.retrieve());

                break;

            default:
                Log.d(TAG, "onActivityResult: uknown request code " + requestCode);
        }
    }

    public void dataresultTostrings(String str)
    {
        int hashCount = 0;
        char[] charArray = str.toCharArray();
        int size = str.length();
        int x = 0;
        int from = 0;
        boolean bool = false;

        while ( x <= size )
        {
            if ( charArray[x] == '#')
            {
                if( hashCount == 0 && bool == false )
                {
                    char[] charArray1 = Arrays.copyOfRange(charArray, from, x);
                    nameR = new String(charArray1);
                    from = x+1;
                    hashCount = 1;
                    bool = true;
                    x++;
                }
                if( hashCount == 1 && bool == false)
                {
                    char[] charArray2 = Arrays.copyOfRange(charArray, from, x);
                    durationR = new String(charArray2);
                    from = x+1;
                    hashCount = 2;
                    bool = true;
                    x++;
                }
                if( hashCount == 2 && bool == false)
                {
                    char[] charArray3 = Arrays.copyOfRange(charArray, from, x);
                    deadlineR = new String(charArray3);
                    from = x+1;
                    hashCount = 3;
                    bool = true;
                    x++;
                }
                if( hashCount == 3 && bool == false)
                {
                    char[] charArray4 = Arrays.copyOfRange(charArray, from, x);
                    dateR = new String(charArray4);
                    from = x+1;
                    hashCount = 4;
                    bool = true;
                    x++;
                }
                if( hashCount == 4 && bool == false)
                {
                    char[] charArray5 = Arrays.copyOfRange(charArray, from, x);
                    timeR = new String(charArray5);
                    from = x+1;
                    break;
                }
            }
            else
            {
                x++;
                bool = false;
            }
        }

    }
}
