package com.jsp.quizscores.ui;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsp.quizscores.R;
import com.jsp.quizscores.model.ClassRoom;
import com.jsp.quizscores.model.Student;

/**
 * A fragment representing a single Student detail screen.
 * This fragment is either contained in a {@link StudentListActivity}
 * in two-pane mode (on tablets) or a {@link StudentDetailActivity}
 * on handsets.
 */
public class StudentDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_DATA = "item_details";

    private Student stu;

    public StudentDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            stu = ClassRoom.getClassRoomMap(getContext()).get(String.valueOf(getArguments().getInt(ARG_ITEM_ID)));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Quiz Scores");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_detail, container, false);

        if (stu != null) {
            ((TextView) rootView.findViewById(R.id.student_detail_title)).setText("Student ID: " + String.valueOf(stu.getSID()));
            ((TextView) rootView.findViewById(R.id.Q1Stu)).setText(String.valueOf(stu.getScores()[0]));
            ((TextView) rootView.findViewById(R.id.Q2Stu)).setText(String.valueOf(stu.getScores()[1]));
            ((TextView) rootView.findViewById(R.id.Q3Stu)).setText(String.valueOf(stu.getScores()[2]));
            ((TextView) rootView.findViewById(R.id.Q4Stu)).setText(String.valueOf(stu.getScores()[3]));
            ((TextView) rootView.findViewById(R.id.Q5Stu)).setText(String.valueOf(stu.getScores()[4]));
        }

        return rootView;
    }
}
