public class NeighborDetails {
    int[][] crossings;
    NeighborDetails(int wordLength){
        crossings = new int[wordLength][2];
    }

    public void setCrossing(int wordId, int indexOfCrossInTheOtherWord, int indexOfCharInMyWord) {
        crossings[indexOfCharInMyWord][0] = wordId;
        crossings[indexOfCharInMyWord][1] = indexOfCrossInTheOtherWord;
    }

    /**
     *
     * @param indexOfCharInMyWord
     * @return an array with the length of 2 = [Crossed word id (int) , indexOfCrossInTheOtherWord (int) ]
     */
    public int[] getCrossing(int indexOfCharInMyWord) {
        return crossings[indexOfCharInMyWord];
    }
}
