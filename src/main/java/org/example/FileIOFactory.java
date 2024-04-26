package org.example;

public class FileIOFactory {
    private FileIOFactory() {}

    public static FileIO getFile(String fileName) {
        return new FileIO(fileName);
    }
}
