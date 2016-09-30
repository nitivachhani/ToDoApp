package com.example.nitipatel.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditText2 = (EditText) findViewById(R.id.etEditText2);
        String toDoItem = getIntent().getStringExtra("toDoItem");
        etEditText2.setText(toDoItem);
    }

    public void onSave(){
        this.finish();
    }

    public void onSaveItem(View view) {
        String toDoItem = etEditText2.getText().toString();
        //Log.d(this.getClass().getName(), "Result:" + toDoItem);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("toDoItem", toDoItem);
        returnIntent.putExtra("position", getIntent().getIntExtra("position",-1));
        //int code = getIntent().getIntExtra("code", 0);
        returnIntent.putExtra("code", 200);
        setResult(200, returnIntent);
        finish();
    }
}
