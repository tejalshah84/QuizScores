package com.jsp.quizscores.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.jsp.quizscores.R;
import com.jsp.quizscores.model.ClassRoom;
import com.jsp.quizscores.util.StatisticsCalculator;

/**
 * Created Tejal Shah.
 */
public class ClassStatisticsActivity extends AppCompatActivity {

    private TextView q1L, q2L, q3L, q4L, q5L, q1H, q2H, q3H, q4H, q5H, q1A, q2A, q3A, q4A, q5A;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_stats);

        q1L = (TextView) findViewById(R.id.Q1Low);
        q2L = (TextView) findViewById(R.id.Q2Low);
        q3L = (TextView) findViewById(R.id.Q3Low);
        q4L = (TextView) findViewById(R.id.Q4Low);
        q5L = (TextView) findViewById(R.id.Q5Low);

        q1H = (TextView) findViewById(R.id.Q1High);
        q2H = (TextView) findViewById(R.id.Q2High);
        q3H = (TextView) findViewById(R.id.Q3High);
        q4H = (TextView) findViewById(R.id.Q4High);
        q5H = (TextView) findViewById(R.id.Q5High);

        q1A = (TextView) findViewById(R.id.Q1Avg);
        q2A = (TextView) findViewById(R.id.Q2Avg);
        q3A = (TextView) findViewById(R.id.Q3Avg);
        q4A = (TextView) findViewById(R.id.Q4Avg);
        q5A = (TextView) findViewById(R.id.Q5Avg);
        statsFill();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void statsFill(){
        int[] low = ClassRoom.getLowScores();
        int[] high = ClassRoom.getHighScores();
        float[] avg = ClassRoom.getAvgScores();
        q1L.setText(String.valueOf(low[0]));
        q2L.setText(String.valueOf(low[1]));
        q3L.setText(String.valueOf(low[2]));
        q4L.setText(String.valueOf(low[3]));
        q5L.setText(String.valueOf(low[4]));
        q1H.setText(String.valueOf(high[0]));
        q2H.setText(String.valueOf(high[1]));
        q3H.setText(String.valueOf(high[2]));
        q4H.setText(String.valueOf(high[3]));
        q5H.setText(String.valueOf(high[4]));
        q1A.setText(String.format("%.1f", avg[0]));
        q2A.setText(String.format("%.1f", avg[1]));
        q3A.setText(String.format("%.1f", avg[2]));
        q4A.setText(String.format("%.1f", avg[3]));
        q5A.setText(String.format("%.1f", avg[4]));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, StudentListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
