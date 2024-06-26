import java.util.ArrayList;
import java.util.Collections;

public class CrossWordGenerator {
    UtilsMethods utils;
    ArrayList<Word> wordsCol;
    ArrayList<Word> wordsRow;
    ArrayList<Word> wordsAll;

    CrossWordGenerator() {
        utils = new UtilsMethods();
        wordsCol = new ArrayList<>();
        wordsRow = new ArrayList<>();
        wordsAll = new ArrayList<>();
    }

    public void createCrossWord(char[][] matrix, WordsTxtFIle wordsTxtFIle) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 'A';
            }
        }

        MatrixSetTitlePosition matrixSetTitlePosition = new MatrixSetTitlePosition(matrix);
        matrixSetTitlePosition.setMatrix();

        MapMatrixToGraph(matrix);


        wordsAll = new ArrayList<>(wordsCol);
        wordsAll.addAll(wordsRow);

        Collections.sort(wordsAll, new WordComparator());

        for (Word word : wordsAll){
            findAndSetValidWords(word, wordsTxtFIle);
        }

        WaveFunctionCollapse(wordsTxtFIle , nextWordToCollapse());

        for (Word word : wordsAll){
            word.print();
        }



        utils.printCharColoredMatrix(matrix, utils.BLACK);


    }

    public void MapMatrixToGraph(char[][] matrix) {
        MapMatrixToGraphColumn(matrix);
        MapMatrixToGraphRow(matrix);
        for (Word word : wordsRow) {
            findSetCrossingWithWords(word);
        }
    }

    private void findSetCrossingWithWords(Word word) {
        int wordLength = word.myDetails.getMyPosition()[2];
        int wordRow = word.myDetails.getMyPosition()[0];
        int wordCol = word.myDetails.getMyPosition()[1];
        int myId = word.id;
        int wordIndex = 0;
        for (int i = 0; i < wordLength; i++) {
            while (wordIndex < wordsCol.size()) {
                Word currentWord = wordsCol.get(wordIndex);
                int currentWordCol = currentWord.myDetails.getMyPosition()[1];
                int currentWordRow = currentWord.myDetails.getMyPosition()[0];
                int currentWordLength = currentWord.myDetails.getMyPosition()[2];
                if (currentWordCol == (wordCol + i) && wordRow >= currentWordRow
                        && wordRow < currentWordLength + currentWordRow) {
                    word.myNeighbors.setCrossing(currentWord.id, Math.abs(wordRow - currentWordRow), i);
                    currentWord.myNeighbors.setCrossing(myId, i, Math.abs(wordRow - currentWordRow));
                    wordIndex++;
                    break;
                }
                wordIndex++;
            }
        }
    }

    private void MapMatrixToGraphColumn(char[][] matrix) {
        int currentWordLength = 0;
        int id = 0;
        for (int col = 0; col < matrix.length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                while (row < matrix[0].length && matrix[row][col] == utils.EMPTY) {
                    currentWordLength++;
                    row++;
                }
                if (currentWordLength != 0) {
                    Word word = new Word(id, row - currentWordLength, col, currentWordLength);
                    wordsCol.add(word);
                    id += 2;
                }
                currentWordLength = 0;
            }
        }


    }

    private void MapMatrixToGraphRow(char[][] matrix) {
        int currentWordLength = 0;
        int id = 1;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                while (col < matrix[0].length && matrix[row][col] == utils.EMPTY) {
                    currentWordLength++;
                    col++;
                }
                if (currentWordLength != 0) {
                    Word word = new Word(id, row, col - currentWordLength, currentWordLength);
                    wordsRow.add(word);
                    id += 2;
                }
                currentWordLength = 0;
            }
        }

    }

    private void WaveFunctionCollapse(WordsTxtFIle wordsTxtFIle , Word word){
        if (word == null || word.isSet){
            return;
        }

        findAndSetValidWords(word,wordsTxtFIle);
        collapseWord(word, wordsTxtFIle);

    }

    private Word nextWordToCollapse(){
        Word minWord = wordsAll.get(0);
        for (Word word : wordsAll) {
            if (!word.isSet && word.getPossibleWords().size() < minWord.getPossibleWords().size()){
                minWord = word;
            }
        }
        return minWord;
    }

    private void collapseWord(Word word , WordsTxtFIle wordsTxtFIle){
        if (word.isSet)
            return;

        word.setWordFromPossibleWords();
        word.isSet = true;
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add(word.myDetails.getWord());
        word.setPossibleWords(arrayList);
        propagateWord(word , wordsTxtFIle);
    }

    private void propagateWord(Word word , WordsTxtFIle wordsTxtFIle) {
        if (word == null)
            return;
        for (int i = 0; i < word.myNeighbors.crossings.length; i++) {
            int[] neighbor = word.myNeighbors.getCrossing(i);
            int id = neighbor[0];
            int indexInOtherWord = neighbor[1];
            Word neighborWord = getWordByID(id);
            char toPutInOtherWord = word.myDetails.getCharArray()[i];
            if (neighborWord == null){
                return;
            }
            neighborWord.myDetails.setCharInWord(toPutInOtherWord , indexInOtherWord);
            findAndSetValidWords(neighborWord, wordsTxtFIle);
        }
    }

    private void findAndSetValidWords(Word word ,WordsTxtFIle wordsTxtFIle){
        ArrayList<String> possibleWords = wordsTxtFIle.getWordsThatValid(word.myDetails.getCharArray());
        word.setPossibleWords(possibleWords);
    }

    private Word getWordByID(int id){
        for (Word word : wordsAll) {
            if(word.id == id)
                return word;
        }
        return null;
    }
}
