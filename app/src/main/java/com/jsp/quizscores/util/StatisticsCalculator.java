package com.jsp.quizscores.util;

import com.jsp.quizscores.model.Student;

/**
 * Created by Tejal Shah.
 */
public class StatisticsCalculator {

        //Method to calculate the lowest quiz score
        public static int[] findLow(Student[] a){

            int[] studentScore;
            int [] lowscores = new int [5];

            for (int i=0; i<lowscores.length;i++){
                lowscores[i]= Integer.MAX_VALUE;
            }

            for(Student s: a){
                if (s!=null){
                    studentScore = s.getScores();
                    for(int i=0; i<lowscores.length;i++){
                        if(lowscores[i] > studentScore[i]){
                            lowscores[i] = studentScore[i];
                        }
                    }

                }
            }

            for (int i=0; i<lowscores.length;i++){
                if (lowscores[i]==Integer.MAX_VALUE){
                    lowscores[i]=0;
                }
            }

            return lowscores;
        }

        //Method to calculate the highest quiz score
        public static int[] findHigh(Student [] a){

            int[] studentScore;
            int [] highscores = new int [5];

            for (int i=0; i<highscores.length;i++){
                highscores[i]= 0;
            }

            for(Student s: a){
                if (s!=null){
                    studentScore = s.getScores();
                    for(int i=0; i<highscores.length;i++){
                        if(highscores[i] < studentScore[i]){
                            highscores[i] = studentScore[i];
                        }
                    }

                }
            }

            return highscores;
        }

        //Method to calculate the average quiz score
        public static float[] findAvg(Student [] a){

            int[] studentScore;
            float [] avgscores = new float [5];
            int studentCount=0;
            int[] totalScore = new int[5];

            for (int i=0; i<totalScore.length;i++){
                totalScore[i]= 0;
            }

            for(Student s: a){
                if (s!=null){
                    studentScore = s.getScores();
                    studentCount++;
                    for(int i=0; i<totalScore.length;i++){
                        totalScore[i]+= studentScore[i];
                    }
                }
            }

            for(int i=0; i<avgscores.length;i++){
                avgscores[i] = (float) totalScore[i]/studentCount;
            }

            return avgscores;
        }
}
