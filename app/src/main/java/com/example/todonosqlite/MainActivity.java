package com.example.todonosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button save;
    EditText date;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHelper helper = new DatabaseHelper(this);
        final ArrayList array_list = helper.getAllContacts();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, array_list);
        date = findViewById(R.id.etNewItem);
        listView = findViewById(R.id.lvItems);
        listView.setAdapter(arrayAdapter);

        findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!date.getText().toString().isEmpty()) {
                    if(dateChecker(date.getText().toString())) {
                        if (helper.insert(date.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "NOT Inserted", Toast.LENGTH_LONG).show();
                        }
                    }else
                        date.setError("Invaild");
                    date.setText("");
                } else {
                    date.setError("Enter Date");
                }
                array_list.clear();
                array_list.addAll(helper.getAllContacts());
                arrayAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();

            }
        });


        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position

                        String str=listView.getItemAtPosition(pos).toString();
                        // Refresh the adapter
                        helper.deleteNote(str);
                        // Return true consumes the long click event (marks it handled)
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                        array_list.clear();
                        array_list.addAll(helper.getAllContacts());
                        arrayAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();
                        return true;

                    }

                });

    }

    boolean dateChecker(String d){
        if(d.length() <10 || d.length()>10)return false;
        try
        {
            if(Integer.parseInt(d.substring(0,2)) < 32 && d.charAt(2)== '/'&&
                Integer.parseInt(d.substring(3,5)) < 13 && d.charAt(5)== '/'&&
                Integer.parseInt(d.substring(6,8)) < 2021){
             return true;
            }
        }

        catch(NumberFormatException e)
        { return false; }
    return false;
    }

}