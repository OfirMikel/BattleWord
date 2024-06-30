

public class Main {
    final static int MatrixRows = 11;
    final static int MatrixCols = 11;
    public static void main(String[] args) {
        char[][] matrix = new char[MatrixRows][MatrixCols];
        WordsTxtFIle wordsTxtFIle = new WordsTxtFIle();
        String RelativePath ="C:/Java Code/CrosswordGenerator/src/";
        wordsTxtFIle.initializeWordArrayFromFile(RelativePath + "mostUsed.txt");

        boolean flag = false;
        while (!flag) {
            CrossWordGenerator crossWord = new CrossWordGenerator();
            flag = crossWord.CreateCrossWord(matrix, wordsTxtFIle);
        }
    }
}

