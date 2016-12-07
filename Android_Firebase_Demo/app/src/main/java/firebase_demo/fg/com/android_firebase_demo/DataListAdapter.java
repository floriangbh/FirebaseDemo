package firebase_demo.fg.com.android_firebase_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<User> userList;
    private int layoutRessource;

    public DataListAdapter(Context ctx, int layoutResourceId, ArrayList<User> data) {
        super(ctx, layoutResourceId);

        this.userList = data;
        this.layoutRessource = layoutResourceId;
        this.context = ctx;
    }

    @Override
    public int getCount() {
        return this.userList.size();
    }

    @Override
    public User getItem(int position) {
        return this.userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater li = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(this.layoutRessource, null);
        }

        // Get row data
        User currentUser  = getItem(position);

        // Id
        TextView idLabel = (TextView) row.findViewById(R.id.id);
        idLabel.setText(currentUser.getId());

        // User
        TextView userLabel = (TextView) row.findViewById(R.id.user);
        userLabel.setText(currentUser.getName());

        return row;
    }
}
