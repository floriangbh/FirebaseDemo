package firebase_demo.fg.com.android_firebase_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<User> userList;
    private ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userList = new ArrayList<User>();

        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        ListView list = (ListView) this.findViewById(R.id.dataList);
        this.listAdapter = new DataListAdapter(this, R.layout.list_view_cell, this.userList);
        list.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.getSingleDataShot();

    }

    protected void getSingleDataShot() {
        Log.d("MAIN", "PAAAASSS");
        this.mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Read each child
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User newUser = new User((String) child.getKey(), (String) child.getValue());
                    Log.d("MAIN", newUser.toString());
                    userList.add(newUser);
                }

                listAdapter.addAll(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "onCancelled", databaseError.toException());
            }
        });

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("MAIN", "onChildAdded:" + dataSnapshot.getKey());
                User newUser = new User((String) dataSnapshot.getKey(), (String) dataSnapshot.getValue());
                Log.d("MAIN", newUser.toString());
                userList.add(newUser);
                listAdapter.addAll(userList);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
