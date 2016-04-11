package com.jsp.quizscores.exception;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tejal Shah.
 */
public class MoreThanFortyLinesException extends Exception {

    private static final long serialVersionUID = 1L;

    //No-arg constructor
    public MoreThanFortyLinesException(){
        super();
    }

    public MoreThanFortyLinesException(Context c){
        super();
        logError(c);
    }

    //Overrides toString method of superclass
    public String toString(){
        return "Input File has more than 40 lines";
    }

    public void logError(Context c) {
        File folder = c.getCacheDir();
        File f = new File(folder, "ErrorLog.txt");
        try {
            if(!f.exists() || f.isDirectory()){
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
            BufferedWriter buff = new BufferedWriter(fw);
            buff.write(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            buff.newLine();
            buff.write("More Than Forty Lines in Input file");
            buff.newLine();
            buff.close();

            //Check ErrorLog Content
            File fol = c.getCacheDir();
            File fi = new File(fol, "ErrorLog.txt");
            FileInputStream fis = new FileInputStream(fi);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("#PrintErrorLog", line);
            }
            buff.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
