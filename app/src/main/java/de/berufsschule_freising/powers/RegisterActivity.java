package de.berufsschule_freising.powers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

import de.berufsschule_freising.powers.firebase.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    private Button registerButton;
    private EditText emailField;
    private EditText passField;
    private EditText userField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("/users");

        registerButton = (Button) findViewById(R.id.rRegisterButton);
        emailField = (EditText) findViewById(R.id.rEmailField);
        passField = (EditText) findViewById(R.id.rPassField);
        userField = (EditText) findViewById(R.id.mUserField);

        registerButton.setOnClickListener(new Register());

    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(RegisterActivity.this, MenuActivity.class);
        i.putExtra("userId", user.getUid());
        startActivity(i);
    }

    class Register implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!emailField.getText().toString().isEmpty() && !passField.getText().toString().isEmpty() && !userField.getText().toString().isEmpty()) {
                final boolean[] userExists = {false};
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        while (iterator.hasNext()) {
                            User user = iterator.next().getValue(User.class);
                            if (user.getName().equals(userField.getText().toString())) {
                                userExists[0] = true;
                            }
                        }
                        if (!userExists[0]) {
                            mAuth.createUserWithEmailAndPassword(emailField.getText().toString(), passField.getText().toString())
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                HashMap<String, Object> data = new HashMap<>();
                                                data.put(user.getUid(), new User(userField.getText().toString()));
                                                ref.updateChildren(data);
                                                updateUI(user);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Username already taken!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "You need to fill all fields!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}
