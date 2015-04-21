package manunya.simpletodo;

//import android.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

//import android.support.v4.app.FragmentManager;


public class MainActivity extends ActionBarActivity implements EditItemFragment.EditItemDialogListener, DatePickerFragment.DatePickerDialogListener {
    //ArrayList<String> items;
    ArrayList<TodoItem> items;
    //ArrayAdapter<String> itemsAdapter;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    TodoItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        db = new TodoItemDatabase(this);
        //db.fix();
        items = db.getAllTodoItems();

        // Create the adapter to convert the array to views
        itemsAdapter = new ItemsAdapter(this, items);

        //readItems(); // upgrade to use database
        //readDatabase();
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //String editItemString = data.getExtras().getString("editItemString");
            int pos = data.getExtras().getInt("pos");
            TodoItem item = (TodoItem) data.getSerializableExtra("item");
            items.set(pos, item);
            itemsAdapter.notifyDataSetChanged();
            //writeItems();
            db.updateTodoItem(item);
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
            new ListView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                    db.deleteTodoItem(items.get(pos));
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    //writeItems();
                    return true;
                }
            }
        );
        lvItems.setOnItemClickListener(
            new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                    /*Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    i.putExtra("item", items.get(pos));
                    i.putExtra("pos", pos);
                    //startActivity(i);
                    startActivityForResult(i, REQUEST_CODE);
                    */
                    showEditDialog(pos, items.get(pos));
                    return;
                }

                private void  showEditDialog(int pos, TodoItem item) {
                    FragmentManager fm = getSupportFragmentManager();
                    EditItemFragment editItemFragment = EditItemFragment.newInstance(pos, item);
                    editItemFragment.show(fm, "fragment_edit_item");
                }
            }
        );

    }

    /*
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }
    */
    /*private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @Override
    public void onReceiveEditItem(int pos, String body) {
        TodoItem item = (TodoItem) items.get(pos);
        item.setBody(body);
        itemsAdapter.notifyDataSetChanged();
        db.updateTodoItem(item);
    }

    @Override
    public void onLaunchDatePicker(int pos) {
        FragmentManager fm = getSupportFragmentManager();
        TodoItem item = (TodoItem) items.get(pos);
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(this, pos, item);
        datePickerFragment.show(fm, "");
    }

    @Override
    public void onDateSet(int pos, long date) {
        TodoItem item = (TodoItem) items.get(pos);
        item.setDueDate(date);
        //itemsAdapter.notifyDataSetChanged();
        db.updateTodoItem(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemString = etNewItem.getText().toString();
        TodoItem newItem = new TodoItem(itemString);
        long id = db.addTodoItem(newItem);
        newItem.setId((int)id);
        items.add(newItem);
        etNewItem.setText("");
        //itemsAdapter.add(itemString);
        //writeItems();
    }

}
