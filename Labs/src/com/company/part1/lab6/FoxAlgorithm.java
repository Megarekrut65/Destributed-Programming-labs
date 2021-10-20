package com.company.part1.lab6;

import mpi.Cartcomm;
import mpi.MPI;
import mpi.Status;

import java.util.Arrays;
class Data{
    public double[] pAMatrix;
    public double[] pBMatrix;
    public double[] pCMatrix;
    public double[] pAblock;
    public double[] pBblock;
    public double[] pCblock;
    public double[] pMatrixAblock;
    public int[] Size = new int[1];
    public int BlockSize;
}
public class FoxAlgorithm {
    private static int ProcNum = 0;
    private static int ProcRank = 0;
    private static int GridSize;
    private static int[] GridCoords = new int[2];
    private static Cartcomm ColComm;
    private static Cartcomm RowComm;
    private static Cartcomm GridComm;
    public static void main(String[] args) {
        start(args);
    }
    private static void DummyDataInitialization (Data data){//double* pAMatrix, double* pBMatrix,int Size){
        int i, j;
        for (i=0; i<data.Size[0]; i++)
            for (j=0; j<data.Size[0]; j++) {
                data.pAMatrix[i*data.Size[0]+j] = 2;
                data.pBMatrix[i*data.Size[0]+j] = 2;
            }
    }
    private static void SerialResultCalculation(double[] pAMatrix, double[] pBMatrix,
                                                double[] pCMatrix, int Size) {
        int i, j, k; // Loop variables
        for (i=0; i<Size; i++) {
            for (j=0; j<Size; j++)
                for (k=0; k<Size; k++)
                    pCMatrix[i*Size+j] += pAMatrix[i*Size+k]*pBMatrix[k*Size+j];
        }
    }
    private static void BlockMultiplication(double[] pAblock, double[] pBblock,
                                            double[] pCblock, int Size) {
        SerialResultCalculation(pAblock,pBblock,pCblock, Size);
    }
    private static void CreateGridCommunicators() {
        var DimSize = new int[2];
        var Periodic = new boolean[2];
        var Subdims = new boolean[2];

        DimSize[0] = GridSize;
        DimSize[1] = GridSize;
        Periodic[0] = false;
        Periodic[1] = false;
        GridComm = MPI.COMM_WORLD.Create_cart(DimSize,Periodic, true);
        GridCoords = GridComm.Coords(ProcRank);
        Subdims[0] = false; // Dimensionality fixing
        Subdims[1] = true; // The presence of the given dimension in the subgrid
        RowComm = GridComm.Sub(Subdims);
        Subdims[0] = true;
        Subdims[1] = false;
        ColComm = GridComm.Sub(Subdims);
    }
    private static void ProcessInitialization (Data data){//double* &pAMatrix, double* &pBMatrix,
                                               //double* &pCMatrix, double* &pAblock, double* &pBblock, double* &pCblock,
                                               //double* &pTemporaryAblock, int &Size, int &BlockSize ) {
        if (ProcRank == 0) {
            data.Size[0] = 4;
            if(data.Size[0] % GridSize != 0)
                System.out.println("Err 79");
        }
        MPI.COMM_WORLD.Bcast(data.Size,0,1,MPI.INT, 0);
        data.BlockSize = data.Size[0]/GridSize;
        data.pAblock = new double [data.BlockSize*data.BlockSize];
        data.pBblock = new double [data.BlockSize*data.BlockSize];
        data.pCblock = new double [data.BlockSize*data.BlockSize];
        data.pMatrixAblock = new double [data.BlockSize*data.BlockSize];
        for (int i=0; i<data.BlockSize*data.BlockSize; i++) {
            data.pCblock[i] = 0;
        }
        if(ProcRank == 0){
            data.pAMatrix = new double [data.Size[0]*data.Size[0]];
            data.pBMatrix = new double [data.Size[0]*data.Size[0]];
            data.pCMatrix = new double [data.Size[0]*data.Size[0]];
            DummyDataInitialization(data);
        }
    }
    private static void CheckerboardMatrixScatter(double[] pMatrix, double[] pMatrixBlock,
                                                  int Size, int BlockSize) {
        var MatrixRow = new double [BlockSize*Size];
        if (GridCoords[1] == 0) {
            ColComm.Scatter(pMatrix, 0, BlockSize*Size, MPI.DOUBLE, MatrixRow,
                    0, BlockSize*Size, MPI.DOUBLE, 0);
        }
        for (int i=0; i<BlockSize; i++) {
            RowComm.Scatter(MatrixRow, i*Size, BlockSize, MPI.DOUBLE,
                    pMatrixBlock, i*BlockSize, BlockSize, MPI.DOUBLE, 0);
        }
    }
    private static void DataDistribution(double[] pAMatrix, double[] pBMatrix, double[]
            pMatrixAblock, double[] pBblock, int Size, int BlockSize) {
        CheckerboardMatrixScatter(pAMatrix, pMatrixAblock, Size, BlockSize);
        CheckerboardMatrixScatter(pBMatrix, pBblock, Size, BlockSize);
    }
    private static void ResultCollection (double[] pCMatrix, double[] pCblock, int Size,
                                          int BlockSize) {
        var pResultRow = new double [Size*BlockSize];
        for (int i=0; i<BlockSize; i++) {
            RowComm.Gather(pCblock, i*BlockSize, BlockSize, MPI.DOUBLE,
                    pResultRow, i*Size, BlockSize, MPI.DOUBLE, 0);
        }
        if (GridCoords[1] == 0) {
            ColComm.Gather(pResultRow, 0,BlockSize*Size, MPI.DOUBLE,
                    pCMatrix, 0,BlockSize*Size, MPI.DOUBLE, 0);
        }
    }
    private static void ABlockCommunication (int iter, double[] pAblock, double[] pMatrixAblock,
                                             int BlockSize) {
        int Pivot = (GridCoords[0] + iter) % GridSize;
        if (GridCoords[1] == Pivot) {
            for (int i=0; i<BlockSize*BlockSize; i++)
                pAblock[i] = pMatrixAblock[i];
        }
        RowComm.Bcast(pAblock, 0, BlockSize*BlockSize, MPI.DOUBLE, Pivot);
    }
    private static void BblockCommunication (double[] pBblock, int BlockSize) {
        int NextProc = GridCoords[0] + 1;
        if ( GridCoords[0] == GridSize-1 ) NextProc = 0;
        int PrevProc = GridCoords[0] - 1;
        if ( GridCoords[0] == 0 ) PrevProc = GridSize-1;
        var status = ColComm.Sendrecv_replace(pBblock, 0,BlockSize*BlockSize, MPI.DOUBLE,
                NextProc, 0,PrevProc, 0);
    }
    private static void ParallelResultCalculation(double[] pAblock, double[] pMatrixAblock,
                                   double[] pBblock, double[] pCblock, int BlockSize) {
        for (int iter = 0; iter < GridSize; iter ++) {
            ABlockCommunication (iter, pAblock, pMatrixAblock, BlockSize);
            BlockMultiplication(pAblock, pBblock, pCblock, BlockSize);
            BblockCommunication(pBblock, BlockSize);
        }
    }
    private static void start(String[] args){
        Data data = new Data();
        MPI.Init(args);
        ProcRank = MPI.COMM_WORLD.Rank();
        ProcNum = MPI.COMM_WORLD.Size();
        GridSize = (int) Math.sqrt((double)ProcNum);
        if (ProcNum != GridSize*GridSize) {
            if (ProcRank == 0) {
                System.out.println("Number of processes must be a perfect square!");
            }
        }
        else {
            if (ProcRank == 0)
                System.out.println("Parallel matrix multiplication program!");
            CreateGridCommunicators();
            ProcessInitialization (data);
            DataDistribution(data.pAMatrix, data.pBMatrix, data.pMatrixAblock, data.pBblock, data.Size[0],
                    data.BlockSize);
            ParallelResultCalculation(data.pAblock, data.pMatrixAblock, data.pBblock,
                    data.pCblock, data.BlockSize);
            ResultCollection(data.pCMatrix, data.pCblock, data.Size[0], data.BlockSize);
        }
        if(ProcRank == 0) System.out.println(Arrays.toString(data.pCMatrix));
        MPI.Finalize();
    }

}
