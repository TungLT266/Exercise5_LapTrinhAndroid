package tunglt.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class TodoListFragment extends Fragment {


    public static RealmResults<TodoList> todoList;
    ListView listView;
    FloatingActionButton buttonAdd;
    public static CustomAdapter adapter;
    public static Realm realm;


    public TodoListFragment() {}

    public static TodoListFragment newInstance(Realm rootRealm){
        TodoListFragment fragment=new TodoListFragment();
        realm=rootRealm;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        listView=(ListView)view.findViewById(R.id.listView);
        todoList=realm.where(TodoList.class).findAll();

        todoList.sort("Priority", Sort.ASCENDING);

        final android.support.v4.app.FragmentManager fm=TodoListFragment.this.getActivity().getSupportFragmentManager();
        adapter= new CustomAdapter(todoList,TodoListFragment.this.getActivity(),fm,realm);
        listView.setAdapter(adapter);

        buttonAdd=(FloatingActionButton) view.findViewById(R.id.addTodo);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskDialog addTaskDialog=AddTaskDialog.newInstance(realm);
                addTaskDialog.show(fm,null);

            }
        });
        return view;
    }
}
