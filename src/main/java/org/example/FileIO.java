package org.example;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileIO {

    private final String fileName;

    Logger logger = Logger.getLogger(getClass().getName());

    FileIO(String fileName) {
        this.fileName = fileName;
    }

    public byte[] read() {
        byte[] byteArray = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            byte[] buffer = new byte[1024];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byteArray = baos.toByteArray();

            bis.close();
            fis.close();

        } catch (FileNotFoundException e) {
            logger.info("raed exception");
        } catch (IOException e) {
            logger.info("IO exception");
        }
        return byteArray;
    }

    public void write(List<String> message) {
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (String line : message) {
                writer.write(line);
            }
            logger.info("written to file");
        } catch (IOException e) {
            logger.info("cannot write to file");
        }
    }
}