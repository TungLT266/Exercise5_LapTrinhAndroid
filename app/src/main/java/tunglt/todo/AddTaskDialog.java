package tunglt.todo;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;

import io.realm.Realm;
import io.realm.Sort;


public class AddTaskDialog extends DialogFragment {
    TextInputEditText inputName;
    DatePicker datePicker;
//    RadioGroup priority;
    RadioButton high;
    RadioButton normal;
    Button buttonSave;
    Button buttonCancel;
    private static Realm realm;




    public static AddTaskDialog newInstance( Realm rootRealm){
        AddTaskDialog dialog = new AddTaskDialog();
        realm=rootRealm;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_task_dialog, container,false);
        inputName=view.findViewById(R.id.inputName);
        datePicker=view.findViewById(R.id.datePicker);
//        datePicker.updateDate(2000,1,1);
        high=view.findViewById(R.id.high);
        normal=view.findViewById(R.id.normal);
        buttonSave=view.findViewById(R.id.buttonSave);
        buttonCancel=view.findViewById(R.id.buttonCancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        TodoList newTodoList=realm.createObject(TodoList.class);


                        newTodoList.setName(inputName.getText().toString().trim());
                        newTodoList.setDate(datePicker.getDayOfMonth());
                        newTodoList.setMonth(datePicker.getMonth()+1);
                        newTodoList.setYear(datePicker.getYear());

                        newTodoList.setPriority(
                                (high.isChecked()==true)?1:
                                        ((normal.isChecked()==true)?2:3)
                        );



                    }
                });
                TodoListFragment.todoList.sort("Priority", Sort.ASCENDING);

                TodoListFragment.adapter.notifyDataSetChanged();
                getDialog().dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }


}
