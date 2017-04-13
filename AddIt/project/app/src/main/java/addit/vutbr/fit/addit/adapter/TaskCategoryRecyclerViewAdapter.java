package addit.vutbr.fit.addit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import addit.vutbr.fit.addit.Controller;
import addit.vutbr.fit.addit.R;
import addit.vutbr.fit.addit.model.State;

/**
 * Adapter which connects data with the RecyclerView
 */
public class TaskCategoryRecyclerViewAdapter extends RecyclerView.Adapter<TaskCategoryRecyclerViewAdapter.TaskViewHolder> {
    /**
     * list of catogories, each is characterized by task's state
     */
    private List<State> states;
    private final Activity context;

    private int counter = 0;

    public TaskCategoryRecyclerViewAdapter(List<State> states,Activity context) {
        this.states = states;
        this.context = context;
    }

    /**
     * called for each row, created the layout
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_one_row_recycler, null);
        State state = states.get(counter++);
        if(counter == states.size())
            counter = 0;
        return new TaskViewHolder(view,state);
    }

    /**
     * called when the given row is about to be displayed
     * @param taskViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        // get task on given index
        State state = states.get(i);

        // set information from task to the layout
        taskViewHolder.categoryName.setText(state.toString());
        taskViewHolder.categoryName.setOnClickListener((v)->{
            int visibility = taskViewHolder.categoryContent.getVisibility();
            if(visibility == View.VISIBLE)
                taskViewHolder.categoryContent.setVisibility(View.GONE);
            else
                taskViewHolder.categoryContent.setVisibility(View.VISIBLE);
        });
        taskViewHolder.reload();
    }

    @Override
    public int getItemCount() {
        return (null != states ? states.size() : 0);
    }

    /**
     * Each inflated layout has its holder
     * rows are reused, that is why it is neccasary to set corect values into the layout in onBindViewHolder
     * findViewByID is called only once per view, which saves time
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView categoryName;
        protected RecyclerView categoryContent;
        protected TaskRecyclerViewAdapter adapter;
        protected State state;

        public TaskViewHolder(View view,State state) {
            super(view);
            this.state = state;
            this.categoryName = (TextView) view.findViewById(R.id.recycler_task_category_name);
            this.categoryContent = (RecyclerView) view.findViewById(R.id.recycler_view_task_category_content);
            this.adapter = new TaskRecyclerViewAdapter(context,Controller.getInstance().getAllTasks(state));
            this.categoryContent.setLayoutManager(new LinearLayoutManager(context));
            this.categoryContent.setAdapter(adapter);
        }

        public void reload(){
            this.adapter.setTasks(Controller.getInstance().getAllTasks(state));
            this.adapter.notifyDataSetChanged();
        }
    }
}