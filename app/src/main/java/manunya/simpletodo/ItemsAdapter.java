package manunya.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Manunya on 4/19/2015.
 */
public class ItemsAdapter extends ArrayAdapter<TodoItem>{
    private static final String DEFAULT_DUE_DATE = "No due date";

    public ItemsAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        tvBody.setText(item.getBody());
        if (item.getDueDate() == -1) {
            tvDueDate.setText(DEFAULT_DUE_DATE);
        } else {
            Date due_date = new Date(item.getDueDate());
            tvDueDate.setText("Due: " + DateFormat.getDateInstance().format(due_date));
        }
        return convertView;
    }

}
