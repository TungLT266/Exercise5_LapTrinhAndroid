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


public class UpdateTaskDialog extends DialogFragment {
    TextInputEditText inputName;
    DatePicker datePicker;
    RadioButton high;
    RadioButton normal;
    RadioButton low;
    Button buttonSave;
    Button buttonCancel;

    private static Realm realm;

    private static TodoList oldData;



    public static UpdateTaskDialog newInstance(Realm rootRealm,TodoList rootOldData){
        UpdateTaskDialog dialog = new UpdateTaskDialog();
        realm=rootRealm;
        oldData=rootOldData;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_task_dialog, container,false);
        inputName=view.findViewById(R.id.inputName);
        inputName.setText(oldData.getName());
        datePicker=view.findViewById(R.id.datePicker);
        datePicker.updateDate(oldData.getYear(),oldData.getMonth()-1,oldData.getDate());


        high=view.findViewById(R.id.high); high.setChecked(oldData.getPriority()==1);
        normal=view.findViewById(R.id.normal); normal.setChecked(oldData.getPriority()==2);
        low=view.findViewById(R.id.low); low.setChecked(oldData.getPriority()==3);
        buttonSave=view.findViewById(R.id.buttonSave);
        buttonCancel=view.findViewById(R.id.buttonCancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TodoList data=oldData;
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        data.setName(inputName.getText().toString().trim());
                        data.setDate(datePicker.getDayOfMonth());
                        data.setMonth(datePicker.getMonth()+1);
                        data.setYear(datePicker.getYear());

                        data.setPriority(
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
