package com.company.part1.lab6;
import mpi.MPI;
public class TapeScheme {
    public static void main(String[] args) {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        System.out.println("Hello world from <"+me+"> of <"+size+">");
        MPI.Finalize();
    }
}
