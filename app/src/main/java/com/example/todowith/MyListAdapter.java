package com.example.todowith;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

class ToDo {
    String description;
    boolean selected = false;

    public ToDo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
public class MyListAdapter extends ArrayAdapter<ToDo> {

    private List<ToDo> toDoList;
    private Context context;

    public MyListAdapter(Context context, int textViewResourceId, List<ToDo> toDoList) {
        super(context, R.layout.single_listview_item, toDoList);
        this.toDoList = toDoList;
        this.context = context;
    }

    private static class ToDoHolder {
        public TextView thingToDo;
        public CheckBox chkBox;
    }

    // provides new View for this Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        ToDoHolder holder = new ToDoHolder();

        if(convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listview_item, null);

            holder.thingToDo = (TextView) v.findViewById(R.id.name);
            holder.chkBox = (CheckBox) v.findViewById(R.id.chk_box);
            holder.chkBox.setOnCheckedChangeListener((MainActivity) context);

            v.setTag(holder);
        } else {
            holder = (ToDoHolder) v.getTag();
        }

        ToDo p = toDoList.get(position);
        holder.thingToDo.setText(p.getDescription());
        holder.chkBox.setChecked(p.isSelected());
        holder.chkBox.setTag(p);

        return v;
    }
}
