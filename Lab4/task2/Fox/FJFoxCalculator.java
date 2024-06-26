package task2.Fox;

import Tools.MatrixEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.ForkJoinPool;

@Getter
@Setter
@RequiredArgsConstructor
public class FJFoxCalculator {
    private MatrixEntity matrixEntity1;
    private MatrixEntity matrixEntity2;
    private int threadsCount;
    private MatrixEntity resultMatrix;

    public FJFoxCalculator(MatrixEntity matrixEntity1, MatrixEntity matrixEntity2, int threadsCount) {
        this.matrixEntity1 = matrixEntity1;
        this.matrixEntity2 = matrixEntity2;
        this.resultMatrix = new MatrixEntity(matrixEntity1.getRowsSize(), matrixEntity2.getColumnsSize());

        if (threadsCount > matrixEntity1.getRowsSize() * matrixEntity2.getColumnsSize() / 4) {
            this.threadsCount = matrixEntity1.getRowsSize() * matrixEntity2.getColumnsSize() / 4;
        } else {
            this.threadsCount = Math.max(threadsCount, 1);
        }
    }

    public MatrixEntity multiplyMatrix() {
        var step = (int) Math.ceil(1.0 * matrixEntity1.getRowsSize() / (int) Math.sqrt(threadsCount));

        ForkJoinPool pool = new ForkJoinPool(threadsCount);
        System.out.println("threads count: " + pool.getParallelism());

        for (int i = 0; i < matrixEntity1.getRowsSize(); i += step) {
            for (int j = 0; j < matrixEntity2.getColumnsSize(); j += step) {
                pool.invoke(new FJFoxCalculatorTask(matrixEntity1, matrixEntity2, i, j, step, resultMatrix));
            }
        }

        pool.shutdown();

        return resultMatrix;
    }

}
