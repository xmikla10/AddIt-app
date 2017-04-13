package addit.vutbr.fit.addit.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.List;

import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.adapter.TaskCategoryRecyclerViewAdapter;
import addit.vutbr.fit.addit.model.State;

/**
 * Created by david on 11.11.16.
 */
public class Other extends AddItBaseActivity {

    private static final String TAG = Other.class.getSimpleName();
    private static final int OTHER = 777;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_root);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<State> states = Arrays.asList(State.FINISHED,State.DELETED);
        recyclerView.setAdapter(new TaskCategoryRecyclerViewAdapter(states,this));

        ToggleButton btn = (ToggleButton) findViewById(R.id.toggleButton);
        btn.setOnClickListener(view -> {
            Intent intent5 = new Intent(Other.this, MainPage.class);
            startActivity(intent5);
            overridePendingTransition  (R.anim.left_slide_in, R.anim.left_slide_out);
        });

        ToggleButton btn2 = (ToggleButton) findViewById(R.id.toggleButton2);
        btn2.setOnClickListener(view -> {
            Intent intent5 = new Intent(Other.this, ListOfAll.class);
            startActivity(intent5);
            overridePendingTransition  (R.anim.left_slide_in, R.anim.left_slide_out);
        });

        ToggleButton btn3 = (ToggleButton) findViewById(R.id.toggleButton3);
        btn3.setBackgroundColor(Color.parseColor("#ff4081"));
    }

    public void onAddNew(View view) {
        Intent intent1 = new Intent(this, AddNew.class);
        startActivityForResult(intent1, OTHER);
        overridePendingTransition  (R.anim.left_slide_in, R.anim.left_slide_out);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent4 = new Intent(this, AppPreferences.class);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(intent4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
