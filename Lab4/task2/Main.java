package task2;

import Algorithms.Sequential.SequentialCalculator;
import Tools.MatrixEntity;
import Tools.RandomMatrixGenerator;
import task2.Fox.FJFoxCalculator;

public class Main {
    public static void main(String[] args) {
        RandomMatrixGenerator randomMatrixGenerator = new RandomMatrixGenerator();

        var SIZE = 1000;
//        var matrixEntity = new MatrixEntity(new int[][]{
//                {1, 5},
//                {2, 3},
//                {1, 7}
//        });
//
//        var matrixEntity2 = new MatrixEntity(new int[][]{
//                {1, 2, 3, 7},
//                {5, 2, 8, 1}
//        });

        var matrixEntity3 = new MatrixEntity(
                randomMatrixGenerator
                        .generateRandomMatrix(SIZE, SIZE)
                        .getMatrix());

        var matrixEntity4 = new MatrixEntity(
                randomMatrixGenerator
                        .generateRandomMatrix(SIZE, SIZE)
                        .getMatrix());

//        System.out.println("Matrix 3:");
//        matrixEntity3.print2D();
//        System.out.println("####");
//
//        System.out.println("Matrix 4:");
//        matrixEntity4.print2D();
//        System.out.println("####");

        var sequentialCalculator = new SequentialCalculator();
        var start = System.currentTimeMillis();
        var res = new MatrixEntity(sequentialCalculator.multiplyMatrix(matrixEntity3, matrixEntity4).getMatrix());
        System.out.println("sequential result:");
        System.out.println(System.currentTimeMillis() - start + " ms");
//        res.print2D();

        var foxCalculator = new FJFoxCalculator(matrixEntity3, matrixEntity4, 6);
        start = System.currentTimeMillis();
        System.out.println("fox result:");
        var res3 = new MatrixEntity(foxCalculator.multiplyMatrix().getMatrix());
        System.out.println(System.currentTimeMillis() - start + " ms");
//        res3.print2D();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (res.get(i, j) != res3.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
