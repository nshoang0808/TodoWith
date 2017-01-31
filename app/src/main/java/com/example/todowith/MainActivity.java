package com.example.todowith;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.todowith.R.id.lvItems;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private List<ToDo> items;
    private MyListAdapter itemsAdapter;
    private ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a list of item
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new MyListAdapter(this,
                R.layout.single_listview_item, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }
    /*
       Handles when click "Add Item" button
     */
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        ToDo item = new ToDo(itemText);
        itemsAdapter.add(item);
        etNewItem.setText("");
        writeItems();
    }

    // Attaches a long click listener to the listview
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                    // Remove the item within array at position
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }

            });
    }
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            List<String> itemsText = new ArrayList<String>(FileUtils.readLines(todoFile));
            items = new ArrayList<ToDo>();
            for (String x: itemsText) {
                items.add(new ToDo(x));
            }
        } catch (IOException e) {
            items = new ArrayList<ToDo>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            List<String> itemsText = new ArrayList<String>();
            for (ToDo x: items) {
                itemsText.add(x.description);
            }
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lvItems.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            ToDo p = items.get(pos);
            p.setSelected(isChecked);

            Toast.makeText(
                    this,
                    "Clicked on Planet: " + p.getDescription() + ". State: is "
                            + isChecked, Toast.LENGTH_SHORT).show();
        }
    }
}
