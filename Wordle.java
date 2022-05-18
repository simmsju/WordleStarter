/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 */

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;

public class Wordle {
    private String word;
    private String[] letters;
    public void run() {
        word = WordleDictionary.FIVE_LETTER_WORDS[(int)(Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)].toUpperCase();
        letters = new String[word.length()];
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        System.out.println(word);
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
        int greens = 0;
        for (int i = 0; i < word.length(); i++) {
            letters[i] = word.substring(i, i+1);
        }
        boolean found = false;
        for (int i = 0; i < WordleDictionary.FIVE_LETTER_WORDS.length; i++) {
            if (s.length() == gw.N_COLS && s.equals(WordleDictionary.FIVE_LETTER_WORDS[i].toUpperCase())) {
                found = true;
            }
        }
        if (found) {
            gw.showMessage("In word list");
            for (int i = 0; i < s.length(); i++) {
                if (s.substring(i, i + 1).equals(letters[i])) {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                    gw.setKeyColor(letters[i], gw.CORRECT_COLOR);
                    letters[i] = "-";
                    greens++;
                }
            }
            for (int i = 0; i < s.length(); i++) {
                if(!gw.getSquareColor(gw.getCurrentRow(), i).equals(gw.CORRECT_COLOR)) {
                    for (int j = 0; j < letters.length; j++) {
                        if (s.substring(i, i + 1).equals(letters[j])) {
                            gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                            if(!gw.getKeyColor(letters[j]).equals(gw.CORRECT_COLOR)) {
                                gw.setKeyColor(letters[j], gw.PRESENT_COLOR);
                            }
                            letters[j] = "-";
                            break;
                        } else {
                            gw.setSquareColor(gw.getCurrentRow(), i, gw.MISSING_COLOR);
                            if(!gw.getKeyColor(s.substring(i, i + 1)).equals(gw.CORRECT_COLOR) && !gw.getKeyColor(s.substring(i, i + 1)).equals(gw.PRESENT_COLOR)) {
                                gw.setKeyColor(s.substring(i, i + 1), gw.MISSING_COLOR);
                            }
                        }
                    }
                }
            }
            if (greens == 5) {
                gw.showMessage("You Win!");
            } else if (gw.getCurrentRow() == 5) {
                gw.showMessage("You Lose. The word was: " + word);
            } else {
                gw.setCurrentRow(gw.getCurrentRow() + 1);
            }
        } else {
            gw.showMessage("Not in word list");
        }
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;

}
