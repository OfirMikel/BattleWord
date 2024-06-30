import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class CrossWordGenerator {
    UtilsMethods utils;
    ArrayList<Word> wordsCol;
    ArrayList<Word> wordsRow;
    ArrayList<Word> wordsAll;

    public static final String BRIGHT_RED = "\033[0;91m";
    public static final String BRIGHT_GREEN = "\033[0;92m";
    public static final String BRIGHT_BLUE = "\033[0;94m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";

    CrossWordGenerator() {
        utils = new UtilsMethods();
        wordsCol = new ArrayList<>();
        wordsRow = new ArrayList<>();
        wordsAll = new ArrayList<>();
    }

    public boolean CreateCrossWord(char[][] matrix, WordsTxtFIle wordsTxtFIle)
    {
        if (BuildCrossWord(matrix, wordsTxtFIle)) {
            printGraph(wordsAll);

            graphToMatrix(matrix, wordsAll);
            utils.printCharColoredMatrix(matrix, utils.BLACK);
            return true;
        }
        return false;
    }

    private boolean BuildCrossWord(char[][] matrix, WordsTxtFIle wordsTxtFIle) {
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

        for (Word word : wordsAll) {
            findAndSetValidWords(word, wordsTxtFIle);
        }

        Stack<Integer> path = new Stack<>();

        return waveFunctionCollapse(wordsTxtFIle, (ArrayList<Word>) wordsAll.clone(), path, matrix, 0);
    }

    private void printGraph(ArrayList<Word> graph) {
        for (Word word : graph) {
            word.printWordWithoutNeighbors();
        }
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

    private void graphToMatrix(char[][] matrix, ArrayList<Word> wordArrayList) {
        for (Word word : wordArrayList) {
            if (word.id % 2 == 1) {
                setWordInMatrix(word, matrix);
            }
        }
    }

    private void setWordInMatrix(Word word, char[][] matrix) {
        int row = word.myDetails.getMyPosition()[0];
        int col = word.myDetails.getMyPosition()[1];
        int wordLength = word.myDetails.getMyPosition()[2];
        for (int i = 0; i < wordLength; i++) {
            if (matrix[row][col + i] == utils.BLACK) {
                continue;
            }
            matrix[row][col + i] = word.myDetails.getCharArray()[i];
        }
    }

    private boolean waveFunctionCollapse(WordsTxtFIle wordsTxtFIle, ArrayList<Word> graph, Stack<Integer> path, char[][] matrix, int i) {
        if (i >= 2500) {
            System.out.println(BRIGHT_RED + "FAIL" + RESET);
            return false;
        }
        Word word = !path.isEmpty() ? getWordByID(path.peek()) : nextWordToCollapse(graph);
        Word nextWord = word;

        while (waveFunctionIterateWord(wordsTxtFIle, nextWord)) {
            path.push(nextWord.id);
            word = nextWord;
            nextWord = nextWordToCollapse(graph);
        }
        if (graphIsFilled(graph)) {
            wordsAll = graph;
            return true;
        }

        reCall(word, wordsTxtFIle);
        word.isSet = false;
        if (!path.isEmpty()) {
            path.pop();
        }
        return waveFunctionCollapse(wordsTxtFIle, graph, path, matrix, i + 1);
    }

    private boolean waveFunctionIterateWord(WordsTxtFIle wordsTxtFIle, Word word) {
        if (word == null || word.possibleWords.isEmpty()) {
            return false;
        }
        collapseWord(word, wordsTxtFIle);
        return true;
    }

    private Word nextWordToCollapse(ArrayList<Word> graph) {
        int id = Integer.MAX_VALUE;
        int currentLength = Integer.MAX_VALUE;
        for (Word word : graph) {
            if (word.myDetails.getWordLength() <= 1) {
                continue;
            }
            if (!word.isSet && (word.getPossibleWords().size() < currentLength)) {
                currentLength = word.getPossibleWords().size();
                id = word.id;
            }
        }
        if (id == Integer.MAX_VALUE) {
            return null;
        }
        return getWordByID(id);
    }

    private boolean graphIsFilled(ArrayList<Word> graph) {
        for (Word word : graph) {
            if (!word.isSet && word.myDetails.getWordLength() > 1) {
                return false;
            }
        }
        return true;
    }

    private void collapseWord(Word word, WordsTxtFIle wordsTxtFIle) {
        if (word.isSet)
            return;

        word.setWordFromPossibleWords();
        word.possibleWords.remove(word.myDetails.getWord());
        word.isSet = true;
        propagateWord(word, wordsTxtFIle);

    }

    private void reCall(Word word, WordsTxtFIle wordsTxtFIle) {
        if (word == null)
            return;
        for (int i = 0; i < word.myNeighbors.crossings.length; i++) {
            int[] neighbor = word.myNeighbors.getCrossing(i);
            int id = neighbor[0];
            int indexInOtherWord = neighbor[1];
            Word neighborWord = getWordByID(id);
            if (neighborWord == null || neighborWord.isSet) {
                continue;
            }
            word.myDetails.setCharInWord(' ', i);
            neighborWord.myDetails.setCharInWord(' ', indexInOtherWord);
            findAndSetValidWords(neighborWord, wordsTxtFIle);
        }
    }

    private void propagateWord(Word word, WordsTxtFIle wordsTxtFIle) {
        if (word == null)
            return;
        for (int i = 0; i < word.myNeighbors.crossings.length; i++) {
            int[] neighbor = word.myNeighbors.getCrossing(i);
            int id = neighbor[0];
            int indexInOtherWord = neighbor[1];
            Word neighborWord = getWordByID(id);
            char toPutInOtherWord = word.myDetails.getCharArray()[i];
            if (neighborWord == null || neighborWord.isSet) {
                continue;
            }
            neighborWord.myDetails.setCharInWord(toPutInOtherWord, indexInOtherWord);
            findAndSetValidWords(neighborWord, wordsTxtFIle);
        }
    }

    private void findAndSetValidWords(Word word, WordsTxtFIle wordsTxtFIle) {
        ArrayList<String> possibleWords = wordsTxtFIle.getWordsThatValid(word.myDetails.getCharArray());
        word.setPossibleWords(possibleWords);
    }

    private Word getWordByID(int id) {
        for (Word word : wordsAll) {
            if (word.id == id)
                return word;
        }
        return null;
    }

    public void printPath(Stack<Integer> path) {
        int i = 1;
        System.out.println();
        for (int value : path) {
            System.out.print(value);
            if (i < path.size())
                System.out.print(" --> ");
            i++;
        }
        System.out.println();
        for (int value : path) {
            getWordByID(value).printWordWithoutNeighbors();
        }
    }
}