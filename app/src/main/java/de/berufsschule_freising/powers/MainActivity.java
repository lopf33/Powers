package de.berufsschule_freising.powers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import de.berufsschule_freising.powers.adapters.DataAdapter;
import de.berufsschule_freising.powers.applogic.GridController;

public class MainActivity extends AppCompatActivity {

    private GridView gv;
    private TextView scoreView;
    private GridController gridController;
    private DataAdapter dataAdapter;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GridView) findViewById(R.id.gridView);
        scoreView = (TextView) findViewById(R.id.scoreField);

        gridController = new GridController(4,4);
        dataAdapter = new DataAdapter(this, gridController);

        gv.setAdapter(dataAdapter);

        gv.setNumColumns(gridController.getSizeY());

        //create gesture detector
        CustomGestureDetector cgd = new CustomGestureDetector(gridController, gridController);
        gestureDetector = new GestureDetector(this, cgd);

        gv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean r = gestureDetector.onTouchEvent(event);
                gv.setAdapter(dataAdapter);
                calcScore();
                return r;
            }
        });

        gridController.generateItem();
        calcScore();
    }

    private void calcScore()
    {
        int score = 0;
        for(int[] arr : gridController.getGrid())
        {
            for(int i : arr)
            {
                score += i;
            }
        }
        scoreView.setText(String.valueOf(score));
    }

}
