package Algorithms.Fox;

import Tools.MatrixEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FoxCalculator {
    private MatrixEntity matrixEntity1;
    private MatrixEntity matrixEntity2;
    private int threadsCount;
    private MatrixEntity resultMatrix;

    public FoxCalculator(MatrixEntity matrixEntity1, MatrixEntity matrixEntity2, int threadsCount) {
        this.matrixEntity1 = matrixEntity1;
        this.matrixEntity2 = matrixEntity2;
        this.resultMatrix = new MatrixEntity(matrixEntity1.getRowsSize(), matrixEntity2.getColumnsSize());

        if (threadsCount > matrixEntity1.getRowsSize() * matrixEntity2.getColumnsSize() / 4) {
            this.threadsCount = matrixEntity1.getRowsSize() * matrixEntity2.getColumnsSize() / 4;
        } else this.threadsCount = Math.max(threadsCount, 1);
    }

    public MatrixEntity multiplyMatrix() {
        var blockSize = (int) Math.ceil(1.0 * matrixEntity1.getRowsSize() / (int) Math.sqrt(threadsCount));

        FoxCalculatorThread[] threads = new FoxCalculatorThread[threadsCount];
        var threadCounter = 0;

        for (int i = 0; i < matrixEntity1.getRowsSize(); i += blockSize) {
            for (int j = 0; j < matrixEntity2.getColumnsSize(); j += blockSize) {
                threads[threadCounter] = new FoxCalculatorThread(matrixEntity1, matrixEntity2, i, j, blockSize, resultMatrix); // зсув блоку по рядкам і колонкам
                threadCounter++;
            }
        }

        for (int i = 0; i < threadCounter; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadCounter; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultMatrix;
    }

}
