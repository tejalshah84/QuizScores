package com.jsp.quizscores.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jsp.quizscores.model.Student;

import java.util.ArrayList;

/**
 * Created by Tejal Shah.
 */
public class DatabaseConnector {

    // database name
    private static final String DATABASE_NAME = "StudentQuizDB";
    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context)
    {
        Log.d("#ShowData", "Creating DBConnector");
        // create a new DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close(); // close the database connection
    } // end method close

    public boolean insertStudentList(ArrayList<Student> stu){
        try {
            open();
            for (Student s : stu) {
                ContentValues newStudent = new ContentValues();
                newStudent.put("student_id", s.getSID());
                newStudent.put("q1", s.getScores()[0]);
                newStudent.put("q2", s.getScores()[1]);
                newStudent.put("q3", s.getScores()[2]);
                newStudent.put("q4", s.getScores()[3]);
                newStudent.put("q5", s.getScores()[4]);
                database.insert("student", null, newStudent);
            }
            close();
        } catch(SQLiteException e){
            return false;
        }
        return true;
    }

    // inserts new student & quiz in the database
    public void insertStudent(Student s)
    {
        ContentValues newStudent = new ContentValues();
        newStudent.put("student_id", s.getSID());
        newStudent.put("q1", s.getScores()[0]);
        newStudent.put("q2", s.getScores()[1]);
        newStudent.put("q3", s.getScores()[2]);
        newStudent.put("q4", s.getScores()[3]);
        newStudent.put("q5", s.getScores()[4]);
        open(); // open the database
        database.insert("student", null, newStudent);
        Log.d("#ShowData", "Insert in DB");
        close(); // close the database
    } // end method insertMortgage

    // return a Cursor with all student information from the database
    public Cursor getAllStudentResults()
    {
        Log.d("#ShowData", "Select from DB");

        String allStudentsQuery = "SELECT * FROM student";

        return databaseOpenHelper.getReadableDatabase().rawQuery(allStudentsQuery, null);
    } // end method getAllStudents

    // return a Cursor containing all information about the student specified by the given id
    public Cursor getOneStudentResult(int id)
    {
        Log.d("#ShowData", "Select from DB");

        String oneStudentQuery = "SELECT * FROM student where student.student_id=" + id;

        return databaseOpenHelper.getReadableDatabase().rawQuery(oneStudentQuery, null);
    } // end method getOneStudent

    //
    public Cursor getStudentCount()
    {
        Log.d("#ShowData", "Select from DB");

        String countQuery = "SELECT COUNT(*) FROM student";

        return databaseOpenHelper.getReadableDatabase().rawQuery(countQuery, null);
    } // end method getOneStudent

    public void deleteAllStudentResults()
    {
        open(); // open the database
        database.delete("student", null, null);
        close(); // close the database
    } // end method deleteAllStudentResults

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the Mortgage table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named Student
            Log.d("#ShowData", "Creating Tables");
            String createStudent = "CREATE TABLE student" +
                    "(_id integer primary key autoincrement, " +
                    "student_id INTEGER unique, " +
                    "q1 INTEGER, q2 INTEGER, q3 INTEGER, q4 INTEGER, q5 INTEGER, " +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";

            db.execSQL(createStudent); // execute the query
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper
}
