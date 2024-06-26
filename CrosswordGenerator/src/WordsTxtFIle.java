import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class WordsTxtFIle {

    public String[] words;
    public int[] lengthOfWordsMaps;
    public int maxWordLength = 16;
    private int length = 10062;

    public WordsTxtFIle() {
        words = new String[length];
        lengthOfWordsMaps = new int[maxWordLength];
    }


    public char[] getWord(char[] wordToFit){
        char[] word = new char[wordToFit.length];
        int startIndex = lengthOfWordsMaps[wordToFit.length];
        int endIndex = lengthOfWordsMaps[wordToFit.length+ 1] -1 ;
        ArrayList<String> wordsThatFit = getAllWordsThatFit(wordToFit , startIndex , endIndex);
        if (wordsThatFit.isEmpty())
            return "not exist".toCharArray();

        Random rand = new Random();

        return wordsThatFit.get(rand.nextInt(wordsThatFit.size())).toCharArray();
    }

    public  ArrayList<String> getWordsThatValid(char[] currentWord){
        int startIndex = lengthOfWordsMaps[currentWord.length];
        int endIndex = lengthOfWordsMaps[currentWord.length+ 1] -1 ;

        return getAllWordsThatFit(currentWord , startIndex , endIndex);

    }

    private boolean containSubString(String word, String wordToFit){
        for (int i = 0; i < word.length(); i++) {
          if (!(word.charAt(i) == wordToFit.charAt(i) || wordToFit.charAt(i) == ' ')) {
            return false;
          }
        }
        return true;
    }

    private ArrayList<String> getAllWordsThatFit (char[] wordToFit, int startIndex, int endIndex){
        ArrayList<String> wordsThatFit = new ArrayList<>();
        for(int i = startIndex ; i < endIndex ; i++){
            String word = words[i];
            String wordFit = String.valueOf(wordToFit);
            if(containSubString(word, wordFit)){
                wordsThatFit.add(word);
            }
        }
        return wordsThatFit;
    }


    public void initializeWordArrayFromFile(String path){
        File file = new File(path);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        int currentWordLength = 0;
        while (fileScanner.hasNextLine() && i < words.length - 1) {
            words[i] = fileScanner.nextLine();
            if (i >=1 && words[i - 1].length() < words[i].length()){
                currentWordLength = words[i].length();
                lengthOfWordsMaps[currentWordLength] = i + 1;
            }
            i++;
        }
        fileScanner.close();


    }



}
