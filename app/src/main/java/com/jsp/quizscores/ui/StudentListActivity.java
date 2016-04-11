package com.jsp.quizscores.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.jsp.quizscores.R;
import com.jsp.quizscores.model.ClassRoom;
import com.jsp.quizscores.model.Student;

import java.util.List;

/**
 * An activity representing a list of Students. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StudentListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ClassRoom.getClassRoomMap(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.student_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.student_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.view_stats){
            Intent intent = new Intent(StudentListActivity.this, ClassStatisticsActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ClassRoom.getClassRoomStudents(this)));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Student> studentList;

        public SimpleItemRecyclerViewAdapter(List<Student> stu) {
            studentList = stu;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.studentItem = studentList.get(position);
            holder.itemTitleView.setText("Student ID:");
            holder.itemStudentIDView.setText(String.valueOf(studentList.get(position).getSID()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(StudentDetailFragment.ARG_ITEM_ID, holder.studentItem.getSID());
                        arguments.putIntArray(StudentDetailFragment.ARG_ITEM_DATA, holder.studentItem.getScores());
                        StudentDetailFragment fragment = new StudentDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.student_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StudentDetailActivity.class);
                        intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, holder.studentItem.getSID());
                        intent.putExtra(StudentDetailFragment.ARG_ITEM_DATA, holder.studentItem.getScores());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView itemTitleView;
            public final TextView itemStudentIDView;
            public Student studentItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                itemTitleView = (TextView) view.findViewById(R.id.id);
                itemStudentIDView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + itemStudentIDView.getText() + "'";
            }
        }
    }

    protected void onStop()
    {
        super.onStop();
        ClassRoom.deleteClassRoom();
    } // end method onStop
}
