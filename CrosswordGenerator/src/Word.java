import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Word {
    public WordDetails myDetails;
    public NeighborDetails myNeighbors;
    public ArrayList<String> possibleWords;
    int id = 0;
    public boolean isSet = false;

    public Word(int id,int row, int col , int wordLength) {
        this.id = id;
        this.myDetails = new WordDetails(row, col , wordLength);
        this.myNeighbors = new NeighborDetails(wordLength);
        this.possibleWords = new ArrayList<>();
    }

    public ArrayList<String> getPossibleWords() {
        return possibleWords;
    }

    public void setPossibleWords(ArrayList<String> possibleWords) {
        this.possibleWords = possibleWords;
    }
    public void setWordFromPossibleWords() {
        if (possibleWords == null || possibleWords.size() < 1) {
            return;
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(possibleWords.size());
        char[] randomWord = possibleWords.get(randomIndex).toCharArray();
        myDetails.setWord(randomWord);
    }
    public void print(){
        String GREEN = "\033[0;32m";
        String BLUE = "\033[0;34m";
        String RESET = "\033[0m";
        String RED = "\033[0;31m";

        int[] myPos = myDetails.getMyPosition();
        int row = myPos[0];
        int col = myPos[1];
        int myLength = myPos[2];
        System.out.println("ID:" + id +"\tMy Word: " + GREEN +  myDetails.getWord() + RESET +  "\n\tMy Starting Location: " + BLUE +"[" + row + ", " + col + "]" + RESET +
                "\tMy Length: "+ BLUE + myLength  + RESET +"\n\tmyNeighbors: ");
        for (int i = 0; i < myNeighbors.crossings.length; i++) {
            int[] neighbor = myNeighbors.getCrossing(i);
            System.out.print("\t\tChar Position In My Word:" +BLUE+ i + RESET + "\tCrossed word id: " + BLUE + neighbor[0] +
                    RESET + "\tChar Position In Neighbor Word: " + BLUE + neighbor[1] +RESET + "\n" );
        }
        if (possibleWords == null)
            return;
        System.out.print("\n\t\t Possible Words: ");
        System.out.println(possibleWords);

    }
}

class WordComparator implements Comparator<Word>
{
    @Override
    public int compare(Word o1, Word o2) {
        return o2.myDetails.getWordLength() - o1.myDetails.getWordLength();
    }
}
