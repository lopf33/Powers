package de.berufsschule_freising.powers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import de.berufsschule_freising.powers.adapters.DataAdapter;
import de.berufsschule_freising.powers.applogic.GridController;

public class MainActivity extends AppCompatActivity {

    private GridView gv;
    private GridController gridController;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GridView) findViewById(R.id.gridView);
        gridController = new GridController(4,4);
        dataAdapter = new DataAdapter(this, gridController);

        gv.setAdapter(dataAdapter);

        gv.setNumColumns(gridController.getSizeY());


    }

}
