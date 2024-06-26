public class WordDetails {
    private char[] word;
    private int row;
    private int col;
    private int wordLength;

    public WordDetails(int row, int col, int wordLength) {
        this.row = row;
        this.col = col;
        this.wordLength = wordLength;
        this.word = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            this.word[i] = ' ';
        }
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setWord(char[] word) {
        this.word = word;
    }
    public String getWord(){
        return new String(word);
    }
    public char[] getCharArray(){
        return word;
    }

    public void setCharInWord(char ch , int index) {
        word[index] = ch;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public int[] getMyPosition() {
        return new int[]{row,col,wordLength};
    }
    public int getWordLength(){
        return wordLength;
    }
}
