package util;

import java.io.*;
import java.nio.file.Files;

public class HttpUtil {

    public String extractPath(String line) throws IOException {
        if (line == null) return "";
        String[] tokens = line.split(" ");
        return tokens[1];
    }

    public byte[] makeBody(String path) throws IOException {
        return Files.readAllBytes(new File("./webapp" + path).toPath());
    }
}
