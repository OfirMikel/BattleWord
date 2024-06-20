import java.io.File;
import java.io.FileNotFoundException;
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
        int startIndex = lengthOfWordsMaps[wordToFit.length-1];
        int endIndex = lengthOfWordsMaps[wordToFit.length] - 1;
        System.out.println(startIndex + " " + endIndex);

        return word;
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
