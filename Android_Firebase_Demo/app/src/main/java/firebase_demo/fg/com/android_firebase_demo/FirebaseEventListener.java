package firebase_demo.fg.com.android_firebase_demo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class FirebaseEventListener implements ChildEventListener {

    private DataListAdapter userList;


    public FirebaseEventListener(DataListAdapter userList) {
        this.userList = userList;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
        User newUser = new User((String) dataSnapshot.getValue(), (String) dataSnapshot.getKey());
        if (!(userList.getUserList().contains(newUser))) {
            userList.addUser(newUser);
            userList.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // Child moved
        // ...
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        userList.removeUser(dataSnapshot.getKey());
        userList.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        // Child changed
        // ...
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Error
        // ...
    }
}
