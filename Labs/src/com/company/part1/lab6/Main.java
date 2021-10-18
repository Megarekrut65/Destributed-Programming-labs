package com.company.part1.lab6;

import java.util.Arrays;

public class Main {
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
}
