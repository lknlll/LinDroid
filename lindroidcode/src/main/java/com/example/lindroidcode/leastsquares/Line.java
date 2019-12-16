package com.example.lindroidcode.leastsquares;

import android.util.Log;

/**
 * 二位数组表示点的坐标
 */
public class Line {

    private static final String TAG = Line.class.getSimpleName();

    /**
     * 最小二乘法求训练集的回归直线
     * @param points points[0] 为 x 值，points[1] 为对应的 y 值；
     * @return y=kx+b dbRt[0]为 k，dbRt[1] 为b；
     */
    public static double [] getLinePara(double [][] points ) {

        double[] dbRt = new double[2];//结果容器
        double dbXSum=0;
        for(int i=0;i<points[0].length;i++) {
            dbXSum=dbXSum+points[0][i];
        }
        double dbXAvg=dbXSum/points[0].length;//x average

        double dbWHeadVal=0;
        /**
         *  avg(x): 训练集x的均值；
         * （x - avg(x)）* y 求和；
         */
        for(int i=0;i<points[0].length;i++) {
            dbWHeadVal=dbWHeadVal+(points[0][i]-dbXAvg)*points[1][i];
        }
        double dbWDown=0;
        double dbWDownP=0;

        /**
         * x的平方和
         */
        for(int i=0;i<points[0].length;i++) {
            dbWDownP=dbWDownP+points[0][i]*points[0][i];
        }
        dbWDown=dbWDownP-                        //平方和减去
                (dbXSum*dbXSum/points[0].length);//和的平方/n
        double dbW=dbWHeadVal/dbWDown;
        dbRt[0]=dbW;
        double dbBSum=0;
        for(int i=0;i<points[0].length;i++) {
            dbBSum=dbBSum+(points[1][i]-dbW*points[0][i]);
        }
        double dbB=dbBSum/points[0].length;
        dbRt[1]=dbB;
        return dbRt;
    }

    public static double[][] deTrend(double[][] dataSet) {

        Log.e(TAG,"origin dataSet: " + printArrayElements(dataSet));
        Log.e(TAG,"linear line k: " + getLinePara(dataSet)[0]);
        Log.e(TAG,"linear line b: " + getLinePara(dataSet)[1]);
        double w = getLinePara(dataSet)[0];
        double b = getLinePara(dataSet)[1];

        double [][] detrendArr = new double[2][dataSet[0].length];//由两个一维数组组成，每个有dataSet[0].length个元素

        for (int i = 0; i < dataSet[0].length; i++) {
            detrendArr[0][i] = dataSet[0][i];
            detrendArr[1][i] = dataSet[1][i] - (dataSet[0][i] * w + b);
        }
        Log.e(TAG,"detrended dataSet: " + printArrayElements(detrendArr));
        return detrendArr;
    }

    public static String printArrayElements(double[][] array){
        StringBuilder s = new StringBuilder("{");
        s.append(printArrayElements(array[0])).append(", ").append(printArrayElements(array[1])).append(" }");
        return s.toString();
    }
    public static String printArrayElements(double[] array){
        StringBuilder s = new StringBuilder("{");
        for (int i = 0; i < array.length; i++) {
            if (array.length - 1 == i) {
                s.append(array[i]).append(" }");
            }else {
                s.append(array[i]).append(", ");
            }
        }
        return s.toString();
    }
}
