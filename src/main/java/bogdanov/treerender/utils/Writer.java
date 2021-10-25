package bogdanov.treerender.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private final File outputFile;

    public Writer(File outputFile) {
        this.outputFile = outputFile;
    }

    public void write(String text) throws IOException {

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(text);
        }

    }

}
