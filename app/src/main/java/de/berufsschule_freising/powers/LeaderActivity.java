package de.berufsschule_freising.powers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.berufsschule_freising.powers.adapters.CustomListAdapter;
import de.berufsschule_freising.powers.firebase.User;

public class LeaderActivity extends AppCompatActivity {

    private DatabaseReference ref;

    private ListView listView;

    private Intent parentIntent;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("/users");

        parentIntent = getIntent();
        currentUser = null;

        listView = (ListView) findViewById(R.id.listView);

        final List<User> users = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.child(parentIntent.getStringExtra("userId")).getValue(User.class);
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                users.clear();
                while(iterator.hasNext())
                {
                    User user = iterator.next().getValue(User.class);
                    users.add(user);
                }

                UserSorter sorter = new UserSorter(users);
                List<User> sortedUsers = sorter.getSortedUsers();
                CustomListAdapter myAdapter = new CustomListAdapter(LeaderActivity.this, sortedUsers, currentUser);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
