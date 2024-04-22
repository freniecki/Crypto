package org.example;

import java.io.*;
import java.util.BitSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        /*
            encryption -jar encrypt message(in HEX) key(in HEX)
            encryptF fileName key(in HEX)
         */
        if (args.length != 3) {
            logger.info("use 3 arguments");
            return;
        }

        if (args[0].equals("encrypt")) {
            String message = args[1];
            String key = args[2];

            String[] messageArray = getMessage(message);
            String keyString = getKeyBitSet(key);
            BitSet keyBitSet = Encryption.convertStringToBitSet(keyString, 64);

            Encryption encryption = new Encryption();
            String[] encrypted = new String[messageArray.length];
            for (int i = 0; i < messageArray.length; i++) {
                BitSet messageBitSet = Encryption.convertStringToBitSet(messageArray[i], 64);
                encrypted[i] = Encryption.bitsToString(encryption.encryption(messageBitSet, keyBitSet), 64);
            }
            System.out.println(binaryStringToHexString(encrypted));

        } else if (args[0].equals("encryptF")) {
            byte[] byteArray = new byte[1024];
            try {
                FileInputStream fis = new FileInputStream(args[1]);
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
                logger.info("file not found");
            } catch (IOException e) {
                logger.info("IO exception");
            }

            String messageString = byteHexToBinaryString(byteArray);
            String[] messageArray = getMessage(messageString);
            String keyString = getKeyBitSet(args[2]);
            BitSet keyBitSet = Encryption.convertStringToBitSet(keyString, 64);

            Encryption encryption = new Encryption();
            String[] encrypted = new String[messageArray.length];
            for (int i = 0; i < messageArray.length; i++) {
                BitSet messageBitSet = Encryption.convertStringToBitSet(messageArray[i], 64);
                encrypted[i] = Encryption.bitsToString(encryption.encryption(messageBitSet, keyBitSet), 64);
            }

            System.out.println(binaryStringToHexString(encrypted));

        } else {
            logger.info("error with base command");
        }
    }

    public static String[] getMessage(String message) {
        byte[] messageByte = stringHexToByteArray(message);
        String messageString = byteHexToBinaryString(messageByte);
        if (messageString.length() % 64 != 0) {
            messageString = messageString + "0".repeat(messageString.length() % 64);
        }
        int size = messageString.length() / 64;
        String[] messageArray = new String[size];

        for (int i = 0; i < size; i++) {
               messageArray[i] = messageString.substring(i * 64, i * 64 + 64);
        }

        // 256 / 64 = 4
        // 192+63 = 255
        return messageArray;
    }

    public static byte[] stringHexToByteArray(String plainText) {
        byte[] byteHex = new byte[plainText.length() / 2];
        for (int i = 0; i < byteHex.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(plainText.substring(index, index + 2), 16);
            byteHex[i] = (byte) j;
        }
        return byteHex;
    }

    public static String byteHexToBinaryString(byte[] byteArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : byteArray) {
            stringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return stringBuilder.toString();
    }

    public static String getKeyBitSet(String key) {
        byte[] hexByte = stringHexToByteArray(key);
        return byteHexToBinaryString(hexByte);
    }

    public static String binaryStringToHexString(String[] binary) {
        StringBuilder stringBuilder = new StringBuilder();
        String hexString;
        int hexDecimal;
        for (String s : binary) {
            for (int i = 0; i < 16; i++) {
                hexString = s.substring(i * 4, i * 4 + 4);
                hexDecimal = Integer.parseInt(hexString, 2);
                stringBuilder.append(Integer.toHexString(hexDecimal));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
