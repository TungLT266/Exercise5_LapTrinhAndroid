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

public class CustomAdapter2 extends ArrayAdapter<DoneList> {
    private RealmResults<DoneList> doneList;
    Context mContext;
    android.support.v4.app.FragmentManager fm;
    Realm realm;

    public CustomAdapter2(RealmResults<DoneList> doneList, Context context,android.support.v4.app.FragmentManager fm,Realm rootRealm){
        super(context,R.layout.row_item,doneList);
        this.doneList=doneList;
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
        final DoneList doneList = getItem(position);
        CustomAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.dueTo = (TextView) convertView.findViewById(R.id.dueTo);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.priority);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(doneList.getName());
        viewHolder.dueTo.setText("Due to "+doneList.getDate()+"/"+doneList.getMonth()+"/"+doneList.getYear());
        viewHolder.priority.setText( (doneList.getPriority()==1)? "High" : ((doneList.getPriority()==2)?"Normal":"Low") );
        viewHolder.priority.setTextColor((doneList.getPriority()==1)? Color.RED : ((doneList.getPriority()==2)?Color.YELLOW:Color.GREEN));

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Warning")
                        .setMessage("Really ? You \"done\" it. Now you don't want anybody to know ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yah", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        doneList.deleteFromRealm();
                                    }
                                });
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton("I'll think about it", null).show();
                return true;
            }
        });
        return convertView;


    }
}
