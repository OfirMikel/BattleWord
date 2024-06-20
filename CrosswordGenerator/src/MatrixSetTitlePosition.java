import java.util.Random;

public class MatrixSetTitlePosition {
    char[][] matrix;
    int maxValue;
    char BLACK;
    char EMPTY = 'A';
    UtilsMethods utilsMethods = new UtilsMethods();

    public MatrixSetTitlePosition(char[][] matrix) {
        this.matrix = matrix;
        maxValue = (int) (this.matrix.length * 0.3);
        BLACK = utilsMethods.BLACK;
        matrix[0][0] = BLACK;
    }

    private void setFirstRowColumn() {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            System.out.println("The matrix is empty");
            return;
        }
        Random rand = new Random();
        for (int j = 1; j < matrix.length; j++) {

            int randomVal = rand.nextInt(matrix.length);
            LocationAroundPoint locationAroundPoint = utilsMethods.getEmptySpaceSurroundingMe(matrix, 0, j);
            if (randomVal <= (matrix.length * 0.6) || locationAroundPoint.left > 0) {
                matrix[0][j] = BLACK;
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            int randomVal = rand.nextInt(matrix.length);
            LocationAroundPoint locationAroundPoint = utilsMethods.getEmptySpaceSurroundingMe(matrix, i, 0);

            if (i == 1 && matrix[0][1] == BLACK)
                continue;
            if (randomVal <= (matrix.length * 0.3) || locationAroundPoint.top > 0) {
                matrix[i][0] = BLACK;
            }
        }
    }

    public void setMatrix() {
        Random rand = new Random();
        setFirstRowColumn();

        for (int i = 1; i < matrix[0].length; i++) {
            int blacksInRow = 0;
            for (int j = 1; j < matrix.length; j++) {
                if (blacksInRow >= maxValue) {
                    continue;
                }
                int randomVal = rand.nextInt(matrix.length);
                LocationAroundPoint locationAroundPoint = utilsMethods.getEmptySpaceSurroundingMe(matrix, i, j);

                if (randomVal <= maxValue && (locationAroundPoint.left > 0 || locationAroundPoint.top > 0) && matrix[i][j] != BLACK) {
                    matrix[i][j] = BLACK;
                    blacksInRow++;
                }
                if (blacksInRow < maxValue / 2 && j == matrix.length - 1) {
                    j = 1;
                }
            }

        }
        for (int i = 0; i < 5; i++) {
            validateMatrix();
        }
    }

    private void validateMatrixDeleteNeighers(int row, int col) {
        if (row == 0) {
            matrix[1][col] = EMPTY;
            return;
        }

        if (col == 0) {
            matrix[row][1] = EMPTY;
            return;
        }

        matrix[row][col] = EMPTY;
        matrix[row -1][col] = EMPTY;
        matrix[row][col - 1] = EMPTY;

    }

    private void validateMatrix() {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                LocationAroundPoint locationAroundPoint = utilsMethods.getEmptySpaceSurroundingMe(matrix, i, j);
                int count = 0;
                if (locationAroundPoint.left == 0)
                    count++;
                if (locationAroundPoint.right == 0)
                    count++;
                if (locationAroundPoint.top == 0)
                    count++;
                if (locationAroundPoint.bottom == 0)
                    count++;

                if (count == 4) {
                    validateMatrixDeleteNeighers(i, j);
                    continue;
                }
                if (i == 0 || j == 0) {
                    if (count == 3)
                        validateMatrixDeleteNeighers(i, j);
                    continue;
                }
                if (count >= 2) {
                    matrix[i][j] = EMPTY;
                }
            }
        }
    }
}
