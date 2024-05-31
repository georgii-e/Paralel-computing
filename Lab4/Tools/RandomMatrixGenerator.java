package Tools;

import lombok.Getter;
import lombok.Setter;

public class RandomMatrixGenerator {
    @Getter
    @Setter
    MatrixEntity matrixEntity;

    public MatrixEntity generateRandomMatrix(int rows, int columns) {
        matrixEntity = new MatrixEntity(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrixEntity.set(i, j, (int) (Math.random() * 10));
            }
        }
        return matrixEntity;
    }
}

