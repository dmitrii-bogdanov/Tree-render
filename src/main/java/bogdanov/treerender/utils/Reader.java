package bogdanov.treerender.utils;

import bogdanov.treerender.exceptions.EmptyInputFileException;
import bogdanov.treerender.exceptions.IncorrectFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    private final File fileToRead;
    private String treeString = "";

    private static final char SPACE = ' ';
    private static final char OPENING_BRACKET = '(';
    private static final char CLOSING_BRACKET = ')';

    public Reader(File fileToRead) {
        this.fileToRead = fileToRead;
    }

    public String getTreeString() {
        return treeString;
    }

    public boolean isEmpty() {
        return treeString.isEmpty();
    }

    public void read() throws FileNotFoundException {
        String line;
        try (Scanner scanner = new Scanner(fileToRead)) {
            if (scanner.hasNextLine()) {
                line = scanner.nextLine();
            } else {
                throw new EmptyInputFileException();
            }
        }

        readLine(line);
    }

    private void readLine(String line) {
        int length = line.length();
        if ((line.charAt(0) != OPENING_BRACKET) || (line.charAt(length - 1) != CLOSING_BRACKET) || length <= 2) {
            throw new IncorrectFormatException();
        }
        line = line.substring(1, length - 1);
        length -= 2;

        StringBuilder result = new StringBuilder();

        char c;
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int brackets = 0;

        while (index < length) {
            c = line.charAt(index++);

            if (isNumber(c)) {
                sb.append(c);
            } else if (c == SPACE) {
                append(result, sb, brackets);
                sb.delete(0, sb.length());
            } else if (c == OPENING_BRACKET) {
                ++brackets;
            } else if (c == CLOSING_BRACKET) {
                if (sb.length() > 0) {
                    append(result, sb, brackets);
                    sb.delete(0, sb.length());
                }
                --brackets;
            } else {
                throw new IncorrectFormatException("Illegal symbol at index " + index);
            }
        }

        if (brackets != 0) {
            throw new IncorrectFormatException("Incorrect brackets usage");
        }

        result.deleteCharAt(result.length() - 1);
        treeString = result.toString();

        if (treeString.isEmpty()) {
            throw new EmptyInputFileException("No number was found");
        }
    }

    private void append(StringBuilder result, StringBuilder sbToAppend, int brackets) {
        if (sbToAppend.length() > 0) {
            for (int i = 0; i < brackets; i++) {
                result.append('\t');
            }
            result.append(getNumber(sbToAppend));
            result.append('\n');
            sbToAppend.delete(0, sbToAppend.length());
        }
    }

    private boolean isNumber(char c) {
        return (c >= '0') && (c <= '9');
    }

    private int getNumber(StringBuilder sb) {
        return Integer.parseInt(sb.toString());
    }


}
