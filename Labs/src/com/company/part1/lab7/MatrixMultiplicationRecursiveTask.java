package com.company.part1.lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
//
//public class MatrixMultiplicationRecursiveTask extends RecursiveTask<double[][]> {
//    private double[][] matrix1;
//    private double[][] matrix2;
//    private double[][] res;
//    private int index;
//    public MatrixMultiplicationRecursiveTask(
//            double[][] matrix1, double[][] matrix2, double[][] res, int index) {
//        this.matrix1 = matrix1;
//        this.matrix2 = matrix2;
//        this.res = res;
//        this.index = index;
//    }

//    @Override
//    protected double[][] compute() {
//        if(index < matrix1.length){
//            List<MatrixMultiplicationRecursiveTask> subtasks =
//                    new ArrayList<MatrixMultiplicationRecursiveTask>();
//            subtasks.addAll(createSubtasks());
//
//            for(MatrixMultiplicationRecursiveTask subtask : subtasks){
//                subtask.fork();
//            }
////            for(int j = 0; j < matrix1.length; j++) {
////                for (int k = 0; k < matrix1[0].length; k++) {
////                    res[index][j] += matrix1[index][k] * matrix2[k][j];
////                }
////            }
//        }
//        return res;
//    }
//    private List<MatrixMultiplicationRecursiveTask> createSubtasks() {
//        List<MatrixMultiplicationRecursiveTask> subtasks =
//                new ArrayList<MatrixMultiplicationRecursiveTask>();
//
//        MatrixMultiplicationRecursiveTask subtask1 =
//                new MatrixMultiplicationRecursiveTask(this.workLoad / 2);
//        MatrixMultiplicationRecursiveTask subtask2 =
//                new MatrixMultiplicationRecursiveTask(this.workLoad / 2);
//
//        subtasks.add(subtask1);
//        subtasks.add(subtask2);
//
//        return subtasks;
//    }
//}
