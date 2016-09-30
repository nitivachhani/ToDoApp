package com.example.nitipatel.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> toDoItems;
    ArrayAdapter<String> toDoItemsAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        //setTitle("Hello Niti");
        lvItems = (ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(toDoItemsAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                toDoItemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditItemView(position);
            }
        });

    }

    public void populateArrayItems(){
        toDoItems = new ArrayList<String>();
        //toDoItems.add("Item 1");
        //toDoItems.add("Item 2");
        //toDoItems.add("Item 3");
        readItems();
        toDoItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            toDoItems = new ArrayList<>(FileUtils.readLines(file));
        }catch (IOException e){
            Log.e("MainActivity", "File read Error" + e.getMessage());
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, toDoItems);
        }catch (IOException e){
            Log.e("MainActivity", "File write Error" + e.getMessage());
        }
    }

    public void onAddItem(View view) {
        toDoItemsAdapter.add(etEditText.getText().toString());
        writeItems();
        etEditText.setText("");
    }

    public void launchEditItemView(int position) {
        String toDoItem = toDoItems.get(position);
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("toDoItem", toDoItem);
        i.putExtra("position", position);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (resultCode == 200) {
            String modifiedToDoItem = resultIntent.getStringExtra("toDoItem");
            int position = resultIntent.getIntExtra("position", -1);

            toDoItems.set(position, modifiedToDoItem);
            writeItems();
            toDoItemsAdapter.notifyDataSetChanged();
        }
    }
}
