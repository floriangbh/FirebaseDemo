package firebase_demo.fg.com.android_firebase_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DataListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Firebase database reference
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        // Init user list
        ListView list = (ListView) this.findViewById(R.id.dataList);
        this.listAdapter = new DataListAdapter(this, R.layout.list_view_cell);
        list.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Add listener
        mDatabase.addChildEventListener(new FirebaseEventListener(this.listAdapter));
    }
}
