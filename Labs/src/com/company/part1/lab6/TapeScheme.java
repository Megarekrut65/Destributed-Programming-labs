package com.company.part1.lab6;
import mpi.MPI;

import java.util.Arrays;

public class TapeScheme {
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
        System.out.println(Arrays.deepToString(multiply(args, matrix1, matrix2)));
    }
    private static double[][] multiply(String[] args, double[][] matrix1, double[][] matrix2){
        if(matrix1.length == 0 || matrix2.length == 0 || matrix1[0].length != matrix2.length){
            throw new IllegalArgumentException("Matrices have incorrect sizes!");
        }
        double[][] res = new double[matrix1.length][matrix2[0].length],
                myRes = new double[matrix1.length][matrix2[0].length];
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        System.out.println("Hello world from <"+me+"> of <"+size+">");
        for(int i= 0; i < myRes.length; i++){
            for(int j = 0; j < myRes[i].length; j++){
                myRes[i][j] = me + 1;
            }
        }
        for(int i = 0; i < myRes.length; i++){
            MPI.COMM_WORLD.Reduce(myRes[i],
                    0,
                    res[i],
                    0,
                    myRes[i].length,
                    MPI.DOUBLE,
                    MPI.SUM,
                    0);
        }
        MPI.Finalize();
        if(me == 0) return res;
        else return null;
    }
}
