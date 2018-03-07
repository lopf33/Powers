package de.berufsschule_freising.powers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

import de.berufsschule_freising.powers.adapters.CustomListAdapter;
import de.berufsschule_freising.powers.adapters.DataAdapter;
import de.berufsschule_freising.powers.applogic.GridController;
import de.berufsschule_freising.powers.firebase.User;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference ref;

    private GridView gv;
    private TextView scoreView;
    private Button ngButton;
    private Button menuButton;
    private GridController gridController;
    private DataAdapter dataAdapter;

    private Intent parentIntent;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentIntent = getIntent();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("/users");

        gv = (GridView) findViewById(R.id.gridView);
        scoreView = (TextView) findViewById(R.id.scoreField);
        ngButton = (Button) findViewById(R.id.ngButton);
        menuButton = (Button) findViewById(R.id.menuButton);

        gridController = new GridController(4,4);
        dataAdapter = new DataAdapter(this, gridController);

        gv.setAdapter(dataAdapter);

        gv.setNumColumns(gridController.getSizeY());

        //create gesture detector
        CustomGestureDetector cgd = new CustomGestureDetector(gridController, gridController);
        gestureDetector = new GestureDetector(this, cgd);

        gv.setOnTouchListener(new CustomOnTouch(this));
        ngButton.setOnClickListener(new Recreate(this));
        menuButton.setOnClickListener(new BackToMenu());

        gridController.generateItem();
        scoreView.setText(String.valueOf(calcScore()));
    }

    private int calcScore()
    {
        int score = 0;
        for(int[] arr : gridController.getGrid())
        {
            for(int i : arr)
            {
                score += i;
            }
        }
        return score;
    }

    private void updateDatabaseHighScore()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(parentIntent.getStringExtra("userId")).getValue(User.class);

                user.setHighScore(calcScore());
                HashMap<String, Object> data = new HashMap<>();
                data.put(parentIntent.getStringExtra("userId"), user);
                ref.updateChildren(data);
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class CustomOnTouch implements View.OnTouchListener {

        private AlertDialog dialog;
        private AlertDialog.Builder builder;

        public CustomOnTouch(Context c)
        {
            builder = new AlertDialog.Builder(c);
            builder.setCancelable(true);
            builder.setTitle("Game Over!");

            dialog = builder.create();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(!gridController.isGameOver())
            {
                boolean r = gestureDetector.onTouchEvent(event);
                gv.setAdapter(dataAdapter);
                scoreView.setText(String.valueOf(calcScore()));
                if(gridController.isGameOver())
                {
                    builder.setMessage("Your score is: " + calcScore());
                    dialog = builder.create();
                    dialog.show();
                    updateDatabaseHighScore();
                }
                return r;
            }
            return true;
        }
    }

    class Recreate implements View.OnClickListener
    {
        AppCompatActivity activity;
        public Recreate(AppCompatActivity activity)
        {
            this.activity = activity;
        }
        @Override
        public void onClick(View v) {
            activity.recreate();
        }
    }

    class BackToMenu implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i = new Intent(MainActivity.this, MenuActivity.class);
            i.putExtra("userId", parentIntent.getStringExtra("userId"));
            startActivity(i);
        }
    }

}
