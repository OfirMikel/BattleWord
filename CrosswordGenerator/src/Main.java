

public class Main {
    final static int MatrixRows = 20;
    final static int MatrixCols = 20;
    public static void main(String[] args) {
        char[][] matrix = new char[MatrixRows][MatrixCols];
        WordsTxtFIle wordsTxtFIle = new WordsTxtFIle();
        String RelativePath ="C:/Java Code/CrosswordGenerator/src/";
        wordsTxtFIle.initializeWordArrayFromFile(RelativePath + "MostUsed.txt");

        wordsTxtFIle.getWord(new char[]{'H', 'e', 'l'});
//        CrossWordGenerator crossWord = new CrossWordGenerator();
//        crossWord.createCrossWord(matrix, wordsTxtFIle);
    }

}

