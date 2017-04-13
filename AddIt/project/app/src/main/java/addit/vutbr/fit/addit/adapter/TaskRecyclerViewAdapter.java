package addit.vutbr.fit.addit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.model.Task;
import addit.vutbr.fit.addit.view.AddNew;
import addit.vutbr.fit.addit.view.MainPage;

/**
 * Adapter which connects data with the RecyclerView
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {
    /**
     * list of tasks to show
     */
    private List<Task> tasks;
    private Activity context;

    public TaskRecyclerViewAdapter(Activity context, List<Task> feedItemList) {
        this.tasks = feedItemList;
        this.context = context;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    /**
     * called for each row, created the layout
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_one_row, null);
        return new TaskViewHolder(view);
    }

    /**
     * called when the given row is about to be displayed
     * @param taskViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        // get task on given index
        Task task = tasks.get(i);

        // set information from task to the layout
        taskViewHolder.taskName.setText(task.getName());
        taskViewHolder.taskPriotrity.setCompoundDrawablesWithIntrinsicBounds(0, 0, task.getPriorityAsDrawable(), 0);
        taskViewHolder.taskDescription.setText(task.getDescription());

        //add click listener
        taskViewHolder.taskName.setOnClickListener((v) -> {
            Intent intent = new Intent(context, AddNew.class);
            intent.putExtra(MainPage.TASK_KEY, task.getPrimaryKey());
            context.startActivityForResult(intent, MainPage.ADD_OR_EDIT);
        });
    }

    @Override
    public int getItemCount() {
        return (null != tasks ? tasks.size() : 0);
    }

    /**
     * Each inflated layout has its holder
     * rows are reused, that is why it is neccasary to set corect values into the layout in onBindViewHolder
     * findViewByID is called only once per view, which saves time
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView taskName;
        protected TextView taskDescription;
        protected TextView taskPriotrity;

        public TaskViewHolder(View view) {
            super(view);
            this.taskPriotrity = (TextView) view.findViewById(R.id.recycler_view_task_priority);
            this.taskName = (TextView) view.findViewById(R.id.recycler_view_task_name);
            this.taskDescription = (TextView) view.findViewById(R.id.recycler_view_task_description);
        }
    }
}