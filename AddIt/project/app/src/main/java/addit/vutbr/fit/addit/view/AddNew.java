package addit.vutbr.fit.addit.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import addit.vutbr.fit.addit.Controller;
import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.databinding.ActivityAddNewBinding;
import addit.vutbr.fit.addit.lib.RealmFind;
import addit.vutbr.fit.addit.model.State;
import addit.vutbr.fit.addit.model.Task;
import io.realm.Realm;
import io.realm.RealmList;

import static addit.vutbr.fit.addit.view.MainPage.ADD_OR_EDIT;
import static addit.vutbr.fit.addit.view.MainPage.TASK_KEY;
import static java.lang.Integer.parseInt;

public class AddNew extends AddItBaseActivity {
    public static final String TAG = AddNew.class.getSimpleName();
    // object which is used in data binding
    private ActivityAddNewBinding binding;
    private Task taskInDtb = null;
    private List<Task> subtasks = null;

    // defines whether we are creating a new task or editing existing one
    private boolean isEditing;
    private static final int DEFAULT_KEY = -1;
    private TextView textDate;
    private TextView textTime;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    String bindPriority;
    private Switch timeSwitch;
    private Switch dateSwitch;

    private LinearLayout subtasksLayout;
    private Button addSubtaskButton;

    private int counter = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Task.NonDtbTask toBind;
        // try to get the id of task to edit
        Intent intent = getIntent();
        int primaryKey = intent.getIntExtra(TASK_KEY, DEFAULT_KEY);
        if (primaryKey != DEFAULT_KEY) {
            isEditing = true;
            taskInDtb = RealmFind.getTaskWithKey(Realm.getDefaultInstance(), Task.class, primaryKey);
            toBind = taskInDtb.createNonDTBCopy();
            Toast.makeText(this, getString(R.string.editingExistingTask) + toBind.getName(), Toast.LENGTH_LONG).show();
        } else {
            isEditing = false;
            toBind = new Task.NonDtbTask();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new);
        binding.setTask(toBind);

        subtasks = new ArrayList<>(toBind.getSubtask());
        SpinnerPriority a = new SpinnerPriority();

        subtasksLayout = (LinearLayout) findViewById(R.id.subtasksLayout);
        addSubtaskButton = (Button) findViewById(R.id.buttonAddSubtask);

