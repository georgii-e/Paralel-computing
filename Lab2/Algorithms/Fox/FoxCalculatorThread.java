package Algorithms.Fox;

import Algorithms.Sequential.SequentialCalculator;
import Tools.MatrixEntity;

public class FoxCalculatorThread extends Thread {
    private final MatrixEntity matrixEntity1;
    private final MatrixEntity matrixEntity2;
    private final int curRowShift;
    private final int curColShift;
    private final int blockSize;
    private final MatrixEntity resultMatrix;

    public FoxCalculatorThread(MatrixEntity matrixEntity1, MatrixEntity matrixEntity2, int curRowShift,
                               int curColShift, int blockSize, MatrixEntity resultMatrix) {
        this.resultMatrix = resultMatrix;
        this.matrixEntity1 = matrixEntity1;
        this.matrixEntity2 = matrixEntity2;
        this.curRowShift = curRowShift;
        this.curColShift = curColShift;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        var m1RowSize = blockSize;
        var m2ColSize = blockSize;

        if (curRowShift + blockSize > matrixEntity1.getRowsSize())
            m1RowSize = matrixEntity1.getRowsSize() - curRowShift;

        if (curColShift + blockSize > matrixEntity2.getColumnsSize())
            m2ColSize = matrixEntity2.getColumnsSize() - curColShift;

        for (int k = 0; k < matrixEntity1.getRowsSize(); k += blockSize) {
            var m1ColSize = blockSize;
            var m2RowSize = blockSize;

            if (k + blockSize > matrixEntity2.getRowsSize()) {
                m2RowSize = matrixEntity2.getRowsSize() - k;
            }

            if (k + blockSize > matrixEntity1.getColumnsSize()) {
                m1ColSize = matrixEntity1.getColumnsSize() - k;
            }

            var blockFirst = copyBlock(matrixEntity1, curRowShift, curRowShift + m1RowSize,
                    k, k + m1ColSize);
            var blockSecond = copyBlock(matrixEntity2, k, k + m2RowSize,
                    curColShift, curColShift + m2ColSize);

            var resBlock = new SequentialCalculator().multiplyMatrix(blockFirst, blockSecond);
            for (int i = 0; i < resBlock.getRowsSize(); i++) {
                for (int j = 0; j < resBlock.getColumnsSize(); j++) {
                    resultMatrix.set(i + curRowShift, j + curColShift, resBlock.get(i, j)
                            + resultMatrix.get(i + curRowShift, j + curColShift));
                }
            }
        }
    }

    private MatrixEntity copyBlock(MatrixEntity src, int rowStart, int rowFinish,
                              int colStart, int colFinish) {
        var copyMatrix = new MatrixEntity(rowFinish - rowStart, colFinish - colStart);
        for (int i = 0; i < rowFinish - rowStart; i++) {
            for (int j = 0; j < colFinish - colStart; j++) {
                copyMatrix.set(i, j, src.get(i + rowStart, j + colStart));
            }
        }
        return copyMatrix;
    }
}

