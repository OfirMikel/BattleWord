import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

//    public static final String GREEN = "\033[0;32m";
//    public static final String YELLOW = "\033[0;33m";
//    public static final String BLUE = "\033[0;34m";
//    public static final String MAGENTA = "\033[0;35m";
//    public static final String CYAN = "\033[0;36m";
//    public static final String WHITE = "\033[0;37m";
//    public static final String BRIGHT_RED = "\033[0;91m";
//    public static final String BRIGHT_GREEN = "\033[0;92m";
//    public static final String BRIGHT_YELLOW = "\033[0;93m";
//    public static final String BRIGHT_BLUE = "\033[0;94m";
//    public static final String BRIGHT_MAGENTA = "\033[0;95m";
//    public static final String BRIGHT_CYAN = "\033[0;96m";
//    public static final String BRIGHT_WHITE = "\033[0;97m";

public class UtilsMethods {
    public char BLACK = '+';
    public char EMPTY = 'A';
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";

    /**
     * Returns the amount of empty space bellow a given location in the array
     *
     * @param matrix the int matrix
     * @param row    the current row of the location
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    public int AmountOfSpaceBellowMe(char[][] matrix, int row, int col) {
        return AmountOfSpaceBellowMeHelper(matrix, row + 1, col);
    }

    /**
     * Helper method for AmountOfSpaceBellowMe
     *
     * @param matrix the int matrix
     * @param row    the current row + 1
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    private int AmountOfSpaceBellowMeHelper(char[][] matrix, int row, int col) {
        if (row >= matrix.length || row < 0 || col >= matrix[0].length || col < 0) {
            return 0;
        }
        if (matrix[row][col] == BLACK) {
            return 0;
        }
        return 1 + AmountOfSpaceBellowMeHelper(matrix, row + 1, col);
    }

    /**
     * Returns the amount of empty space bellow a given location in the array
     *
     * @param matrix the int matrix
     * @param row    the current row of the location
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    public int AmountOfSpaceRightMe(char[][] matrix, int row, int col) {
        return AmountOfSpaceRightMeHelper(matrix, row, col + 1);
    }

    /**
     * Helper method for AmountOfSpaceBellowMe
     *
     * @param matrix the int matrix
     * @param row    the current row + 1
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    private int AmountOfSpaceRightMeHelper(char[][] matrix, int row, int col) {
        if (row >= matrix.length || row < 0 || col >= matrix[0].length || col < 0) {
            return 0;
        }
        if (matrix[row][col] == BLACK) {
            return 0;
        }
        return 1 + AmountOfSpaceRightMeHelper(matrix, row, col + 1);
    }

    /**
     * Returns the amount of empty space bellow a given location in the array
     *
     * @param matrix the int matrix
     * @param row    the current row of the location
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    public int AmountOfSpaceLeftMe(char[][] matrix, int row, int col) {
        return AmountOfSpaceLeftMeHelper(matrix, row, col - 1);
    }

    /**
     * Helper method for AmountOfSpaceBellowMe
     *
     * @param matrix the int matrix
     * @param row    the current row + 1
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    private int AmountOfSpaceLeftMeHelper(char[][] matrix, int row, int col) {
        if (row >= matrix.length || row < 0 || col >= matrix[0].length || col < 0) {
            return 0;
        }
        if (matrix[row][col] == BLACK) {
            return 0;
        }
        return 1 + AmountOfSpaceLeftMeHelper(matrix, row, col - 1);
    }

    /**
     * Returns the amount of empty space bellow a given location in the array
     *
     * @param matrix the int matrix
     * @param row    the current row of the location
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    public int AmountOfSpaceTopMe(char[][] matrix, int row, int col) {
        return AmountOfSpaceTopMeHelper(matrix, row - 1, col);
    }

    /**
     * Helper method for AmountOfSpaceBellowMe
     *
     * @param matrix the int matrix
     * @param row    the current row + 1
     * @param col    the current col of the location
     * @return the amount of cells that aren't black below a value.
     */
    private int AmountOfSpaceTopMeHelper(char[][] matrix, int row, int col) {
        if (row >= matrix.length || row < 0 || col >= matrix[0].length || col < 0) {
            return 0;
        }
        if (matrix[row][col] == BLACK) {
            return 0;
        }
        return 1 + AmountOfSpaceLeftMeHelper(matrix, row - 1, col);
    }

    /**
     * give the amount of spaces to the left , right , top , bottom
     *
     * @param matrix given matrix
     * @param row    the row of the current position
     * @param col    the column of the current position
     * @return LocationAround calss elemnt that represent the amount of spaces to the left , right , top , bottom of a position
     */
    public LocationAroundPoint getEmptySpaceSurroundingMe(char[][] matrix, int row, int col) {
        int left = AmountOfSpaceLeftMe(matrix, row, col);
        int right = AmountOfSpaceRightMe(matrix, row, col);
        int top = AmountOfSpaceTopMe(matrix, row, col);
        int bottom = AmountOfSpaceBellowMe(matrix, row, col);
        return new LocationAroundPoint(right, left, top, bottom);
    }

    /**
     * print the matrix
     *
     * @param matrix the matrix that need to be printed
     */
    public void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }


    public void printMatrixColoredLocation(char[][] matrix, int highlightRow, int highlightCol) {
        String RESET = "\033[0m";
        String BRIGHT_GREEN = "\033[0;92m";
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (row == highlightRow && col == highlightCol) {
                    System.out.print(BRIGHT_GREEN + matrix[row][col] + RESET + " ");
                } else {
                    System.out.print(matrix[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public void printCharColoredMatrix(char[][] matrix, char targetChar) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == targetChar) {
                    System.out.print(RED + matrix[row][col] + RESET + " ");
                } else {
                    System.out.print(matrix[row][col] + " ");
                }
            }
            System.out.println();
        }
    }


    public static void sortFile(String inputPath, String outputPath) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(inputPath));

        lines.replaceAll(String::toLowerCase);

        lines.sort(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));

        Files.write(Path.of(outputPath), lines);

    }

}
