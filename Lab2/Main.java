import Algorithms.Fox.FoxCalculator;
import Algorithms.Sequential.SequentialCalculator;
import Tools.MatrixEntity;
import Tools.MatrixGenerator;

public class Main {
    public static void main(String[] args) {
        MatrixGenerator matrixGenerator = new MatrixGenerator();

        var SIZE = 5;

        var matrixEntity1 = new MatrixEntity(
                matrixGenerator
                        .generateRandomMatrix(SIZE, SIZE)
                        .getMatrix());

        var matrixEntity2 = new MatrixEntity(
                matrixGenerator
                        .generateRandomMatrix(SIZE, SIZE)
                        .getMatrix());

        System.out.println("Matrix 3:");
        matrixEntity1.print2D();
        System.out.println("####");

        System.out.println("Matrix 4:");
        matrixEntity2.print2D();
        System.out.println("####");

        var sequentialCalculator = new SequentialCalculator();
        var res = new MatrixEntity(sequentialCalculator.multiplyMatrix(matrixEntity1, matrixEntity2).getMatrix());
        System.out.println("sequential result:");
        res.print2D();

        var parallelCalculator = new Algorithms.Parallel.ParallelCalculator();
        System.out.println("parallel result:");
        var res2 = new MatrixEntity(parallelCalculator.multiplyMatrix(matrixEntity1, matrixEntity2, 4).getMatrix());
        res2.print2D();

        var foxCalculator = new FoxCalculator(matrixEntity1, matrixEntity2, 4);
        System.out.println("fox result:");
        var res3 = new MatrixEntity(foxCalculator.multiplyMatrix().getMatrix());
        res3.print2D();
    }

}
