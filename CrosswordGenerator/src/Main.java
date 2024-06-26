

public class Main {
    final static int MatrixRows = 14;
    final static int MatrixCols = 14;
    public static void main(String[] args) {
        char[][] matrix = new char[MatrixRows][MatrixCols];
        WordsTxtFIle wordsTxtFIle = new WordsTxtFIle();
        String RelativePath ="C:/Java Code/CrosswordGenerator/src/";
        wordsTxtFIle.initializeWordArrayFromFile(RelativePath + "mostUsed.txt");

        CrossWordGenerator crossWord = new CrossWordGenerator();
        crossWord.createCrossWord(matrix, wordsTxtFIle);
    }

}

