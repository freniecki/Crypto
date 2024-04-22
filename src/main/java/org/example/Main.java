package org.example;

import java.util.BitSet;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        /*
            encryption -jar encrypt/encryptF message(in HEX) key(in HEX)
         */
        if (args.length != 3) {
            logger.info("use 3 arguments");
            return;
        }

        if (args[0].equals("encrypt")) {
            String message = args[1];
            String key = args[2];

            BitSet[] bitSets = messageToBitSet(message);
            Encryption encryption = new Encryption();
            for (int i = 0; i < bitSets.length; i++) {
                encryption.encryption(bitSets[i], )
            }
        } else if (args[0].equals("encryptF")) {

        } else {
            logger.info("");
        }

    }

    public static BitSet[] messageToBitSet(String message) {
        byte[] bytes = message.getBytes();
        int paddingLength = 8 - (bytes.length % 8);
        byte[] paddedMessage = new byte[bytes.length + paddingLength];
        System.arraycopy(bytes, 0, paddedMessage, 0, bytes.length);
        for (int i = bytes.length; i < paddedMessage.length; i++) {
            paddedMessage[i] = (byte) paddingLength;
        }

        int bitSetSize = paddedMessage.length / 8;
        BitSet[] bitSets = new BitSet[bitSetSize];
        for (int i = 0; i < bitSetSize; i++) {
            bitSets[i] = new BitSet(64);
            for (int j = 0; j < 64; j++) {
                if ((paddedMessage[i * 8 + j / 8] & (1 << (7 - j % 8))) != 0) {
                    bitSets[i].set(j);
                }
            }
        }
        return bitSets;
    }
}
