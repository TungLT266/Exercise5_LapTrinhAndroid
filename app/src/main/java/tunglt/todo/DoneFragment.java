package tunglt.todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class DoneFragment extends Fragment {
    RealmResults<DoneList> doneList;
    ListView listView;
    public static CustomAdapter2 adapter;
    public static Realm realm;

    public DoneFragment() {}

    public static DoneFragment newInstance(Realm rootRealm){
        DoneFragment fragment=new DoneFragment();
        realm=rootRealm;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        listView=(ListView)view.findViewById(R.id.listView);
        doneList=realm.where(DoneList.class).findAll();
        doneList.sort("Priority", Sort.ASCENDING);

        final android.support.v4.app.FragmentManager fm=DoneFragment.this.getActivity().getSupportFragmentManager();
        adapter= new CustomAdapter2(doneList,DoneFragment.this.getActivity(),fm,realm);
        listView.setAdapter(adapter);
        return view;
    }
}
