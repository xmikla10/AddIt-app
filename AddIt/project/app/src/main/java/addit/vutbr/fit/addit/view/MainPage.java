package addit.vutbr.fit.addit.view;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ToggleButton;

import addit.vutbr.fit.addit.Controller;
import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.databinding.ActivityMainPageBinding;
import addit.vutbr.fit.addit.model.Task;
import addit.vutbr.fit.addit.viewlib.SwipeDismissTouchListener;

import static java.lang.Integer.parseInt;

/**
 * Main page showing the most important task
 */
public class MainPage extends AddItBaseActivity implements SwipeDismissTouchListener.DismissCallbacks {
    public static final String TAG = MainPage.class.getSimpleName();
    public static final int ADD_OR_EDIT = 333;
    public int check = 0;

    public static final String TASK_KEY = "task";
    private volatile ActivityMainPageBinding binding;
    private View mainTaskView;
    private View allTasksFinishedView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding...
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ToggleButton btn = (ToggleButton) findViewById(R.id.toggleButton);
        btn.setBackgroundColor(Color.parseColor("#ff4081"));

        ToggleButton btn2 = (ToggleButton) findViewById(R.id.toggleButton2);
        btn2.setOnClickListener(view -> {
            Intent intent5 = new Intent(MainPage.this, ListOfAll.class);
            startActivity(intent5);
            overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
        });

        ToggleButton btn3 = (ToggleButton) findViewById(R.id.toggleButton3);
        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(MainPage.this, Other.class);
            startActivity(intent);
            overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
        });
        // now add the swipe to delete stuff
        mainTaskView = findViewById(R.id.mainTaskView);
        // it is neccessary to set OnClickListener, otherwise it does not work
        mainTaskView.setOnClickListener(view1 -> {
        });
        mainTaskView.setOnTouchListener(new SwipeDismissTouchListener(mainTaskView, null, this));

        allTasksFinishedView = findViewById(R.id.allTasksFinishedView);
        showTaskIfAvailable();

    }

    private void showTaskIfAvailable() {
        Controller controller = Controller.getInstance();
        Task task = controller.getMostImportantTask();
        if (task != null) {
            allTasksFinishedView.setVisibility(View.VISIBLE);
            mainTaskView.setVisibility(View.VISIBLE);
            controller.setMainActivityBinding(binding, task);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear_anim);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mainTaskView.startAnimation(animation);
        } else {
            allTasksFinishedView.setVisibility(View.VISIBLE);
            mainTaskView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    public void onAddNew(View view) {
        Intent intent1 = new Intent(this, AddNew.class);
        startActivityForResult(intent1, ADD_OR_EDIT);
        overridePendingTransition  (R.anim.slide_in_up, R.anim.slide_out_up);
    }


    public void onEditTask(View view) {
        Intent intent = new Intent(this, AddNew.class);
        Task taskToEdit = binding.getTask();
        intent.putExtra(TASK_KEY, taskToEdit.getPrimaryKey());
        startActivityForResult(intent, ADD_OR_EDIT);
        overridePendingTransition  (R.anim.slide_in_up, R.anim.slide_out_up);
    }

    public void onDone(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.disappear_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Controller.getInstance().markTaskAsFinished(binding.getTask());
                showTaskIfAvailable();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mainTaskView.startAnimation(animation);
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
            overridePendingTransition  (R.anim.slide_in_down, R.anim.slide_out_down);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean canDismiss(Object token) {
        // we can always dismiss
        return true;
    }

    @Override
    public void onDismiss(View view, Object token) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_task)
                .setMessage(R.string.delete_task_confirm)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    // delete the old and put in the new one
                    Controller controller = Controller.getInstance();
                    controller.markTaskAsDeleted(binding.getTask());
                    showTaskIfAvailable();
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_OR_EDIT:
                // just try to fetch any task from database
                showTaskIfAvailable();
                break;

            default:
                Log.d(TAG, getString(R.string.onActivityResult) + requestCode);
        }
    }
}
