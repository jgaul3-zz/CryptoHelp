import java.util.Scanner;
import java.io.File;

public class Monoalphabetic {

    private  static Scanner userIn = new Scanner(System.in);

    private static void freqAnalysis(String cipherText) {

        int[] tally = new int[26];
        int count = 0;

        for (char cha: cipherText.toCharArray()) {
            int asciiVal = (int) cha;
            if (asciiVal > 64 && asciiVal < 91) {
                tally[asciiVal - 65]++;
                count++;
            }
        }

        for (int i = 0; i < 26; i++) {
            System.out.println((char) (i + 65) + " : " + Math.round(((double) tally[i] / (double) count) * 100)/100.0);
        }
    }


    public static void main(String[] args) throws Exception {
	    System.out.println("Welcome to CryptoHelp!");

        String cipherText = "";

        if (cipherText.isEmpty()) {
            System.out.println("Please enter location of cipher text.");
            String fileName = userIn.next();

            Scanner inputFile = new Scanner(new File(fileName));

            while (inputFile.hasNext()) {
                String toAdd = inputFile.nextLine();

                cipherText += toAdd;
            }
        }

        String clearText = cipherText;

        //Only 52 needed, but people are dumb.
        char[] currentChanges = new char[60];
        int pointer = 0;

        int order = 0;

        while (order != -1) {
            System.out.println("\nCurrent Cleartext:\n" + clearText + "\n");
            System.out.println("Type a number for next action:");
            System.out.println("-1: Quit");
            System.out.println("0: Reset Changes");
            System.out.println("1: Frequency Analysis");
            System.out.println("2: Substitute (old letter new letter, e.g. 'a b')");
            System.out.println("3: Undo last substitution");
            System.out.println("4: Show current changes");

            order = userIn.nextInt();

            if (order == 0) {
                clearText = cipherText;
            } else if (order == 1) {
                freqAnalysis(clearText);
            } else if (order == 2) {
                //Redundancy Check
                char check1 = userIn.next().toUpperCase().charAt(0);
                char check2 = userIn.next().toLowerCase().charAt(0);

                boolean firstOkay = true;
                boolean secondOkay = true;

                for (int i = 0; i < pointer; i = i + 2) {
                    if (currentChanges[i] == check1) {
                        firstOkay = false;
                    }
                }
                for (int i = 1; i < pointer; i = i + 2) {
                    if (currentChanges[i] == check2) {
                        secondOkay = false;
                    }
                }

                if (firstOkay && secondOkay) {
                    currentChanges[pointer] = check1;
                    pointer++;
                    currentChanges[pointer] = check2;
                    pointer++;

                    clearText = clearText.replace(currentChanges[pointer - 2], currentChanges[pointer - 1]);
                }
            } else if (order == 3) {
                if (pointer != 0) {
                    clearText = clearText.replace(currentChanges[pointer - 1], currentChanges[pointer - 2]);
                    pointer = pointer - 2;
                }
            } else if (order == 4) {
                for (int i = 0; i < pointer; i = i + 2) {
                    System.out.println(currentChanges[i] + " -> " + currentChanges[i + 1]);
                }
            }
        }
    }
}
