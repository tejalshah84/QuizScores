package com.jsp.quizscores.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tejal Shah
 */
public class Student implements Parcelable {

    private int SID;
    private int scores[];
    private int mData;

    //No-arg constructor
    public Student(){
        this.scores = new int[5];
    }

    private Student(Parcel in){
        this.SID = in.readInt();
        this.scores = new int[5];
        in.readIntArray(this.scores);
    }

    //Constructor with args
    public Student(int id, int q1, int q2, int q3, int q4, int q5){
        this.SID = id;
        this.scores = new int[5];
        scores[0] = q1;
        scores[1] = q2;
        scores[2] = q3;
        scores[3] = q4;
        scores[4] = q5;
    }

    //Getter for Student ID
    public int getSID() {
        return this.SID;
    }

    //Setter for Student ID
    public void setSID(int sID) {
        this.SID = sID;
    }

    //Getter for Student Scores
    public int[] getScores() {
        return scores;
    }

    //Setter for Student Scores
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    //Setter for Student Scores
    public void setEachScore(int q1, int q2, int q3, int q4, int q5) {
        if (scores==null){
            scores = new int[5];
        }
        scores[0] = q1;
        scores[1] = q2;
        scores[2] = q3;
        scores[3] = q4;
        scores[4] = q5;
    }

    //Print Student ID
    public void printStudentID(){
        System.out.print(getSID() + "      \t");
    }

    //Print Student scores
    public void printScores(){
        for (int i=0; i<getScores().length; i++){
            System.out.print(getScores()[i] + "\t");
        }
        System.out.println();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SID);
        dest.writeIntArray(this.scores);
    }

    public static final Parcelable.Creator<Student> CREATOR
            = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}

