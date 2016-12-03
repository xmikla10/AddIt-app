package com.flatmate.flatmate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Peter on 11/20/2016.
 */

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    Context context;
    int layoutResourceId;
    ToDo data[] = null;

    public ToDoAdapter(Context context, int layoutResourceId, ToDo[] data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ToDoHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ToDoHolder();
            holder.textViewTaskName = (TextView) row.findViewById(R.id.textViewTaskName);
            holder.textViewTime = (TextView) row.findViewById(R.id.textViewTime);
            holder.textViewStatus = (TextView) row.findViewById(R.id.textViewStatus);
            holder.textViewtime2 = (TextView) row.findViewById(R.id.textViewtime2);

            row.setTag(holder);
        }
        else
        {
            holder = (ToDoHolder)row.getTag();
        }

        ToDo todo = data[position];
        holder.textViewTaskName.setText(todo.work);
        holder.textViewTime.setText(todo.deadl);
        holder.textViewStatus.setText("auctioning");
        holder.textViewtime2.setText(todo.time);

        return row;
    }

    static class ToDoHolder
    {
        TextView textViewTaskName;
        TextView textViewTime;
        TextView textViewStatus;
        TextView textViewtime2;
    }
}