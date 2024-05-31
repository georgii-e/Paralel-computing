package Algorithms.Sequential;

import Tools.MatrixEntity;

public class SequentialCalculator {
    public MatrixEntity multiplyMatrix(MatrixEntity matrixEntity1, MatrixEntity matrixEntity2) {
        if (matrixEntity1.getColumnsSize() != matrixEntity2.getRowsSize()) {
            throw new IllegalArgumentException("matrices cannot be multiplied because the " +
                    "number of columns of matrix A is not equal to the number of rows of matrix B.");
        }

        var resultMatrix = new MatrixEntity(matrixEntity1.getRowsSize(), matrixEntity2.getColumnsSize());
        for (int i = 0; i < matrixEntity1.getRowsSize(); i++) {
            for (int j = 0; j < matrixEntity2.getColumnsSize(); j++) {
                for (int k = 0; k < matrixEntity1.getColumnsSize(); k++) {
                    resultMatrix.set(i, j, resultMatrix.get(i, j) + matrixEntity1.get(i, k) * matrixEntity2.get(k, j));
                }
            }
        }

        return resultMatrix;
    }

}

