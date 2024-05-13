package org.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Helper {

    static Logger logger = Logger.getLogger(Helper.class.getName());

    private Helper() {
    }

    static byte[] combineByteArray(List<byte[]> byteArray) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : byteArray) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                logger.info("io exception while combining bytes");
            }
        }

        return outputStream.toByteArray();
    }

    static List<byte[]> byteArrayToByteList(byte[] byteArray) {
        byte[] clearByteArray = Helper.byteArrayFillZeros(byteArray);
        int length = clearByteArray.length / 8;
        List<byte[]> list = new ArrayList<>();
        byte[] byteArrayToList = new byte[8];

        for (int i = 0; i < length; i++) {
            System.arraycopy(clearByteArray, i * 8, byteArrayToList, 0, 8);
            list.add(byteArrayToList);
            byteArrayToList = new byte[8];
        }

        return list;
    }

    static byte[] byteArrayFillZeros(byte[] byteArray) {
        if (byteArray.length % 8 != 0) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int paddingLength = 8 - byteArray.length % 8;
            try {
                outputStream.write(byteArray);
                for (int i = 0; i < paddingLength; i++) {
                    outputStream.write((byte) paddingLength);
                }
            } catch (IOException e) {
                logger.info("io exception: filling with padding");
            }
            return outputStream.toByteArray();
        } else {
            return byteArray;
        }
    }

    static byte[] removePadding(byte[] byteArray) {
        int length = byteArray.length;
        int paddingLength = byteArray[length - 1] & 0xFF;

        if (paddingLength > length) {
            return byteArray;
        }

        int unpaddedLength = length - paddingLength;
        byte[] unpaddedArray = new byte[unpaddedLength];
        System.arraycopy(byteArray, 0, unpaddedArray, 0, unpaddedLength);

        return unpaddedArray;
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

    public static String byteToBinaryString(byte b) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }
}
