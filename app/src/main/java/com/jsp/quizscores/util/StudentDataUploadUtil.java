package com.jsp.quizscores.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.jsp.quizscores.exception.MoreThanFortyLinesException;
import com.jsp.quizscores.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Created by Tejal Shah.
 */
public class StudentDataUploadUtil {

    public static ArrayList<Student> readFile(Context context, String filename) {

        ArrayList<Student> stu = new ArrayList<Student>();
        AssetManager am = context.getAssets();
        int lineCounter = 0;
        int studentCount = 0;
        boolean title = false;
        Log.d("#UploadFile", "Reading File");

        try {
            //FileReader file = new FileReader(filename);
            InputStreamReader file = new InputStreamReader(am.open(filename));
            BufferedReader buff = new BufferedReader(file);
            boolean eof = false;

            while (!eof) {
                String line = buff.readLine();

                if (line == null){
                    eof = true;
                }
                else {
                    ++lineCounter;

                    StringTokenizer st = new StringTokenizer(line);
                    boolean isID = true;
                    int id = 0;
                    int i=0;
                    int [] temp = new int[5];
                    boolean nonInt = false;

                    while (st.hasMoreTokens()) {
                        String str = st.nextToken();
                        if (lineCounter == 1 && convertToInt(str)==-1){
                            nonInt = true;
                            title = true;
                        }
                        else{
                            try{
                                if (isID){
                                    id = Integer.parseInt(str);
                                    isID = false;
                                }
                                else {
                                    temp[i++] = Integer.parseInt(str);
                                }
                                nonInt = false;
                            } catch(NumberFormatException e) {
                                System.out.println("Error: Invalid Score format");
                            }
                        }//else
                    }// while

                    if((title && lineCounter>41)||(!title && lineCounter>40)){
                        buff.close();
                        throw new MoreThanFortyLinesException(context);
                    }

                    if (!nonInt){
                        Student tempStudent = new Student();
                        tempStudent.setSID(id);
                        tempStudent.setScores(temp);
                        stu.add(tempStudent);
                        studentCount++;
                    }
                }//else
            }//while

            buff.close();
        } catch(MoreThanFortyLinesException e){
            System.out.println("Error: " + e.toString());
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
        return stu;
    }

    //Method to check if a string value can be converted to int and catch exception if not
    static int convertToInt(String s){

        int value = -1;

        try{
            value = Integer.parseInt(s);
            return value;
        }
        catch(NumberFormatException e) {
            return value;
        }
        catch(NullPointerException e) {
            return value;
        }
    }

}