        timeSwitch = (Switch) findViewById(R.id.switch1);
        timeSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textTime = (TextView) findViewById(R.id.switch1TextTime);
                    textTime.setVisibility(View.VISIBLE);
                    startActivityForResult(new Intent(AddNew.this, TimePopUp.class), TimePopUp.TIME_POPUP_FINISHED);
                } else {
                    textTime = (TextView) findViewById(R.id.switch1TextTime);
                    textTime.setVisibility(View.INVISIBLE);
                }
            }
        });

        dateSwitch = (Switch) findViewById(R.id.switch2);
        dateSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textTime = (TextView) findViewById(R.id.switch2TextDate);
                    textTime.setVisibility(View.VISIBLE);
                    startActivityForResult(new Intent(AddNew.this, DatePopUp.class), DatePopUp.DATE_POPUP_FINISHED);
                } else {
                    textTime = (TextView) findViewById(R.id.switch2TextDate);
                    textTime.setVisibility(View.INVISIBLE);
                }
            }
        });

        taskNameEditText = (EditText) findViewById(R.id.editTextTaskName);
        taskDescriptionEditText = (EditText) findViewById(R.id.editTextTaskDescription);

        taskNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                toBind.setName(editable.toString());
            }
        });

        taskDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                toBind.setDescription(editable.toString());
            }
        });

        textTime = (TextView) findViewById(R.id.switch1TextTime);
        textDate = (TextView) findViewById(R.id.switch2TextDate);

        Button confirm = (Button) findViewById(R.id.buttonConfirm);
        confirm.setText(isEditing ? R.string.edit : R.string.add_it);

        addSubstasksToLayout();
    }

    private void addSubstasksToLayout() {
        if (subtasks != null) {
            for (Task subtask : subtasks) {
                if(subtask.getStateAsEnum() == State.ACTIVE) {
                    LinearLayout linearLayout = inflateSubtaskLinearLayout(subtask);
                    subtasksLayout.addView(linearLayout);
                }
            }
            subtasks.clear();
        }
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        TextView text1 = (TextView) findViewById(R.id.textView3);
        TextView text2 = (TextView) findViewById(R.id.textView4);
        TextView text3 = (TextView) findViewById(R.id.textView5);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        Switch switch2 = (Switch) findViewById(R.id.switch2);
        Button btn = (Button) findViewById(R.id.buttonPr);

        if (checkBox.isChecked()) {
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
            text3.setVisibility(View.VISIBLE);
            taskNameEditText.setVisibility(View.VISIBLE);
            taskDescriptionEditText.setVisibility(View.VISIBLE);
            switch1.setVisibility(View.VISIBLE);
            switch2.setVisibility(View.VISIBLE);
            textDate.setVisibility(View.VISIBLE);
            textTime.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            addSubtaskButton.setVisibility(View.VISIBLE);
            subtasksLayout.setVisibility(View.VISIBLE);
        } else {
            text1.setVisibility(View.INVISIBLE);
            text2.setVisibility(View.INVISIBLE);
            text3.setVisibility(View.INVISIBLE);
            taskNameEditText.setVisibility(View.VISIBLE);
            taskDescriptionEditText.setVisibility(View.INVISIBLE);
            switch1.setVisibility(View.INVISIBLE);
            switch2.setVisibility(View.INVISIBLE);
            textDate.setVisibility(View.INVISIBLE);
            textTime.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
            addSubtaskButton.setVisibility(View.INVISIBLE);
            subtasksLayout.setVisibility(View.INVISIBLE);
        }
    }


    public void onAddSubtaskLayout(View view) {
        LinearLayout linearLayout = inflateSubtaskLinearLayout(null);
        subtasksLayout.addView(linearLayout);
    }

    @NonNull
    private LinearLayout inflateSubtaskLinearLayout(@Nullable Task task) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(this);
        textView.setText(counter++ + ".");
        linearLayout.addView(textView);
        View view;
        Button deleteButton = null;
        Button doneButton = null;
        if (task != null) {
            // add name
            Button taskDescription = new Button(this);
            taskDescription.setText(task.getName());
            taskDescription.setTextSize(20);
            view = taskDescription;

            //add on click listester
            taskDescription.setOnClickListener(t -> {
                Intent intent1 = new Intent(this, AddNew.class);
                intent1.putExtra(TASK_KEY, task.getPrimaryKey());
                startActivityForResult(intent1, ADD_OR_EDIT);
                overridePendingTransition  (R.anim.slide_in_up, R.anim.slide_out_up);
            });

            doneButton = new Button(this);
            doneButton.setText(getString(R.string.done));
            doneButton.setTextColor(Color.WHITE);
            doneButton.setBackgroundColor(Color.parseColor("#ff6699"));
            doneButton.setOnClickListener(v->{
//            lsadkfjdsljfdklj sadkljfoewjklewfjefwoijfeoijguhugihigrhueihreihureihruihuiqehwuiwehwequihfeuiewfuiefhwefih when i was a little boy, I was used to going jogging in o local park
//                    one day I saw a big owl. It was so beautiful that I simpy stopped and stared at it. My hearth was filled with love.
                subtasksLayout.removeView(linearLayout);
                Controller.getInstance().markTaskAsFinished(task);
            });

            deleteButton = new Button(this);
            deleteButton.setText(getString(R.string.delete));
            deleteButton.setTextColor(Color.WHITE);
            deleteButton.setBackgroundColor(Color.parseColor("#ff4081"));
            deleteButton.setOnClickListener(v->{
//            lsadkfjdsljfdklj sadkljfoewjklewfjefwoijfeoijguhugihigrhueihreihureihruihuiqehwuiwehwequihfeuiewfuiefhwefih when i was a little boy, I was used to going jogging in o local park
//                    one day I saw a big owl. It was so beautiful that I simpy stopped and stared at it. My hearth was filled with love.
                subtasksLayout.removeView(linearLayout);
                Controller.getInstance().markTaskAsDeleted(task);
            });

        } else {
            EditText editText = new EditText(this);
            editText.setMinEms(10);
            editText.requestFocus();
            editText.setTextSize(20);
            view = editText;


            deleteButton = new Button(this);
            deleteButton.setText(getString(R.string.remove));
            deleteButton.setTextColor(Color.WHITE);
            deleteButton.setBackgroundColor(Color.parseColor("#ff4081"));
            deleteButton.setOnClickListener(v->{
//            lsadkfjdsljfdklj sadkljfoewjklewfjefwoijfeoijguhugihigrhueihreihureihruihuiqehwuiwehwequihfeuiewfuiefhwefih when i was a little boy, I was used to going jogging in o local park
//                    one day I saw a big owl. It was so beautiful that I simpy stopped and stared at it. My hearth was filled with love.
                subtasksLayout.removeView(linearLayout);
            });
        }


        view.setMinimumHeight(10);
        linearLayout.addView(view);
        if(doneButton != null)
            linearLayout.addView(doneButton);
        if(deleteButton != null)
            linearLayout.addView(deleteButton);

        return linearLayout;
    }

    public void addButtonClicked(View view) {
        Task.NonDtbTask task = binding.getTask();
        task.setName(taskNameEditText.getText().toString());
        task.setDescription(taskDescriptionEditText.getText().toString());
        task.setPriority(bindPriority);
        Controller controller = Controller.getInstance();

        getNewTasks();

        if (isEditing) {
            // edit existing task, it means replace the old one with new one
            controller.updateTask(task,subtasks);
            Toast.makeText(this, getString(R.string.task) + task.getName() + " " + getString(R.string.editedSuccessfully), Toast.LENGTH_SHORT).show();
        } else {
            Task emptyTask = controller.createEmptyTask();
            task.setPrimaryKey(emptyTask.getPrimaryKey());
            task.setIsRoot(true);
            controller.updateTask(task,subtasks);
            Toast.makeText(this, getString(R.string.newTask) + task.getName() + getString(R.string.createdSuccessfully), Toast.LENGTH_SHORT).show();
        }
//        Intent intent = new Intent();
//        intent.putExtra(SELECTED_DATE_KEY,selectedDate);
//        setResult(DATE_POPUP_FINISHED,intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    private void getNewTasks() {
        Controller instance = Controller.getInstance();
        for(int i = 0 ; i < subtasksLayout.getChildCount(); i++){
            LinearLayout child = ((LinearLayout) subtasksLayout.getChildAt(i));
            View childAt = child.getChildAt(1);
            if(childAt instanceof EditText){
                Task tmp = instance.createEmptyTask();
                Task.NonDtbTask tsk = new Task.NonDtbTask();
                tsk.setName(((EditText) childAt).getText().toString());
                tsk.setPrimaryKey(tmp.getPrimaryKey());
                tsk.setIsRoot(false);
                instance.updateTask(tsk);
                subtasks.add(tmp);
            }
        }
    }

    public void buttonClickFunction(View v) {
        startActivityForResult(new Intent(AddNew.this, SpinnerPriority.class), SpinnerPriority.SPINNER_FINISHED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
        {
            Switch sw1 = (Switch) findViewById(R.id.switch1);
            Switch sw2 = (Switch) findViewById(R.id.switch2);
            sw1.setChecked(false);
            sw2.setChecked(false);
            return;
        }

        switch (requestCode) {
            case SpinnerPriority.SPINNER_FINISHED:
                String[] split2 = data.getStringExtra(SpinnerPriority.SELECTED_SPINNER_KEY).split(":");
                Button btn = (Button) findViewById(R.id.buttonPr);
                String str = Arrays.toString(split2);
                if (str.equals("[1]")) {
                    bindPriority = "green";
                    btn.setText("Low Priority");
                    btn.setBackgroundColor(Color.GREEN);
                } else if (str.equals("[2]")) {
                    bindPriority = "orange";
                    btn.setText("Medium Priority");
                    btn.setBackgroundColor(Color.parseColor("#FFA500"));
                } else if (str.equals("[3]")) {
                    bindPriority = "red";
                    btn.setText("High Priority");
                    btn.setBackgroundColor(Color.RED);
                } else {
                    btn.setBackgroundColor(Color.parseColor("#1f000000"));
                    btn.setText("SET PRIORITY");
                }
                break;

            case TimePopUp.TIME_POPUP_FINISHED:
                String[] split = data.getStringExtra(TimePopUp.SELETECTED_TIME_KEY).split(":");
                Task.NonDtbTask task = binding.getTask();
                try {
                    task.setHour(parseInt(split[0].trim()));
                    task.setMinute(parseInt(split[1].trim()));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case DatePopUp.DATE_POPUP_FINISHED:
                String[] split1 = data.getStringExtra(DatePopUp.SELECTED_DATE_KEY).split("/");
                Task.NonDtbTask task1 = binding.getTask();
                try {
                    task1.setDate(new Date(parseInt(split1[2].trim()), parseInt(split1[1].trim()), parseInt(split1[0].trim())));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case ADD_OR_EDIT:
                Intent intent = new Intent(this,AddNew.class);
                startActivity(intent);
                overridePendingTransition  (R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
                break;
            default:
                Log.d(TAG, getString(R.string.onActivatyResult) + requestCode);
        }
    }
}
