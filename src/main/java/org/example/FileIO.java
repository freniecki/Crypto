package org.example;

import java.io.*;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.*;

public class FileIO {

    private final String fileName;

    static Logger logger = Logger.getLogger(FileIO.class.getName());

    private FileIO(String fileName) {
        this.fileName = fileName;
    }

    public static FileIO getFile(String fileName) {
        return new FileIO(fileName);
    }

    public byte[] readTextFileToBytes() {
        String filePath = new File(fileName).getAbsolutePath();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), US_ASCII))) {
            int character;
            while ((character = reader.read()) != -1) {
                outputStream.write((byte) character);
            }
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException");
        } catch (IOException e) {
            logger.info("IOException");
        }

        return outputStream.toByteArray();
    }

    public void writeBytesToFile(byte[] data) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);

        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(data);
            logger.info("Data written to file");
        } catch (IOException e) {
            logger.info("Error writing data to file");
        }
    }

    public byte[] readBytesFromFile() {
        String filePath = new File(fileName).getAbsolutePath();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException");
        } catch (IOException e) {
            logger.info("IOException");
        }

        return outputStream.toByteArray();
    }

    public void writeBytesAsASCII(byte[] data) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), US_ASCII))) {
            writer.write(new String(data, US_ASCII));
            logger.info("Data written to file");
        } catch (IOException e) {
            logger.info("Error writing data to file");
        }
    }


    /**
     Serialization methods
     **/
    public void writeObject(Object T) throws FileNotFoundException, IOException{
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);


        try {
            FileOutputStream outputFile = new FileOutputStream(filePath);
            ObjectOutputStream output = new ObjectOutputStream(outputFile);
            output.writeObject(T);
            output.close();
            outputFile.close();
            logger.info("Data written to file");
        }
        catch (FileNotFoundException fnfe) { // fnfe - File Not Found Exception
            logger.info("FileNotFoundException");
            throw fnfe;
        }
        catch (IOException io) {
            logger.info("IOException");
            throw io;
        }
    }

    public Object readObject() throws FileNotFoundException, IOException, RuntimeException{
        String filePath = new File(fileName).getAbsolutePath();
        Object T = null;
        try {
            FileInputStream inputFile = new FileInputStream(filePath);
            ObjectInputStream input = new ObjectInputStream(inputFile);
            T = input.readObject();
        }
        catch (FileNotFoundException fnfe) {
            logger.info("FileNotFoundExceprtion");
            throw fnfe;
        }
        catch (IOException io) {
            logger.info("IOException");
            throw io;
        } catch (ClassNotFoundException e) {
            logger.severe("Class not found");
        }
        return T;
    }
}