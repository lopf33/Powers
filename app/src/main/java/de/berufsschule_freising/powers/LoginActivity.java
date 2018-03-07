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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button loginButton;
    private Button registerButton;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.lRegisterButton);
        email = (EditText) findViewById(R.id.lEmailField);
        password = (EditText) findViewById(R.id.lPassField);

        loginButton.setOnClickListener(new LoginClickListener());
        registerButton.setOnClickListener(new RegisterClickListener());
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("userId", user.getUid());
        startActivity(intent);
    }

    private void login()
    {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }

    class LoginClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
            {
                login();
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Fields must all be filled!",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    class RegisterClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        }
    }
}
