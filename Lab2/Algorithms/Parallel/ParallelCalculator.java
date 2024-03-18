package Algorithms.Parallel;

import Tools.MatrixEntity;

import java.util.ArrayList;


public class ParallelCalculator {

    public MatrixEntity multiplyMatrix(MatrixEntity matrixEntity1, MatrixEntity matrixEntity2, int threadsCount) {


        if (matrixEntity1.getColumnsSize() != matrixEntity2.getRowsSize()) {
            throw new IllegalArgumentException("matrices cannot be multiplied because the " +
                    "number of columns of matrix A is not equal to the number of rows of matrix B.");
        }

        var height = matrixEntity1.getRowsSize();
        var width = matrixEntity2.getColumnsSize();
        var resultMatrix = new MatrixEntity(height, width);


        var rowsPerThread = height / threadsCount;
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < threadsCount; i++) {
            var from = i * rowsPerThread;
            int to;

            if (i == threadsCount - 1) {
                to = height;
            } else {
                to = (i + 1) * rowsPerThread;
            }
            threads.add(new Thread(() -> {
                for (int row = from; row < to; row++) {
                    for (int col = 0; col < width; col++) {
                        for (int k = 0; k < matrixEntity2.getRowsSize(); k++) {
                            resultMatrix.set(row, col, resultMatrix.get(row, col)
                                    + matrixEntity1.get(row, k) * matrixEntity2.get(k, col));
                        }
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultMatrix;
    }

}

