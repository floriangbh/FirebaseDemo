package firebase_demo.fg.com.android_firebase_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DataListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        ListView list = (ListView) this.findViewById(R.id.dataList);
        this.listAdapter = new DataListAdapter(this, R.layout.list_view_cell);
        list.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.getSingleDataShot();

    }

    protected void getSingleDataShot() {
        Log.d("MAIN", "PAAAASSS");
        /*this.mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Read each child
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User newUser = new User((String) child.getKey(), (String) child.getValue());
                    Log.d("MAIN", newUser.toString());
                    listAdapter.addUser(newUser);
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "onCancelled", databaseError.toException());
            }
        });*/

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("MAIN", "onChildAdded:" + dataSnapshot.getKey());
                User newUser = new User((String) dataSnapshot.getKey(), (String) dataSnapshot.getValue());
                Log.d("MAIN", newUser.toString());
                if (!(listAdapter.getUserList().contains(newUser))) {
                    listAdapter.addUser(newUser);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User newUser = new User((String) dataSnapshot.getKey(), (String) dataSnapshot.getValue());
                Log.d("DELETE", newUser.toString());
                if (!(listAdapter.getUserList().contains(newUser))) {
                    listAdapter.removeUser(newUser);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }
}
