package com.company.part1.lab6;

import java.util.ArrayList;
import java.util.Arrays;

public class ConsecutiveMatrixMultiplication {
    public static void main(String[] args) {
        double[][]
                matrix1 = {
                {1,2,3},
                {1,2,3},
                {1,2,3}},
                matrix2={
                        {5,6,7},
                        {5,6,7},
                        {5,6,7}};
        System.out.println(Arrays.deepToString(ConsecutiveMatrixMultiplication.multiply(matrix1, matrix2)));
    }
    private static double[][] multiply(double[][] matrix1, double[][] matrix2){
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
