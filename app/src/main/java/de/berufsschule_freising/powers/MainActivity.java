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

import de.berufsschule_freising.powers.adapters.DataAdapter;
import de.berufsschule_freising.powers.applogic.GridController;

public class MainActivity extends AppCompatActivity {

    private GridView gv;
    private TextView scoreView;
    private Button ngButton;
    private Button menuButton;
    private GridController gridController;
    private DataAdapter dataAdapter;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
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
            startActivity(i);
        }
    }

}
