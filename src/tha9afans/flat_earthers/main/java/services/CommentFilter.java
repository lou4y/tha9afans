package services;

import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class CommentFilter {
    private static HashSet<String> inappropriateWords = new HashSet<>();

    static {
        // Read inappropriate words from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                inappropriateWords.add(line.trim());
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isProfane(String word) {
        // Check if word is in set of inappropriate words
        return inappropriateWords.contains(word.toLowerCase());
    }

    public static void applyFilter(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            for (String word : newValue.split("\\s+")) {
                if (isProfane(word)) {
                    StringBuilder filteredText = new StringBuilder(newValue.length());
                    int i = 0;
                    while (i < newValue.length()) {
                        int j = newValue.indexOf(word, i);
                        if (j == -1) {
                            filteredText.append(newValue.substring(i));
                            break;
                        } else {
                            filteredText.append(newValue.substring(i, j));
                            filteredText.append("*".repeat(word.length()));
                            i = j + word.length();
                        }
                    }
                    JOptionPane.showMessageDialog(null,
                            "Please stop writing inappropriate language. Your comment has been filtered.");
                    textField.setText(filteredText.toString());
                    break;
                }
            }
        });
    }
}
