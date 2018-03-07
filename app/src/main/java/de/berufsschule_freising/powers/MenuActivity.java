package de.berufsschule_freising.powers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.berufsschule_freising.powers.firebase.User;

public class MenuActivity extends AppCompatActivity {

    DatabaseReference ref;

    Intent parentIntent;

    private Button startButton;
    private Button leaderboardButton;
    private Button logoutButton;
    private TextView userField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("/users");

        parentIntent = getIntent();

        startButton = (Button) findViewById(R.id.startButton);
        leaderboardButton = (Button) findViewById(R.id.leaderboardButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        userField = (TextView) findViewById(R.id.mUserField);

        ref.addValueEventListener(new UserFieldListener());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                i.putExtra("userId", parentIntent.getStringExtra("userId"));
                startActivity(i);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, LeaderActivity.class);
                i.putExtra("userId", parentIntent.getStringExtra("userId"));
                startActivity(i);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    class UserFieldListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.child(parentIntent.getStringExtra("userId")).getValue(User.class);
            userField.setText("Hello, " + user.getName());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
