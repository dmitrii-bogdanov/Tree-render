package bogdanov.treerender.utils;

import bogdanov.treerender.exceptions.EmptyInputFileException;
import bogdanov.treerender.exceptions.IncorrectFormatException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    private final String INPUT_FILE_NAME = "test_input.txt";
    private final String EMPTY_STRING = "";

    private File makeFile(String text) {
        File file = new File(INPUT_FILE_NAME);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Test
    void read_correctFile() {
        String inputText = "(1 (2 (4 5 6 (7))(10))(3))";
        String correctResult = "1\n" +
                "\t2\n" +
                "\t\t4\n" +
                "\t\t5\n" +
                "\t\t6\n" +
                "\t\t\t7\n" +
                "\t\t10\n" +
                "\t3";

        Reader reader = new Reader(makeFile(inputText));
        try {
            reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(correctResult, reader.getTreeString());
    }

    @Test
    void read_emptyFile() {
        Reader reader = new Reader(makeFile(EMPTY_STRING));

        assertThrows(EmptyInputFileException.class, () -> reader.read());
    }

    @Test
    void read_illegalSymbol() {
        String inputText = "(1 (2 x(4 5 6 (7))(10))(3))";

        Reader reader = new Reader(makeFile(inputText));
        assertThrows(IncorrectFormatException.class, () -> reader.read());
    }

    @Test
    void read_bracketsError() {
        String inputText = "(1 (2 4 5 6 (7))(10))(3))";

        Reader reader = new Reader(makeFile(inputText));
        assertThrows(IncorrectFormatException.class, () -> reader.read());
    }

    @Test
    void read_notExistingFile() {
        File file = new File("some filename for test.txt");
        assertFalse(file.exists());

        Reader reader = new Reader(file);
        assertThrows(FileNotFoundException.class, () -> reader.read());
    }

    @Test
    void read_noStartingOpeningBracket() {
        String inputText = "1 (2 (4 5 6 (7))(10))(3))";

        Reader reader = new Reader(makeFile(inputText));
        assertThrows(IncorrectFormatException.class, () -> reader.read());
    }

    @Test
    void read_noFinishingClosingBracket() {
        String inputText = "(1 (2 (4 5 6 (7))(10))(3";

        Reader reader = new Reader(makeFile(inputText));
        assertThrows(IncorrectFormatException.class, () -> reader.read());
    }

    @Test
    void read_onlyBrackets() {
        String inputText = "()";

        Reader reader = new Reader(makeFile(inputText));
        assertThrows(IncorrectFormatException.class, () -> reader.read());
    }

}