package org.gross.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    private FileUtils() {
    }

    public static void writeToFile(List<String> fileContent, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(String.join("\n", fileContent));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred when tried save to the file", e);
        }
    }

}
