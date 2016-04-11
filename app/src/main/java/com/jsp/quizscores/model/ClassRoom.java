package com.jsp.quizscores.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.jsp.quizscores.util.DatabaseConnector;
import com.jsp.quizscores.util.StatisticsCalculator;
import com.jsp.quizscores.util.StudentDataUploadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Tejal Shah.
 */
public class ClassRoom implements Parcelable {

    private static List<Student> classRoom;
    private static Map<String, Student> classRoom_map;
    private static int[] lowScore;
    private static int[] highScore;
    private static float[] avgScore;
    private static int studentCount = 0;


    private ClassRoom(){

    }

    public ClassRoom(Parcel in) {
        in.readIntArray(lowScore);
        in.readIntArray(highScore);
        in.readFloatArray(avgScore);
        classRoom = new ArrayList<Student>();
        in.readList(classRoom, getClass().getClassLoader());
    }

   public static void initializeClassRoom(Context context) {

           //Read File
           ArrayList<Student> fileData = StudentDataUploadUtil.readFile(context, "studentdata2.txt");
           DatabaseConnector databaseConnector =
                   new DatabaseConnector(context);
           //Delete existing data
           databaseConnector.deleteAllStudentResults();
           deleteClassRoom();
           //insert in DB
           databaseConnector.insertStudentList(fileData);
           //Read data from DB
           Cursor results = databaseConnector.getAllStudentResults();
           //Populate classroom with DB data
           createClassRoomFromDB(results);
           results.close();
           databaseConnector.close();
           calcStatistics();

    }

    private static void createClassRoomFromDB(Cursor results) {
        classRoom = new ArrayList<Student>();
        classRoom_map = new HashMap<String, Student>();

        if (results.moveToFirst()) {
            do {
                int stuid = results.getInt(1);
                int qu1 = results.getInt(2);
                int qu2 = results.getInt(3);
                int qu3 = results.getInt(4);
                int qu4 = results.getInt(5);
                int qu5 = results.getInt(6);
                addStudentToClassRoom(stuid, qu1, qu2, qu3, qu4, qu5);
            } while (results.moveToNext());
        }
    }

   public static void deleteClassRoom() {
       classRoom = null;
       classRoom_map = null;
       studentCount = 0;
       lowScore = null;
       highScore = null;
       avgScore = null;
   }

    private static void addStudentToClassRoom(int id, int q1, int q2, int q3, int q4, int q5) {
        Student s = new Student(id, q1, q2, q3, q4, q5);
        classRoom.add(s);
        classRoom_map.put(String.valueOf(s.getSID()), s);
        studentCount++;
    }

    private static void calcStatistics() {
        Student[] tempCalc = new Student[classRoom.size()];
        classRoom.toArray(tempCalc);
        calcLowScores(tempCalc);
        calcHighScores(tempCalc);
        calcAvgScores(tempCalc);
    }

    private static void calcLowScores(Student[] s) {
        lowScore = StatisticsCalculator.findLow(s);
    }

    private static void calcHighScores(Student[] s) {
        highScore = StatisticsCalculator.findHigh(s);
    }

    private static void calcAvgScores(Student[] s) {
        avgScore = StatisticsCalculator.findAvg(s);
    }

    public static List<Student> getClassRoomStudents(Context context) {
        if(classRoom==null){
            initializeClassRoom(context);
        }
        return classRoom;
    }

    public static Map<String, Student> getClassRoomMap(Context context) {
        if(classRoom_map==null){
            initializeClassRoom(context);
        }
        return classRoom_map;
    }

    public static int[] getLowScores() {
        return lowScore;
    }

    public static int[] getHighScores() {
        return highScore;
    }

    public static float[] getAvgScores() {
        return avgScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(lowScore);
        dest.writeIntArray(highScore);
        dest.writeFloatArray(avgScore);
        dest.writeList(classRoom);
    }

    public static final Parcelable.Creator<ClassRoom> CREATOR = new Parcelable.Creator<ClassRoom>() {
        public ClassRoom createFromParcel(Parcel in) {
            return new ClassRoom(in);
        }

        public ClassRoom[] newArray(int size) {
            return new ClassRoom[size];
        }
    };

}
