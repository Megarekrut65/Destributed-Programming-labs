package com.company.part1.lab6;

import java.util.ArrayList;

public class ConsecutiveMatrixMultiplication {
    public static double[][] multiply(double[][] matrix1, double[][] matrix2){
        if(matrix1.length == 0 || matrix2.length == 0 || matrix1[0].length != matrix2.length){
            throw new IllegalArgumentException("Matrices have incorrect sizes!");
        }
        var res = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix2[0].length; j++){
                res[i][j] = 0;
                for (int k = 0; k < matrix1[0].length; k++){
                    res[i][j] = res[i][j] + matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return res;
    }
}
