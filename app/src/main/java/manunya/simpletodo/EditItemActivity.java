package manunya.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    EditText etEditItem;
    int pos;
    TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        pos = getIntent().getIntExtra("pos", 0);
        //String itemString = getIntent().getStringExtra("itemString");
        item = (TodoItem) getIntent().getSerializableExtra("item");
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item.getBody());
        etEditItem.setSelection(etEditItem.getText().length());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onSaveItem(View view) {
        Intent data = new Intent();
        //data.putExtra("editItemString", etEditItem.getText().toString());
        TodoItem updatedItem = new TodoItem(etEditItem.getText().toString());
        updatedItem.setId(item.getId());
        data.putExtra("pos", pos);
        data.putExtra("item", updatedItem);
        setResult(RESULT_OK, data);
        finish();
    }
}
