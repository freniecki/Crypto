package org.example;

import java.io.*;
import java.util.logging.Logger;

public class FileIO {

    private final String fileName;

    static Logger logger = Logger.getLogger(FileIO.class.getName());

    private FileIO(String fileName) {
        this.fileName = fileName;
    }

    public static FileIO getFile(String fileName) {
        return new FileIO(fileName);
    }

    public byte[] read() {
        String filePath = new File(fileName).getAbsolutePath();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            int data;
            while ((data = reader.read()) != -1) {
                outputStream.write(data);
            }

        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException");
        } catch (IOException e) {
            logger.info("IOException");
        }

        return outputStream.toByteArray();
    }

    public void write(byte[] message) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);
        File file = new File(filePath);

        try {
            if (file.exists()) {
                logger.info("file exist");
            } else {
                if (file.createNewFile()) {
                    logger.info("file created");
                } else {
                    logger.info("cannot create file");
                }
            }
        } catch (IOException e) {
            logger.info("error when creating file");
        }

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
            outputStream.write(message);
            logger.info("written to file");
        } catch (IOException e) {
            logger.info("cannot write to file");
        }
    }
}