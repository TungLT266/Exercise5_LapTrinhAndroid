package tunglt.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class CustomAdapter extends ArrayAdapter<TodoList> {
    private RealmResults<TodoList> todoLists;
    Context mContext;
    android.support.v4.app.FragmentManager fm;
    Realm realm;

    public CustomAdapter(RealmResults<TodoList> todoList, Context context,android.support.v4.app.FragmentManager fm,Realm rootRealm){
        super(context,R.layout.row_item,todoList);
        this.todoLists=todoList;
        this.mContext=context;
        this.fm=fm;
        this.realm=rootRealm;
    }



    public static class ViewHolder{
        TextView name;
        TextView dueTo;
        TextView priority;
    }



    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        final TodoList todoList = getItem(position);
        ViewHolder viewHolder;



        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.dueTo = (TextView) convertView.findViewById(R.id.dueTo);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.priority);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(todoList.getName());
        viewHolder.dueTo.setText("Due to "+todoList.getDate()+"/"+todoList.getMonth()+"/"+todoList.getYear());
        viewHolder.priority.setText( (todoList.getPriority()==1)? "High" : ((todoList.getPriority()==2)?"Normal":"Low") );
        viewHolder.priority.setTextColor((todoList.getPriority()==1)? Color.RED : ((todoList.getPriority()==2)?Color.YELLOW:Color.GREEN));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence action[] = new CharSequence[] {"Done","Edit"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(todoList.getName());
                builder.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if(which==0) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    DoneList doneList=realm.createObject(DoneList.class);
                                    doneList.setName(todoList.getName());
                                    doneList.setDate(todoList.getDate());
                                    doneList.setMonth(todoList.getMonth());
                                    doneList.setYear(todoList.getYear());
                                    doneList.setPriority(todoList.getPriority());
                                    todoList.deleteFromRealm();
                                    TodoListFragment.adapter.notifyDataSetChanged();
                                    DoneFragment.adapter.notifyDataSetChanged();

                                }
                            });
                        }
                        else {
                            UpdateTaskDialog updateTaskDialog=UpdateTaskDialog.newInstance(realm,todoList);
                            updateTaskDialog.show(fm,null);
                        }

                    }
                });
                builder.show();

            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Warning")
                        .setMessage("Are you sure this is not an accident ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("No,it 's not", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        todoList.deleteFromRealm();
                                    }
                                });
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton("Oops,undo this for me", null).show();
                return true;
            }
        });
        return convertView;


    }

}
