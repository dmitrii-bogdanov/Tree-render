package bogdanov.treerender.utils;

import bogdanov.treerender.exceptions.EmptyInputFileException;
import bogdanov.treerender.exceptions.IncorrectFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Renderer {

    private final File inputFile;
    private final File outputFile;

    public Renderer(String inputFileName, String outputFileName) {
        inputFile = new File(inputFileName);
        outputFile = new File(outputFileName);
    }

    public void run() {
        Reader reader = new Reader(inputFile);
        try {
            reader.read();
        } catch (FileNotFoundException | EmptyInputFileException | IncorrectFormatException e) {
            System.out.println(e.getMessage());
        }

        Writer writer = new Writer(outputFile);
        try {
            writer.write(reader.getTreeString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}
