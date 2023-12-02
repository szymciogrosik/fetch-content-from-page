package org.gross.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

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

    public static File getFile(String resourcePath) {
        try {
            return new File(Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(resourcePath)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI, please provide correct one.", e);
        }
    }

}
