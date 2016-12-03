package addit.vutbr.fit.addit.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import addit.vutbr.fit.addit.Controller;
import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.adapter.TaskRecyclerViewAdapter;
import addit.vutbr.fit.addit.model.State;

import static addit.vutbr.fit.addit.view.MainPage.ADD_OR_EDIT;

/**
 * Created by Peter on 11/10/2016.
 */

public class ListOfAll extends AppCompatActivity {
    public static final int LIST_OF_ALL = 555;
    public static final String TAG = ListOfAll.class.getSimpleName();

    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_all);

        // get the recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //set layout manager, default linear manager is enough for us
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // load the data
        loadDataIntoAdapter();

        ToggleButton btn = (ToggleButton) findViewById(R.id.toggleButton);
        btn.setOnClickListener(view -> {
            Intent intent5 = new Intent(ListOfAll.this, MainPage.class);
            startActivity(intent5);
            overridePendingTransition  (R.anim.left_slide_in, R.anim.left_slide_out);
        });

        ToggleButton btn2 = (ToggleButton) findViewById(R.id.toggleButton2);
        btn2.setBackgroundColor(Color.parseColor("#ff4081"));

        ToggleButton btn3 = (ToggleButton) findViewById(R.id.toggleButton3);
        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(ListOfAll.this,Other.class);
            startActivity(intent);
            overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
        });
    }

    /**
     * gets all taks from controller, creates adapter and adss it to the recyclerview
     */
    private void loadDataIntoAdapter() {
        // todo optimize if neccessary
        adapter = new TaskRecyclerViewAdapter(this,Controller.getInstance().getAllTasks(State.ACTIVE));
        recyclerView.setAdapter(adapter);
    }

    /**
     * callback from FAB, starts addNewActivity
     * @param view
     */
    public void onAddNew(View view) {
        Intent intent1 = new Intent(this, AddNew.class);
        startActivityForResult(intent1, LIST_OF_ALL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
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

    /**
     * called after addNewActivityFinished
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_OR_EDIT:
                // reload the tasks(if the addNewActivity was successful, there is a new task in the DTB, we have to get him :)
                loadDataIntoAdapter();
                adapter.notifyDataSetChanged();
                break;

            default:
                Log.d(TAG, getString(R.string.onActivityResult) + requestCode);
        }
    }
}
