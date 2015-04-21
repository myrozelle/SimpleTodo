package manunya.simpletodo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

//import android.app.DialogFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {#@link EditItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends DialogFragment {
    private static final String TITLE = "Edit Item";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POS = "pos";
    private static final String KEY_ITEM = "item";

    EditText etEditItem;
    Button btnSaveEditItem;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EditItemFragment.
     */
    public static EditItemFragment newInstance(int pos, TodoItem item) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, TITLE);
        args.putInt(KEY_POS, pos);
        args.putSerializable(KEY_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    public EditItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        String title = getArguments().getString(KEY_TITLE);
        getDialog().setTitle(title);
        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        final TodoItem item = (TodoItem) getArguments().getSerializable(KEY_ITEM);
        etEditItem.setText(item.getBody());
        etEditItem.setSelection(etEditItem.getText().length());
        // Show soft keyboard automatically
        etEditItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        btnSaveEditItem = (Button) view.findViewById(R.id.btnSaveEditItem);
        btnSaveEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onReceiveEditItem(getArguments().getInt(KEY_POS), etEditItem.getText().toString());
            getDialog().dismiss();
            }
        });
        Button btnLaunchDatePicker = (Button) view.findViewById(R.id.btnLaunchDatePicker);
        btnLaunchDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onLaunchDatePicker(getArguments().getInt(KEY_POS));
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public interface EditItemDialogListener {
        void onReceiveEditItem(int pos, String body);
        void onLaunchDatePicker(int pos);
    }
}
